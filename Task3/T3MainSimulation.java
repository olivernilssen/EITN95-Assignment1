import java.io.*;


public class T3MainSimulation extends T3GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
		double [] means = new double [1];
		float [] timeSpent = new float[1];
		int i = 0;
		
		while (i < means.length){
			T3Event actEvent;
			T3State actState = new T3State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);  
			insertEvent(MEASURE, 1);
			
			// The main simulation loop
			while (actState.noMeasurements < 1000){
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
				//System.out.println("At time :" + time + " Event: " + actEvent.eventType);
				//System.out.println("Q1: " + actState.numbQ1 + " Q2: " + actState.numbQ2);
			}
		
			actState.W.close();
			double mean = 1.0*actState.accumulated/actState.noMeasurements;
			float timeQ = (float) (1.0 * actState.accumulatedStart / actState.leftQ2);
			System.out.println(mean + " " + timeQ);
			// // Printing the result of the simulation, in this case a mean value
			// System.out.println("Avg people in queue " + 1.0*actState.accumulated/actState.noMeasurements);
			// System.out.println("time in queue " + 1.0*actState.accumulatedStart/actState.leftQ2);
			means[i] = mean;
			timeSpent[i] = timeQ;
			i++;
			time = 0;
		}
		// for (double p : means) {
		// 	System.out.print(p  + " ");
		// }
		System.out.println("done \n");

		// for (float t : timeSpent) {
		// 	System.out.print(t  + " ");
		// }

	}
}