import java.io.*;

public class T1MainSimulation extends T1GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	T1Event actEvent;
    	T1State actState = new T1State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (actState.noMeasurements < 4000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
			actState.treatEvent(actEvent);
    	}
		
		double probOfRejection = 1.0*actState.rejected/actState.totalArrivals;
    	// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean number in queue: " + 1.0*actState.accumulated/actState.noMeasurements);
		System.out.println("Probaility of rejection: " + probOfRejection);
		System.out.println("Number of rejected: " + actState.rejected 
							+ "\nNumber of arrivals " + actState.totalArrivals 
							+ "\nNumber of measurments: " + actState.noMeasurements);
	}
}