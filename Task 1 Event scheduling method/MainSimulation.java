import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (time < 10000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
			actState.treatEvent(actEvent);
			// System.out.println("At time :" + time + " Event: " + actEvent.eventType);
			// System.out.println("Q1: " + actState.numbQ1 + " Q2: " + actState.numbQ2);
    	}
		
		double probOfRejection = 1.0*actState.rejected/actState.totalArrivals;
    	// Printing the result of the simulation, in this case a mean value
		System.out.println(1.0*actState.accumulatedQ2/actState.noMeasurements);
		System.out.println("Number of rejected: " + actState.rejected + "\nNumber of arrivals " + actState.totalArrivals +"\nNumber of measurments: " + actState.noMeasurements);
		System.out.println("Probaility of rejection: " + probOfRejection);
	}
}