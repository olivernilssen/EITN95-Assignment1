import java.util.*;

class T1State extends T1GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numbQ1 = 0, numbQ2 = 0, rejected = 0, accumulated = 0, noMeasurements = 0, totalArrivals = 0;
	public int unsteadyMeasurment = 0, steady = 0; 
	public double Q1_st = 2.1, Q2_ts = 2, arrivaltime = 5, measureMean = 5, mean = 0;
	public boolean warmup = true;

	Random slump = new Random(); // This is just a random number generator

	//calcualte the different exponentially distributed time
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
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	private void arrival(){
		if (numbQ1 == 0)
			insertEvent(DEPART1, time + nextExpo(Q1_st));
		
		if (numbQ1 >= MAXQ1)
			rejected += 1;
		else
			numbQ1++;
			totalArrivals++;
		insertEvent(ARRIVAL, time + arrivaltime);
	}
	
	private void departure1(){
		numbQ1--;
		numbQ2++;
		if (numbQ2 == 1)
			insertEvent(DEPART2, time + Q2_ts);
		if (numbQ1 > 0)
			insertEvent(DEPART1, time + nextExpo(Q1_st));
	}

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
			// System.out.println("variance between the means " + variance);

			if ((variance <= 0 && variance > -1 ) || (variance >= 0 && variance < 1)){
				steady++; 
			} else{
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
			accumulated += numbQ2;
			noMeasurements++;
		}

		insertEvent(MEASURE, time + nextExpo(measureMean));
	}
}