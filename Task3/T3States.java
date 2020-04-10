import java.util.*;
import java.io.*;

class T3State extends T3GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numbQ1 = 0, numbQ2 = 0,  accumulated = 0, noMeasurements = 0, totalArrivals = 0;
	public double accumulatedStart = 0;
	public int leftQ2 = 0;
	T3Queue <Integer> Q1 = new T3QueueList<Integer>();
	T3Queue <Integer> Q2 = new T3QueueList<Integer>();

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
		totalArrivals++;
		Q1.insert(totalArrivals, time);
		
		if (Q1.size() == 1)
			insertEvent(DEPART1, time + 1.0*slump.nextDouble());
		
		insertEvent(ARRIVAL, time + 2*slump.nextDouble());
	}
	
	private void departure1(){
		Q2.insert(Q1.jobnr(), Q1.startTime());
		Q1.delete();

		if (Q2.size() == 1)
			insertEvent(DEPART2, time + 1.0*slump.nextDouble());
		if (Q1.size() >= 1)
			insertEvent(DEPART1, time + 1.0*slump.nextDouble());
	}

	private void departure2(){
		double timeSpent = time - Q2.startTime();
		accumulatedStart += timeSpent;
		// System.out.println("jobnr " + Q2.jobnr() + " came at time: " + Q2.startTime() + " and left at: " + time)
		// System.out.println("leftTime: " + timeSpent);
		Q2.delete();

		if (Q2.size() >= 1)
			insertEvent(DEPART2, time + 1.0*slump.nextDouble());
		
		leftQ2++;
		//System.out.println("start: " + + " end: " + time);
	}
	
	private void measure(){
		accumulated += Q1.size() + Q2.size();
		noMeasurements++;
		insertEvent(MEASURE, time + 1);
	}
}