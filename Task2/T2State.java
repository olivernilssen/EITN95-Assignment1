import java.util.*;
import java.io.*;

class T2State extends T2GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	private double Xa = 0.002, Xb = 0.004, d = 1, lambda = 150, m = 0.1;
	public int noQueue = 0, jobA = 0, jobB = 0, inDelay = 0, accumulated = 0, noMeasurements = 0, totalarrivals = 0, collected = 0;
	public boolean jobRunning = false;

	T2Queue <Integer> queue = new T2QueueList<Integer>();

	Random slump = new Random(); // This is just a random number generator

	//https://stackoverflow.com/questions/2206199/how-do-i-generate-discrete-random-events-with-a-poisson-distribution
	public double poissonRandomInterarrivalDelay(double L) {
		return (Math.log(1.0-Math.random())/-L);
	}
	
	T2SimpleFileWriter W = new T2SimpleFileWriter("Task2/customersB.m", false);

	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T2Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case SWAP:
				swap();
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
		totalarrivals++;
		if (queue.size() == 0) swap();

		queue.insert(JOBA, time);

		insertEvent(ARRIVAL, time + poissonRandomInterarrivalDelay(lambda));
	}

	private void swap(){
		
	}

	private void departure(){

	}

	private void serviceA () {
		jobRunning = true;

		//even for when serviceA finishes
		insertEvent(ENDA, time + Xa);
	}

	private void endServiceA(){
		jobA--;
		jobRunning = false;
		inDelay++;

		insertEvent(DELAY, time +  d);

		if (jobB >= 1 && !jobRunning){
			insertEvent(JOBB, time);
		}
		else if (jobA >= 1 && !jobRunning) {
			insertEvent(JOBA, time);
		}
	}

	private void StopDelay() {
		inDelay--;
		jobB++;

		if (!jobRunning && jobB >= 1)
			insertEvent(JOBB, time);
		else if (jobA >= 1 && !jobRunning && jobB == 0)
			insertEvent(JOBA, time);
	}

	private void serviceB(){
		jobRunning = true;
		
		insertEvent(ENDB, time + Xb);
	}

	private void endServiceB(){
		jobB--;
		jobRunning = false;
  
		if(jobB >= 1 && !jobRunning) 
			insertEvent(JOBB, time);
		else if (jobA >= 1 && !jobRunning)
			insertEvent(JOBA, time);
	}
	
	private void measure(){
		accumulated += jobA + jobB;
		noMeasurements++;
		collected = jobA + jobB;

		W.println(String.valueOf(collected));

		insertEvent(MEASURE, time + m);
	}
}