import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State();
    	// The state that should be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL_Q1, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (actState.nbrArrived < 1000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("Number arrived Q1: " + actState.nbrArrived);
    	System.out.println("Number rejected Q1: " +  actState.nbrOfReject);
    	System.out.println("Probability rejected: " + (double) actState.nbrOfReject/(double) actState.nbrArrived);
    }
}