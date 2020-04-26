import java.util.*;
import java.io.*;

class T4State extends T4GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int N = 100, x = 10, lambda = 4, T = 4;
	public int beingServed = 0, rejected = 0, finished = 0, accumulated = 0, 
				noMeasurements = 0, unsteadyMeasurments, totalArrivals = 0, steady = 0;
	public double accumulatedStart = 0, mean = 0;
	public boolean warmup = true;

	public double poisson(double L) {
		return (Math.log(1.0-Math.random())/-L);
	}

	T2SimpleFileWriter W = new T2SimpleFileWriter("Task4/customers6.m", false);

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
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	private void arrival(){
		totalArrivals++;

		if (beingServed < N){
			beingServed++;
			insertEvent(DEPART, time + x);
		}else{
			rejected++;
		}
		
		insertEvent(ARRIVAL, time + poisson(lambda));
	}
	
	private void departure(){
		beingServed--;
		finished++;
	}
	
	private void measure(){
		if (warmup){
			unsteadyMeasurments++;
			accumulated += beingServed;
			double newMean = accumulated/unsteadyMeasurments;
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -1 ) || (variance >= 0 && variance < 1)){ steady++;}
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