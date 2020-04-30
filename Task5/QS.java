import java.util.*;
import java.util.LinkedList; 
import java.util.Queue;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	//variables for simulation
	public int Qnumb;
	private double m = 2, s = 0.5; //measurment and servicetimes 
	private int totalArrivals = 0;
	public Proc sendTo;
	public Proc measurproc;

	//variables for warmup
	private boolean warmup = true;
	private double steady = 0, noMeasurements = 0, mean = 0, accumulated = 0;

	//variables for measuring timespent
	public int leftQ = 0, numBatchesT = 0, countT = 0;
	public double timeSpent = 0, batchT = 0, allBatchesT = 0, allTimes = 0;
	Random slump = new Random();

	//variables for batches customers
	private int batchSize = 15, countC = 0;
	public double batchC = 0, allBatchesC = 0; 
	public int numBatchesC = 0;

	//queue too keep track of all customers
	Queue<Customer> queue = new LinkedList<>();



	//exponential arrival time, get next value
	public double getExpo(double mean) {
		return Math.log(1-slump.nextDouble())/(-1/mean);
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				totalArrivals++;
				queue.add(new Customer(totalArrivals, time));
				
				if (queue.size() == 1){
					SignalList.SendSignal(READY, this, time + getExpo(s));
				}
			} break;

			case READY:{
				Customer removed = queue.remove();
				
				if(startMeasuring){
					allTimes += (time - removed.startTime);
					batchT += (time - removed.startTime);
					leftQ++;
					countT++; 
	
					if(countT > batchSize){
						numBatchesT++;
						allBatchesT += (batchT/batchSize);
						countT = 0; batchT = 0;
					}
				}
			
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (queue.size() > 0){
					SignalList.SendSignal(READY, this, time + getExpo(s));
				}
			} break;

			case MEASURE:{
				measure();
			} break;
		}
	}

	private void measure(){
		if (warmup){
			noMeasurements++;
			accumulated += queue.size();
			double newMean = accumulated/noMeasurements;
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -0.5 ) || (variance >= 0 && variance < 0.5)) { steady++;}
			else{ steady = 0; }

			mean = newMean;

			if(steady == 500){
				warmup = false;
				startMeasuring = true;
				accumulated = 0;
				noMeasurements = 0;
			}
		}
		else {
			batchC += queue.size();
			countC++;
			if (countC > batchSize){
				allBatchesC += (batchC/batchSize);
				batchC = 0;
				countC = 0; 
				numBatchesC++;
			}
		}
		
		SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
	}
}