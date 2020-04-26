import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nbrInQueueA = 0, accumulated = 0, noMeasurements = 0, nbrInQueueB = 0;

	Random p = new Random(); // This is just a random number generator
	double lambda = 150;
	double delay = 1;
	double depart_A = 0.02;
	double depart_B = 0.04;
	double measureTime = 0.1;
	int queueA = 0, queueB = 0;
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPARTURE_A:
				departureA();
				break;
			case ARRIVAL_B:
				arrivalB();
					break;
			case DEPARTURE_B:
				departureB();
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
		queueA++;
		insertEvent(DEPARTURE_A, time + depart_A);		
		insertEvent(ARRIVAL, time - Math.log(1-p.nextDouble())/lambda);		

	}
	
	private void departureA(){
		queueA--;
		nbrInQueueA++;
		insertEvent(ARRIVAL_B, time + delay);	
		if(queueB > 0) {
			insertEvent(DEPARTURE_B, time + depart_B);	
		}
		else if(queueA > 0) {
			insertEvent(DEPARTURE_A, time + depart_A);	
		}
	}
	private void arrivalB(){
		queueB++;
		if(queueB > 0) {
			insertEvent(DEPARTURE_B, time + depart_B);			
		}
		
	}
	private void departureB(){
		if(queueB > 0) {
		queueB--;
		nbrInQueueB++;
		insertEvent(DEPARTURE_B, time + depart_B);			
		}
		else if(queueA > 0){
		insertEvent(DEPARTURE_A, time + depart_B);			
		}
		
	}
	
	private void measure(){
		accumulated = nbrInQueueB + nbrInQueueA;
		noMeasurements++;
		insertEvent(MEASURE, time + measureTime);
	}
}