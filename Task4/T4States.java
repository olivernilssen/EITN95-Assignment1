import java.util.*;

class T4State extends T4GlobalSimulation{
	//variables for simulation
	public int N = 100, x = 10, lambda = 4, T = 4;
	public int beingServed = 0, rejected = 0, finished = 0;

	//variables for warmup
	private int unsteadyMeasurments, steady = 0;
	private double mean = 0;
	public boolean warmup = true;
	
	//variables for measuring	
	public double accumulated = 0, noMeasurements = 0;
	public double accumulatedStart = 0;

	//calculate the arrival of next arrival from poisson
	public double poisson(double L) {
		return (Math.log(1.0-Math.random())/-L);
	}

	SimpleFileWriter W = new SimpleFileWriter("Task4/customers1.m", false);
	Random slump = new Random(); // This is just a random number generator
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T4Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPART:
				departure();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	//On arrival we check if the amount of customers being served exceeds
	//the number of servers, if not we start serving otherwise reject. 
	//Create new arrival event
	private void arrival(){
		
		if (beingServed < N){
			beingServed++;
			insertEvent(DEPART, time + x);
		}else{
			rejected++;
		}
		
		insertEvent(ARRIVAL, time + poisson(lambda));
	}
	
	//Once departing, we remove amount of customers being served. 
	private void departure(){
		beingServed--;
		finished++;
	}
	
	//measure how many customers are being served in the system
	//but check if it's reached steady state first
	private void measure(){
		if (warmup){
			unsteadyMeasurments++;
			accumulated += beingServed;
			double newMean = accumulated/unsteadyMeasurments;
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -1 ) || (variance >= 0 && variance < 1)) { steady++;}
			else{ steady = 0; }

			mean = newMean;

			if(steady == 250){
				warmup = false;
				accumulated = 0;
				noMeasurements = 0;
			}
		}
		else {
			accumulated += beingServed;
			noMeasurements++;

			W.println(String.valueOf(beingServed));
		}
		
		insertEvent(MEASURE, time + T);
	}
}