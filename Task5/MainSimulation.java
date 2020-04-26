import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation
public class MainSimulation extends Global{

	public static void main(String[] args) throws IOException {
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.
		Signal actSignal;
		new SignalList();

		QS Q1 = new QS();
		Q1.Qnumb = 1;
		allQueues[0] = Q1; 
		QS Q2 = new QS();
		Q2.Qnumb = 2;
		allQueues[1] = Q2;
		QS Q3 = new QS();
		Q3.Qnumb = 3;
		allQueues[2] = Q3;
		QS Q4 = new QS();
		Q4.Qnumb = 4;
		allQueues[3] = Q4;
		QS Q5 = new QS();
		Q5.Qnumb = 5;
		allQueues[4] = Q5;

		for(QS q : allQueues){
			q.sendTo = null;
			SignalList.SendSignal(MEASURE, q, time);
		}

		// Here process instances are created (5 queues and one generator) and their
		// parameters are given values.

		Gen Generator = new Gen();
		Generator.lambda = 0.12; // Generator sets uniform arrival time to be 0.12
		// The generated customers shall be sent to Q1

		// To start the simulation the first signals are put in the signal list
		SignalList.SendSignal(READY, Generator, time);

		// This is the main loop
		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
			// System.out.println("Q1: " + Q1.numberInQueue + " Q2: " + Q2.numberInQueue + " Q3: " + Q3.numberInQueue);
		}

		// Finally the result of the simulation is printed below:
		double allAccumulated = 0;
		double allMeasurments = 0;
		for (QS q : allQueues){
			allAccumulated += q.accumulated;
			allMeasurments += q.noMeasurements;
			System.out.println("numb: " + q.Qnumb + " mean: " + q.accumulated/q.noMeasurements);
		}
		System.out.println("Mean number of customers in queuing system: " + 1.0 * allAccumulated / allMeasurments);
	}
}