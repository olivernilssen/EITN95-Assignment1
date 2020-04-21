import java.util.*;
import java.io.*;

class T4State extends T4GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int N = 1000, x = 100, lambda = 8, T = 1;
	public int beingServed = 0, rejected = 0, finished = 0, accumulated = 0, noMeasurements = 0, totalArrivals = 0;
	public double accumulatedStart = 0;
	public int leftQ2 = 0;

	public double poisson(double L) {
		return (Math.log(1.0-Math.random())/-L);
	}

	T2SimpleFileWriter W = new T2SimpleFileWriter("Task4/customers.m", false);

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
		accumulated += beingServed;
		noMeasurements++;
		
		W.println(String.valueOf(beingServed));
		
		insertEvent(MEASURE, time + T);
	}
}