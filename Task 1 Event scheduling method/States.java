import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numbQ1 = 0, numbQ2 = 0, rejected = 0, accumulatedQ2 = 0, noMeasurements = 0, totalArrivals = 0;

	Random slump = new Random(); // This is just a random number generator
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
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
		if (numbQ1 == 0)
			insertEvent(DEPART1, time + 2.1*slump.nextDouble());
		
		if (numbQ1 >= MAXQ1)
			rejected += 1;
		else
			numbQ1++;
			totalArrivals++;
		insertEvent(ARRIVAL, time + 1);
	}
	
	private void departure1(){
		numbQ1--;
		numbQ2++;
		if (numbQ2 == 1)
			insertEvent(DEPART2, time + 2);
		if (numbQ1 > 0)
			insertEvent(DEPART1, time + 2.1*slump.nextDouble());
	}

	private void departure2(){
		numbQ2--;
		if (numbQ2 > 0)
			insertEvent(DEPART2, time + 2);
		
	}
	
	private void measure(){
		accumulatedQ2 += numbQ2;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble()*5);
	}
}