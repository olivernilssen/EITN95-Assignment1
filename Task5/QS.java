import java.util.*;
import java.util.LinkedList; 
import java.util.Queue;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	//variables for simulation
	public int Qnumb;
	private double s = 0.5; //servicetimes 
	private int totalArrivals = 0;
	public Proc sendTo;
	public Proc measurproc;

	//variables for measuring timespent
	public int leftQ = 0, numBatchesT = 0, countT = 0;
	public double timeSpent = 0, batchT = 0, allBatchesT = 0, finishTimes = 0;
	Random slump = new Random();

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

				finishTimes += (time - removed.startTime);
				leftQ++;
			
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (queue.size() > 0){
					SignalList.SendSignal(READY, this, time + getExpo(s));
				}
			} break;
		}
	}
}