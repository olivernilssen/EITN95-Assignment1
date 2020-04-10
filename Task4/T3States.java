import java.util.*;
import java.io.*;

class T3State extends T3GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numbQ1 = 0, numbQ2 = 0,  accumulated = 0, noMeasurements = 0, totalArrivals = 0;
	public double accumulatedStart = 0;
	public int leftQ2 = 0;

	Random slump = new Random(); // This is just a random number generator
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T3Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPART1:
				departure1(x.startTime);
				break;
			case DEPART2:
				departure2(x.startTime);
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
		numbQ1++;
		totalArrivals++;

		if (numbQ1 == 1)
			insertEvent(DEPART1, time + 1.0*slump.nextDouble(), time);
		
		insertEvent(ARRIVAL, time + 1.1*slump.nextDouble(), time + 1.1*slump.nextDouble());
	}
	
	private void departure1(Double startTime){
		numbQ1--;
		numbQ2++;
		if (numbQ2 == 1)
			insertEvent(DEPART2, time + 1.0*slump.nextDouble(), startTime);
		if (numbQ1 >= 1)
			insertEvent(DEPART1, time + 1.0*slump.nextDouble(), startTime);
	}

	private void departure2(Double startTime){
		numbQ2--;
		if (numbQ2 >= 1)
			insertEvent(DEPART2, time + 1.0*slump.nextDouble(), startTime);
		
		double timeBetween = time - startTime;
		accumulatedStart += timeBetween;
		leftQ2++;
		//System.out.println("start: " + startTime + " end: " + time);
	}
	
	private void measure(){
		accumulated += numbQ2 + numbQ1;
		noMeasurements++;
		insertEvent(MEASURE, time + 1, 0);
	}
}