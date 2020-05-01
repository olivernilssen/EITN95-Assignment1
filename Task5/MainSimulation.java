import java.io.*;
import java.text.DecimalFormat;

//It inherits Proc so that we can use time and the signal names without dot notation
public class MainSimulation extends Global{
	static DecimalFormat df = new DecimalFormat("#.####");

	public static void main(String[] args) throws IOException {
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.
		Signal actSignal;
		new SignalList();

		//Change these values, and the correct values will be applied to 
		//both measuring files(for matlab) and for the generator
		global_method = RAND;
		global_mean = 0.12;

		// Create the process Measure, that measures values 
		//in the system
		Measure measure = new Measure();
		SignalList.SendSignal(WARMUP, measure, time);

		//initialise the queues we are using and add them to the global 
		//list allQueues.
		for (int i = 0; i < allQueues.length; i++){
			allQueues[i] = new QS();
			allQueues[i].Qnumb = i + 1; //name of queue
			allQueues[i].sendTo = null;
			allQueues[i].measurproc = measure;
			SignalList.SendSignal(MEASURE, allQueues[i], time);
		}

		//initialize the generator and set values (this can also be done in the gen as 
		//the values are now global)
		Gen Generator = new Gen();
		Generator.lambda = global_mean; // Generator sets uniform arrival time to be 0.12
		Generator.method = global_method; //choose which method to use for queue selection

		// To start the simulation the first signals are put in the signal list
		SignalList.SendSignal(READY, Generator, time);

		// This is the main loop
		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		measure.Cw.close();
		measure.Tw.close();
		System.out.println("Mean number of customers in queuing system: " + df.format(1.0 * measure.allBatchC / measure.noBatchesC));
		System.out.println("Mean number spent in queue: " + df.format(1.0 * measure.allBatchT / measure.noBatchesT));
	}
}