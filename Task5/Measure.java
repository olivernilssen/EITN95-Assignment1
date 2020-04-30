import java.util.Random;

//this class is used to measure all batches in all queues and input it into a .m file
public class Measure extends Proc {
    
	SimpleFileWriter Cw = new SimpleFileWriter("Task5/customers3-RR.m", false); //customer in queue 
    SimpleFileWriter Tw = new SimpleFileWriter("Task5/timeinQ3-RR.m", false); //time spent in queue

	//variables for measuring timespent and customers
	private double accumulated = 0;
	private Random slump = new Random();

	private int batchSizeT = 12, batchSizeC = 15;
	private double batchT = 0, batchC = 0; 
	public double allBatchT = 0, allBatchC = 0;
	private double noBatchesT = 0, noBatchesC = 0;
	private int countT = 1, countC = 0;

    public void TreatSignal(Signal x){
		switch (x.signalType){
			case MEASUREC:{
				if (startMeasuring){	
					measureCustomers();
				}

				SignalList.SendSignal(MEASUREC, this, time + 2*slump.nextDouble());
			}
			break;
			case MEASURET:{
				if (startMeasuring && allQueues[0].leftQ > 5){	
					measureTime();
				}
				SignalList.SendSignal(MEASURET, this, time + 2*slump.nextDouble());
			}
			break;
		}
	}
    
	private void measureCustomers(){
		accumulated = 0;
		for (QS q : allQueues){
			accumulated += q.queue.size();
		}

		batchC += accumulated;
		countC++; 

		if (countC > batchSizeC){
			allBatchC += (batchC/batchSizeC);
			noBatchesC++;
			countC = 1;
			batchC = 0;
			Cw.println(String.valueOf(allBatchC/noBatchesC));
		}
	}

	private void measureTime(){
		accumulated = 0;
		for (QS q : allQueues){
			accumulated += (q.finishTimes/q.leftQ);
		}

		batchT += accumulated;
		countT++; 

		if (countT > batchSizeT){
			double mean = batchT/batchSizeT;
			allBatchT = allBatchT + mean;
			noBatchesT++;
			countT = 1;
			batchT = 0;
			Tw.println(String.valueOf(allBatchT/noBatchesT));
		}
	}
}