import java.io.*;

public class T2MainSimulation extends T2GlobalSimulation{
 
    public static <Item> void main(String[] args) throws IOException {
    	T2Event actEvent;
    	T2State <Item> actState = new T2State<Item>(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 0.1);
        
        // The main simulation loop
    	while (actState.noMeasurements < 2000){
    		actEvent = T2eventList.fetchEvent();
    		time = actEvent.eventTime;
			actState.treatEvent(actEvent);
	 	}
		
    	// Printing the result of the simulation, in this case a mean value
		System.out.println("Avg. no customers " + 1.0*actState.accumulated/actState.noMeasurements);
		//System.out.println("Customers left queue " + actState.leaving);
		actState.W.close();
	}
}