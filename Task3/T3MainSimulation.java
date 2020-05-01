import java.io.*;


public class T3MainSimulation extends T3GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
		T3Event actEvent;
		T3State actState = new T3State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL, 0);  
		insertEvent(MEASURE, 1);
		
		// The main simulation loop
		while (actState.noMeasurements < 2000){
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
	
		actState.W.close();
		double mean = 1.0*actState.accumulated/actState.noMeasurements;
		float timeQ = (float) (1.0 * actState.accumulatedStart / actState.leftQ2);
		System.out.println("Mean number of customer: " + mean );
		System.out.println("Mean time spent in Q: " + timeQ);
	}
}