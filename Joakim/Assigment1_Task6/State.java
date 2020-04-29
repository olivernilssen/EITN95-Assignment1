import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	public int numbQ1 = 0, numbQ2 = 0,  accumulated = 0, noMeasurements = 0; 
	public int totalArrivals = 0, steady = 0, unsteadyMeasurment = 0;
	public double lambda = 4/60;
	public int serviceT = 1;
	public double startT = 9*60;
	public double time = startT;
	public double endT = 17.00;
	public int costumer = 0;
	public int day = 0;
	double accumulatedStart = 0, mean = 0;
	public boolean warmup = true, sched1Depart = false, sched2Depart = false;
	Random slump = new Random();
	
	T6SimpleFileWriter W = new T6SimpleFileWriter("Task6/customers1.m", false);
	T6Queue <Integer > Q1 = new T6QueueList < Integer >() ;

	public double getExpo(double mean) {
		return Math.log(1-slump.nextDouble())/(-1/mean);
	}
	public double getDepart() {
	return (10*slump.nextDouble() + 10);
	}
	
	// This is just a random number generator
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
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
	
	//Insert arrival into queue1, start departure from queue 1 if equals 0.
	//insert new arrival
	private void arrival(){
		if((time/60) < endT) {
			costumer++;
			Q1.insert ( costumer , time);
			insertEvent ( DEPART , time + getDepart());
		}
		else {
			if(!Q1.isEmpty()) {
			insertEvent ( DEPART , time );	
		}	
	}
}
	
	//depart from one queue 1 to queue 2, keep values from first queue
	//delete the customer from queue 1. 
	//Start new departure from 2 (to initialise if it was 0)
	//start new departure from 1 if the size is bigger than 0
	private void departure(){
		costumer--;
		if(costumer == 0 && (time/60) > endT) {
			insertEvent ( MEASURE , time, day);
			day++;
			time = startT;
		}
		else if((time/60) > endT) {
			insertEvent ( DEPART , time + getDepart());
		}
		else {
			insertEvent ( ARRIVAL , time + getDepart());
		}
	}

	//Delete customer from queue, and if there is still someone 
	//in the queue, start serving them and insert new departure 
		private void measure(){
		if (warmup){
			unsteadyMeasurment++;
			accumulated += Q1.size() + Q2.size();
			double newMean = accumulated/unsteadyMeasurment;
			
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -3 ) || (variance >= 0 && variance < 3)){
				steady++;
			}
			else{
				steady = 0;
			}

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