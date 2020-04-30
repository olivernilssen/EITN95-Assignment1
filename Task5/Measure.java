import java.util.Random;

//this class is used to measure all batches in all queues and input it into a .m file
public class Measure extends Proc {
    
	SimpleFileWriter Cw = new SimpleFileWriter("Task5/customers1-random.m", false); //customer in queue 
    SimpleFileWriter Tw = new SimpleFileWriter("Task5/timeinQ1-random.m", false); //time spent in queue

	//variables for measuring timespent and customers
	private double accumulated = 0, accumulatedTime = 0;
	private double noMeasurements = 0, noMeasurementsT = 0;
	private Random slump = new Random();


    public void TreatSignal(Signal x){
		switch (x.signalType){
			case MEASUREC:{
				if (startMeasuring){	
					measureCustomers();
				}
			}
			break;
			case MEASURET:{
				if (startMeasuring){	
					measureTime();
				}
			}
			break;
		}
	}
    
    
	private void measureCustomers(){
		noMeasurements++; 
		for (QS q : allQueues){
			accumulated += q.queue.size();
		}

		Cw.println(String.valueOf((accumulated/allQueues.length)/noMeasurements));

		SignalList.SendSignal(MEASUREC, this, time + 2*slump.nextDouble());

	}

	private void measureTime(){
		noMeasurementsT++; 
		for (QS q : allQueues){
			accumulatedTime += (q.allTimes/q.leftQ);
		}

		Tw.println(String.valueOf((accumulatedTime/allQueues.length)/noMeasurementsT));

		SignalList.SendSignal(MEASURET, this, time + 2*slump.nextDouble());
	}
}