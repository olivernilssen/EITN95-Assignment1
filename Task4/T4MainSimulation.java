import java.io.*;
import java.text.DecimalFormat;


public class T4MainSimulation extends T4GlobalSimulation{
 
	static DecimalFormat df = new DecimalFormat("#.#####");

    public static void main(String[] args) throws IOException {
	
		T4Event actEvent = new T4Event();
		T4State actState = new T4State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		
		insertEvent(ARRIVAL, 0);  
		insertEvent(MEASURE, 4);
		
		// The main simulation loop
		while (actState.noMeasurements < 4000){
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
		
		actState.W.close();
		double mean = 1.0*actState.accumulated/actState.noMeasurements;
		
		System.out.println("Mean = " + mean);
	}
}