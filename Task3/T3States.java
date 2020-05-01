import java.util.*;
import java.util.Queue;

class T3State extends T3GlobalSimulation{
	//variables for simulation
	private int serviceT = 1;
	private double arrival = 1.1, m = 0.5;

	//variables for warmup
	private double mean = 0;
	private boolean warmup = true, sched1Depart = false, sched2Depart = false;
	private int steady = 0, unsteadyMeasurment = 0;

	//variables for measuring
	public int accumulated = 0, noMeasurements = 0; 
	public int totalArrivals = 0;	
	public int leftQ2 = 0;
	public double accumulatedStart = 0;

	Queue <T3Customer> Q1 = new LinkedList<>();
	Queue <T3Customer> Q2 = new LinkedList<>();
	
	SimpleFileWriter W = new SimpleFileWriter("Task3/customers11.m", false);

	//calculate the exponential distribution based on mean
	public double getExpo(double mean) {
		return Math.log(1-slump.nextDouble())/(-1/mean);
	}

	Random slump = new Random(); // This is just a random number generator
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T3Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPART1:
				departure1();
				break;
			case DEPART2:
				departure2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	//Insert arrival into queue1, start departure from queue 1 if equals 0.
	//insert new arrival
	private void arrival(){
		totalArrivals++;
		Q1.add(new T3Customer(totalArrivals, time));
		
		if (Q1.size() == 1 && sched1Depart == false){
			insertEvent(DEPART1, time + getExpo(serviceT));
			sched1Depart = true;
		}
		
		insertEvent(ARRIVAL, time + getExpo(arrival));
	}
	
	//depart from one queue 1 to queue 2, keep values from first queue
	//delete the customer from queue 1. 
	//Start new departure from 2 (to initialise if it was 0)
	//start new departure from 1 if the size is bigger than 0
	private void departure1(){
		sched1Depart = false;
		Q2.add(Q1.remove());
		
		if (Q2.size() == 1 && sched2Depart == false){
			insertEvent(DEPART2, time + getExpo(serviceT));
			sched2Depart = true;
		}
		if (Q1.size() > 0 && sched1Depart == false){
			sched1Depart = true;
			insertEvent(DEPART1, time + getExpo(serviceT));
		}
	}

	//Delete customer from queue, and if there is still someone 
	//in the queue, start serving them and insert new departure 
	private void departure2(){
		sched2Depart = false;
		T3Customer removed = Q2.remove();

		accumulatedStart += (time - removed.getStartTime());		

		if (Q2.size() > 0 && sched2Depart == false){
			insertEvent(DEPART2, time + getExpo(serviceT));
			sched2Depart = true;
		}

		leftQ2++;
	}
	
	//measure, but first check if simulation is still in 
	//warmup state or steady-state
	private void measure(){
		if (warmup){
			unsteadyMeasurment++;
			accumulated += Q1.size() + Q2.size();
			double newMean = accumulated/unsteadyMeasurment;
			
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -3 ) || (variance >= 0 && variance < 3)){ steady++; }
			else{ steady = 0; }

			mean = newMean;

			if(steady == 250){
				warmup = false;
				accumulated = 0;
				noMeasurements = 0;
			}
		}
		else {
			accumulated += Q1.size() + Q2.size();
			noMeasurements++;

			W.println(String.valueOf(accumulatedStart/leftQ2));
		}
		
		insertEvent(MEASURE, time + m);
	}
}