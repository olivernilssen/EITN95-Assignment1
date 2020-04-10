import java.util.*;
import java.io.*;


public class T3MainSimulation extends T3GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	T3Event actEvent;
    	T3State actState = new T3State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0, 0);  
        insertEvent(MEASURE, 5, 0);
        
        // The main simulation loop
    	while (time < 10000){
    		actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
			//System.out.println("At time :" + time + " Event: " + actEvent.eventType);
			//System.out.println("Q1: " + actState.numbQ1 + " Q2: " + actState.numbQ2);
    	}
		
    	// Printing the result of the simulation, in this case a mean value
		System.out.println(1.0*actState.accumulated/actState.noMeasurements);
		System.out.println(actState.accumulatedStart/actState.leftQ2);
	}
}