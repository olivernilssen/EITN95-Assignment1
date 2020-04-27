import java.util.*;

class T2State extends T2GlobalSimulation {
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements, runningjobs and lambda
	private double d = 1, lambda = 150, m = 0.1, mean = 0;
	public int accumulated = 0, noMeasurements = 0, unsteadyMeasurment = 0;
	public int totalArrivals = 0, steady = 0;
	public boolean Arunning = false, Brunning = false, warmup = true;
	public int queue_A = 0, queue_B = 0;
	public double Xa = 0.002, Xb = 0.004;

	Random slump = new Random(); // This is just a random number generator

	public double poissonArrival(double L) {
		return (Math.log(1.0-Math.random())/-L);
	}
	
	T2SimpleFileWriter W = new T2SimpleFileWriter("Task2/customersB.m", false);

	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T2Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case ACTION:
				action();
				break;
			case DELAY:
				delay();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	// The following methods defines what should be done when an event takes place. 
	private void arrival(){
		totalArrivals++;
		queue_A++;

		if (queue_A == 1 && Brunning == false && queue_B == 0){ // && queue_B == 0
			insertEvent(ACTION, time + Xa);
			Arunning = true;
		}

		insertEvent(ARRIVAL, time + poissonArrival(lambda));
	}

	//depending on which job is first in the queue, decided which action to take
	//if the first queue object is JOBA, then delete the job, if it is JOBA, 
	//we put it in the delay queue and start an action to remove it from delay
	private void action(){
		if (Arunning){
			Arunning = false;
			queue_A--;
			
			insertEvent(DELAY, time + d);
		}
		else if (Brunning){
			Brunning = false;
			queue_B--;
		}
		
		if (queue_B > 0){
			Brunning = true;
			insertEvent(ACTION, time + Xb);
		}
		else if(queue_A > 0){
			Arunning = true;
			insertEvent(ACTION, time + Xa);
		}
	}

	private void delay(){
		queue_B++;

		if (!Arunning && !Brunning ){
			if (queue_B > 0) {
				Brunning = true;
				insertEvent(ACTION, time + Xb);
			}
			else if(queue_A > 0){
				Arunning = true;
				insertEvent(ACTION, time + Xa);
			}
		}
	}
	
	private void measure(){
		//check if warmup time is still true
		if (warmup){
			unsteadyMeasurment++;
			accumulated += queue_A + queue_B;
			double newMean = accumulated/unsteadyMeasurment;
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -2) || (variance >= 0 && variance < 2)) {
				steady++;
			}
			else {
				steady = 0;
			}

			mean = newMean;

			//if we have been steady for more than 500 measurments, then start taking
			//the real sampels
			if(steady == 200){
				warmup = false;
				accumulated = 0;
				noMeasurements = 0;
			}
		}
		else {
			accumulated += queue_A + queue_B;
			noMeasurements++;

			W.println(String.valueOf(queue_A + queue_B));
		}
		
		insertEvent(MEASURE, time + m);
	}
}