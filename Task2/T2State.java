import java.util.*;
import java.io.*;

class T2State <Item> extends T2GlobalSimulation {
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	private double d = 1, lambda = 150, m = 0.1;
	public int accumulated = 0, noMeasurements = 0, arrivalNumber = 0, leaving = 0;
	public boolean jobArunning = false, jobBrunning = false;

	T2Queue <Integer> queue = new T2QueueList<Integer>();
	T2Queue <Integer> delayqueue = new T2QueueList<Integer>();

	Random slump = new Random(); // This is just a random number generator

	//https://stackoverflow.com/questions/2206199/how-do-i-generate-discrete-random-events-with-a-poisson-distribution
	public double poissonRandomInterarrivalDelay(double L) {
		return (Math.log(1.0-Math.random())/-L);
	}
	
	T2SimpleFileWriter W = new T2SimpleFileWriter("Task2/customersA.m", false);

	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T2Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case ACTION:
				action();
				break;
			case DELAY:
				delay();
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
		arrivalNumber++;
		// System.out.println("new arrival " + arrivalNumber);
		queue.insert(arrivalNumber, JOBA, time, jobBrunning);
		
		if (queue.size() == 1 && !jobArunning && !jobBrunning) {
			insertEvent(ACTION, time + queue.actionTime());
			if(queue.priority() == JOBB) {
				jobBrunning = true;
			}
			else {
				jobArunning = true;
			}
		}
		insertEvent(ARRIVAL, time + poissonRandomInterarrivalDelay(lambda));
	}

	private void action(){
		// System.out.println("... Qs: " + queue.size());
		// for (int i : queue){
		// 	System.out.println(i);
		// }

		int jobPriority = queue.priority();
		
		if(jobPriority == JOBA){
			double [] removed = queue.delete();
			delayqueue.insert((int) removed[0], 0, removed[1], false);
			insertEvent(DELAY, time + d);
		}
		else if (jobPriority == JOBB){
			queue.delete();
			leaving++;
		}

		jobArunning = false;
		jobBrunning = false;

		if (!queue.isEmpty()) {
			insertEvent(ACTION, time + queue.actionTime());
			if(queue.priority() == JOBB) {
				jobBrunning = true;
			}
			else {
				jobArunning = true;
			}
		}
	}

	private void delay(){
		double [] endDelay = delayqueue.delete();
		queue.insert((int) endDelay[0], JOBB, endDelay[1], jobBrunning);

		if (queue.size() == 1 && !jobArunning && !jobBrunning){
			insertEvent(ACTION, time + queue.actionTime());
			
			if(queue.priority() == JOBB) {
				jobBrunning = true;
			}
			else {
				jobArunning = true;
			}
		}
	}
	
	private void measure(){
		accumulated += queue.size();
		noMeasurements++;

		W.println(String.valueOf(queue.size()));

		insertEvent(MEASURE, time + m);
	}
}