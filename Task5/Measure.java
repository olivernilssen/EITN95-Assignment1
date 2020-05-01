import java.util.Random;

//this class is used to measure all batches in all queues and input it into a .m file
public class Measure extends Proc {
	private String textfile = "Task5/matlab-files/";

	private String chosenMethod;
	private String chosenMean;

	//A method to return the correct filename depending on 
	//what method we use and what mean we use
	public String returnString (String type){
		switch(global_method){
			case RAND:{
				chosenMethod = "RAND";
			}break;
			case ROUNDROBIN:{
				chosenMethod = "RR";
			}break;
			case SQF:{
				chosenMethod = "SQF";
			}
		}//End switch

		if(global_mean == 0.12) { chosenMean = "1"; } 
		else if (global_mean == 0.11) { chosenMean = "2"; }
		else if (global_mean == 0.15) { chosenMean = "3"; }
		else if (global_mean == 2.00) { chosenMean = "4"; }
		
		return textfile + type + chosenMean + "-" + chosenMethod +".m";
	}//end returnString();
	
	SimpleFileWriter Cw = new SimpleFileWriter(returnString("customers"), false); //customer in queue 
    SimpleFileWriter Tw = new SimpleFileWriter(returnString("timeinQ"), false); //time spent in queue

	//variables for measuring timespent and customers
	private double accumulated = 0;
	private Random slump = new Random();

	private int batchSizeT = 12, batchSizeC = 15;
	private double batchT = 0, batchC = 0; 
	public double allBatchT = 0, allBatchC = 0;
	public double noBatchesT = 0, noBatchesC = 0;
	private int countT = 1, countC = 0;
	private double noMeasurements = 0, steady = 0, mean = 0;
	private double unsteadyAccumulted = 0;

    public void TreatSignal(Signal x){
		switch (x.signalType){
			case WARMUP:{
				warmup();
			}

			case MEASUREC:{	
				measureCustomers();
				SignalList.SendSignal(MEASUREC, this, time + 2*slump.nextDouble());
			}
			break;
			case MEASURET:{
				measureTime();
				SignalList.SendSignal(MEASURET, this, time + 2*slump.nextDouble());
			}
			break;
		}//end switch
	}//end Treatsignal

	//method that does the warmup calculations
	//if warmup is finish, it won't be called anymore and the measuring will begin
	private void warmup(){
		accumulated = 0;
		for (QS q : allQueues){
			accumulated += q.queue.size();
		}//end for loop
	
		noMeasurements++;
		unsteadyAccumulted += accumulated;
		double newMean = unsteadyAccumulted/noMeasurements;
		double variance = mean - newMean;

		if ((variance <= 0 && variance > -1.5 ) || (variance >= 0 && variance < 1.5)) { steady++;}
		else{ steady = 0; }

		mean = newMean;

		if(steady == 100){
			startMeasuring = true;
			unsteadyAccumulted = 0;
			noMeasurements = 0;
			SignalList.SendSignal(MEASUREC, this, time + 2*slump.nextDouble());
			SignalList.SendSignal(MEASURET, this, time + 2*slump.nextDouble());
		}
		else 
		{
			SignalList.SendSignal(WARMUP, this, time + 2*slump.nextDouble());
		}//end if
	}//end warmup()

	//Method to measure customers in the queue
	//we do the measurments in batches of 15
	private void measureCustomers(){
		accumulated = 0;
		
		for (QS q : allQueues){
			accumulated += q.queue.size();
		}

		batchC += (accumulated/allQueues.length);
		countC++; 

		if (countC > batchSizeC){
			allBatchC += (batchC/batchSizeC);
			noBatchesC++;
			countC = 1;
			batchC = 0;
			Cw.println(String.valueOf(allBatchC/noBatchesC));
		}//end if()
	}//end measureCustomers


	//method to measure how long the average wait time in the system is
	//we do the measurments in batches of 12
	private void measureTime(){
		accumulated = 0;
		for (QS q : allQueues){
			accumulated += (q.finishTimes/q.leftQ);
		}

		batchT += (accumulated/allQueues.length);
		countT++; 

		if (countT > batchSizeT){
			allBatchT += batchT/batchSizeT;
			noBatchesT++;
			countT = 1;
			batchT = 0;
			Tw.println(String.valueOf(allBatchT/noBatchesT));
		}//end if()
	}//end measureTime
}//end class