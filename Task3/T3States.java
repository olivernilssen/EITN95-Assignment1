import java.util.*;
import java.io.*;

class T3State extends T3GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numbQ1 = 0, numbQ2 = 0,  accumulated = 0, noMeasurements = 0, totalArrivals = 0, steady = 0, unsteadyMeasurment = 0;
	public double accumulatedStart = 0, mean = 0;
	public boolean warmup = true, sched1Depart = false, sched2Depart = false;
	public int leftQ2 = 0;

	T3Queue <Integer> Q1 = new T3QueueList<Integer>();
	T3Queue <Integer> Q2 = new T3QueueList<Integer>();
	
	T3SimpleFileWriter W = new T3SimpleFileWriter("Task3/customers11.m", false);

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
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	private void arrival(){
		// System.out.println("..Arrival..");
		totalArrivals++;
		Q1.insert(totalArrivals, time);
		
		if (Q1.size() == 1 && sched1Depart == false){
			// System.out.println("schedule depart from Q1 "  + Q1.size());
			insertEvent(DEPART1, time + 1.0*slump.nextDouble());
			sched1Depart = true;
		}
		
		insertEvent(ARRIVAL, time + 1.1*slump.nextDouble());
	}
	
	private void departure1(){
		// System.out.println("..depart 1..");
		if (Q1.isEmpty()){
			System.out.println("Something went wrong Q1");
		}
			else {
			sched1Depart = false;
			Q2.insert(Q1.jobnr(), Q1.startTime());
			Q1.delete();

			// System.out.println("inserting into q2 - s: " + Q2.size());

			if (Q2.size() == 1 && sched2Depart == false){
				// System.out.println("Inserting departure from Q2 - s: " + Q2.size());
				insertEvent(DEPART2, time + 1.0*slump.nextDouble());
				sched2Depart = true;
			}
			if (Q1.isEmpty() == false && sched1Depart == false){
				// System.out.println("Inserting departure from Q1 - s: " + Q1.size());
				sched1Depart = true;
				insertEvent(DEPART1, time + 1.0*slump.nextDouble());
			}
		}
	}

	private void departure2(){
		// System.out.println("..depart 2..");
		sched2Depart = false;
		if (Q2.size() == 0){
			System.out.println("something went wrong here");
		}
		else{

			double timeSpent = time - Q2.startTime();
			accumulatedStart += timeSpent;		
			Q2.delete();

			if (Q2.isEmpty() == false && sched2Depart == false){
				// System.out.println("new departure from 2: " + Q2.size());
				insertEvent(DEPART2, time + 1.0*slump.nextDouble());
				sched2Depart = true;
			}

			leftQ2++;
		}
		//System.out.println("start: " + + " end: " + time);
	}
	
	private void measure(){
		if (warmup){
			unsteadyMeasurment++;
			accumulated += Q1.size() + Q2.size();
			double newMean = accumulated/unsteadyMeasurment;
			
			double variance = mean - newMean;
			// System.out.println("variance between the means " + variance);

			if ((variance <= 0 && variance > -2 ) || (variance >= 0 && variance < 2)){
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

			W.println(String.valueOf(Q1.size() + Q2.size()));
		}
		
		insertEvent(MEASURE, time + 0.5);
	}
}