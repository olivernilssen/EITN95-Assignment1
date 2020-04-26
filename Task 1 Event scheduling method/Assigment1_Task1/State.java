import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue1 = 0, numberInQueue2 = 0, nbrArrived = 0, noMeasurements = 0, nbrOfReject = 0, accumulated = 0;

	Random p = new Random(); // This is just a random number generator
	int arrivalTimeQ1 = 1;
	double serviceTimeQ1 = 2.1;
	double measureTime = 5;
	double lambda_Q1 = 1/serviceTimeQ1;
	int serviceTimeQ2 = 2;
	double lambda_M = 1/measureTime;
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_Q1:
				arrival_Q1();
				break;
			case DEPARTURE_Q1:
				departure_Q1();
				break;
			case DEPARTURE_Q2:
				departure_Q2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival_Q1(){
		nbrArrived++;
		if(numberInQueue1 < 10){
			numberInQueue1++;
		}
		else {
			nbrOfReject++;
		}
		if (numberInQueue1 ==1) {
			insertEvent(DEPARTURE_Q1, time - Math.log(1-p.nextDouble())/lambda_Q1);
		}
		insertEvent(ARRIVAL_Q1, time + arrivalTimeQ1);
	}
	private void departure_Q1() {
		numberInQueue1--;
		numberInQueue2++;
		if (numberInQueue2 == 1) {
			insertEvent(DEPARTURE_Q2, time + serviceTimeQ1);
		}
		if(numberInQueue1 > 0) {
			insertEvent(DEPARTURE_Q1, time - Math.log(1-p.nextDouble())/lambda_Q1);
		}
	}
	private void departure_Q2() {
		numberInQueue2--;
		if(numberInQueue2 > 0) {
			insertEvent(DEPARTURE_Q2, time + serviceTimeQ2);				
		}
	}
	
	private void measure(){
		accumulated =+ nbrArrived;
		noMeasurements++;
		insertEvent(MEASURE, time - Math.log(1-p.nextDouble())/lambda_M);
	}
}