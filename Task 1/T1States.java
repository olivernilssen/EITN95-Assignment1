import java.util.*;

class T1State extends T1GlobalSimulation{
	//variables for simulation
	private int numbQ1 = 0, numbQ2 = 0;
	private double Q1_st = 2.1, Q2_ts = 2, m = 5, arrivalrate = 1; //st = service time

	//variables for measuring
	public int rejected = 0, accumulated = 0, noMeasurements = 0, totalArrivals = 0;

	//variables for warmup
	private int unsteadyMeasurment = 0, steady = 0; 
	private double mean = 0; 
	private boolean warmup = true;

	Random slump = new Random(); // This is just a random number generator

	//calcualte the different exponentially distributed times
	public double nextExpo(double mean){
		return Math.log(1-slump.nextDouble())/-(1/mean);
	}
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(T1Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPART1:
				departure1();
				break;
			case DEPART2:
				departure2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	//arrival method, add arrival to queue1
	//reject if the queue is full
	//start new departure if queue == 1
	private void arrival(){
		if (numbQ1 == 0)
			insertEvent(DEPART1, time + nextExpo(Q1_st));
		
		if (numbQ1 >= MAXQ1)
			rejected += 1;
		else
			numbQ1++;
			totalArrivals++;
		insertEvent(ARRIVAL, time + arrivalrate);
	}
	
	//remove 1 from queue 1 and add it to queue 2
	//start new departure fore queue 2 
	private void departure1(){
		numbQ1--;
		numbQ2++;
		if (numbQ2 == 1)
			insertEvent(DEPART2, time + Q2_ts);
		if (numbQ1 > 0)
			insertEvent(DEPART1, time + nextExpo(Q1_st));
	}

	//remove from queue 2
	//start new departure if list is not zero
	private void departure2(){
		numbQ2--;
		if (numbQ2 > 0)
			insertEvent(DEPART2, time + Q2_ts);
		
	}
	
	//Don't start measuring before reacing steady-state if possible.
	//Checks the variance between old mean and new mean to get it as low as possible for 
	//a set amount of measurments before we start taking samples for our test
	private void measure() {
		//check if warmup time is still true
		if (warmup){
			unsteadyMeasurment++;
			accumulated += numbQ2;
			double newMean = accumulated/unsteadyMeasurment;
			
			double variance = mean - newMean;

			if ((variance <= 0 && variance > -1 ) || (variance >= 0 && variance < 1)){ steady++; } 
			else{ steady = 0;}

			mean = newMean;

			//if we have been steady for more than 200 measurments, then start taking
			//the real sampels
			if(steady == 200){
				warmup = false;
				accumulated = 0;
				noMeasurements = 0;
			}
		}
		else {
			accumulated += numbQ2;
			noMeasurements++;
		}

		insertEvent(MEASURE, time + nextExpo(m));
	}
}