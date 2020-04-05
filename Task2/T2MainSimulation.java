import java.util.*;
import java.io.*;


public class T2MainSimulation extends T2GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	T2Event actEvent;
    	T2State actState = new T2State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 0.1);
        
        // The main simulation loop
    	while (time < 150){
    		actEvent = T2eventList.fetchEvent();
    		time = actEvent.eventTime;
			actState.treatEvent(actEvent);
			//System.out.println("At time :" + time + " Event: " + actEvent.eventType);
			//System.out.println("Type A: " + actState.jobA + " Type B: " + actState.jobB + " Indelay: " + actState.inDelay);
    	}
		
    	// Printing the result of the simulation, in this case a mean value
		System.out.println(1.0*actState.accumulated/actState.noMeasurements + " - no mess: " + actState.noMeasurements);
		actState.W.close();
	}
}