import java.util.*;
import java.util.LinkedList; 
import java.util.Queue; 

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int Qnumb, totalArrivals = 0;
	public int accumulated = 0, noMeasurements = 0 , leftQ = 0;
	public Proc sendTo;
	public double serviceTime = 0.5, timeSpent = 0;
	Random slump = new Random();

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
					SignalList.SendSignal(READY, this, time + getExpo(serviceTime));
				}
			} break;

			case READY:{
				Customer removed = queue.remove();
				timeSpent += (time - removed.startTime);

				leftQ++;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (queue.size() > 0){
					SignalList.SendSignal(READY, this, time + getExpo(serviceTime));
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				if(accumulated + queue.size() < 0) {
					System.out.println("what the fuck? " + accumulated + " " + queue.size());
				}
				accumulated += queue.size();
				
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
}