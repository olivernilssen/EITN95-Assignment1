import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation
public class T6MainSimulation extends T6Global{

	public static void main(String[] args) throws IOException {
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.
		double batchWait = 0, allBatchesWait = 0;
		double batchFinish = 0, allBatchesFinish = 0;
		int i = 0, N = 1000; //i = iteration, N = days we want done
		int batchSize = 10; //size of each batch
		int count = 0; //to count what batch we are on right now

		T6Signal actSignal = new T6Signal();
		new T6SignalList();
		//create the Qprocess aka. our pharmacist	
		T6QS pharmacist = new T6QS();
		//T6SignalList.SendSignal(MEASURE, pharmacist, time);

		T6Gen Generator = new T6Gen();
		Generator.lambda = 4;//h-1 : Poisson arrival, unit given in hours (will be converted)
		Generator.sendTo = pharmacist; // choose where to send new costomers (we only have one pharmacist)

		// To start the simulation the first signals are put in the signal list

		// This is the main loop - while not finished all perscriptions continue
		while (i < N){
			try {
			T6SignalList.SendSignal(READY, Generator, time);

				while (!finished) {
					actSignal = T6SignalList.FetchSignal();
					time = actSignal.arrivalTime;
					actSignal.destination.TreatSignal(actSignal);
				}

				batchFinish += time;
				batchWait += (int)pharmacist.timeSpent/pharmacist.leftQ;
				count++;
				
				if (count == batchSize){
					count = 0;
					allBatchesWait += (batchWait/batchSize);
					batchWait = 0;
				}
				pharmacist.resetDay();
				time = 0;
				finished = false;
				i++;
			}
			catch (NullPointerException ne) {
				System.out.println("there is no signal, signal == " + ne.getMessage());
				return;
			}
		}

		String finishedDay = hrminsec((int)batchFinish/N, true);
		String WorkTime = hrminsec((int)allBatchesWait/(N/batchSize), false);

		// Finally the result of the simulation is printed below:
		System.out.println("Mean time scripts spent in queue: " + WorkTime);
		System.out.println("The pharmacist finished work on average at: " + finishedDay);
	}

	public static String hrminsec(int timespent, boolean clock){
		int numberOfHours = (timespent % 86400 ) / 3600;
		int numberOfMinutes = ((timespent % 86400 ) % 3600 ) / 60;
		int numberOfSeconds = ((timespent % 86400 ) % 3600 ) % 60;
		if (clock) {numberOfHours = numberOfHours + 9;}
		return numberOfHours + ":" + numberOfMinutes + ":" + numberOfSeconds;
	}
}