import java.io.*;
import java.text.DecimalFormat;


public class T4MainSimulation extends T4GlobalSimulation{
 
	static DecimalFormat df = new DecimalFormat("#.#####");

    public static void main(String[] args) throws IOException {
		float [] means = new float [1];
		int i = 0, q = 0;

		while (i < means.length){
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
				//System.out.println("At time :" + time + " Event: " + actEvent.eventType);
				//System.out.println("Q1: " + actState.numbQ1 + " Q2: " + actState.numbQ2);
			}
			
			actState.W.close();
			float mean = (float)1.0*actState.accumulated/actState.noMeasurements;
			means[i] = mean;
			q += mean;
			i++;

		}

		System.out.println("Mean of means = " + (q/means.length));

		for (double p : means) {
			System.out.print((df.format(p)) + " ");
		}
	}
}