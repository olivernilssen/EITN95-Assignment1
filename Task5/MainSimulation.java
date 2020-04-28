import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation
public class MainSimulation extends Global{

	public static void main(String[] args) throws IOException {
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.
		Signal actSignal;
		new SignalList();

		// Here process instances are created (5 queues and one generator) and their
		// parameters are given values.

		//initialise the queues we are using and add them to the global 
		//list allQueues.
		for (int i = 0; i < allQueues.length; i++){
			allQueues[i] = new QS();
			allQueues[i].Qnumb = i + 1; //name of queue
			allQueues[i].sendTo = null;
			SignalList.SendSignal(MEASURE, allQueues[i], time);
		}

		Gen Generator = new Gen();
		Generator.lambda = 0.12; // Generator sets uniform arrival time to be 0.12
		Generator.method = RAND; //choose which method to use for queue selection

		// To start the simulation the first signals are put in the signal list
		SignalList.SendSignal(READY, Generator, time);

		// This is the main loop
		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		// Finally the result of the simulation is printed below:
		double allAccumulated = 0, allMeasurments = 0, allTimes = 0, allLeft = 0;
		
		for (QS q : allQueues){
			allTimes += q.timeSpent; 
			allLeft += q.leftQ;
			allAccumulated += q.accumulated;
			allMeasurments += q.noMeasurements;
			double mean = 1.0 * q.accumulated/q.noMeasurements;
			System.out.println("numb: " + q.Qnumb + " mean: " + mean + " timespent " + (q.timeSpent/q.leftQ));
		}

		System.out.println("Mean number of customers in queuing system: " + 1.0 * allAccumulated / allMeasurments);
		System.out.println("Mean number spent in queue: " + 1.0 * allTimes / allLeft);
	}
}