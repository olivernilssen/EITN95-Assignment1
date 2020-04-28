import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation
public class T6MainSimulation extends T6Global{

	public static void main(String[] args) throws IOException {
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.
		double meanWait = 0;
		double meanFinish = 0;
		double meanScripts = 0;
		int i = 0, N = 10; //i = iteration, N = days we want done

		while (i < N){
			T6Signal actSignal = new T6Signal();
			new T6SignalList();
			//create the Qprocess aka. our pharmacist	
			T6QS pharmacist = new T6QS();
			T6SignalList.SendSignal(MEASURE, pharmacist, time);

			T6Gen Generator = new T6Gen();
			Generator.lambda = 4;//h-1 : Poisson arrival, unit given in hours (will be converted)
			Generator.sendTo = pharmacist; // choose where to send new costomers (we only have one pharmacist)

			// To start the simulation the first signals are put in the signal list
			T6SignalList.SendSignal(READY, Generator, time);

			// This is the main loop - while not finished all perscriptions continue
			
			while (!finished) {
				actSignal = T6SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}

			meanFinish += time;
			meanWait += (int)pharmacist.timeSpent/pharmacist.leftQ;
			// meanScripts += pharmacist.accumulated/pharmacist.noMeasurements;
			time = 0;
			finished = false;
			i++;
		}

		String finishedDay = hrminsec((int)meanFinish/N);
		String WorkTime = hrminsec((int)meanWait/N);
		double meaninQueue = meanScripts/N;

		// Finally the result of the simulation is printed below:
		System.out.println("Mean number of customers in queuing system: " + meaninQueue);
		System.out.println("Mean time spent in queue: " + WorkTime);
		System.out.println("The pharmacist finished work at on averge: " + finishedDay);
	}

	public static String hrminsec(int timespent){
		int numberOfHours = (timespent % 86400 ) / 3600;
		int numberOfMinutes = ((timespent % 86400 ) % 3600 ) / 60;
		int numberOfSeconds = ((timespent % 86400 ) % 3600 ) % 60;
		return (numberOfHours+9) + ":" + numberOfMinutes + ":" + numberOfSeconds;
	}
}