import java.util.*;
import java.io.*;


public class T4MainSimulation extends T4GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	T4Event actEvent;
    	T4State actState = new T4State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 1);
        
        // The main simulation loop
    	while (time < 1000){
    		actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
			//System.out.println("At time :" + time + " Event: " + actEvent.eventType);
			//System.out.println("Q1: " + actState.numbQ1 + " Q2: " + actState.numbQ2);
		}
		
		actState.W.close();
    	// Printing the result of the simulation, in this case a mean value
		System.out.println(1.0*actState.accumulated/actState.noMeasurements);
		System.out.println("finihed: " + actState.finished + " rejected: " + actState.rejected);
	}
}