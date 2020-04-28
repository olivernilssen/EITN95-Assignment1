import java.util.*;
import java.util.LinkedList; 
import java.util.Queue; 

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class T6QS extends T6Proc{
	public int Qnumb, totalArrivals = 0;
	public int accumulated = 0, noMeasurements = 0 , leftQ = 0;
	public T6Proc sendTo;
	public int serviceMin = (10*60), serviceMax = (20*60);
	public double timeSpent = 0;
	Random slump = new Random();

	//queue too keep track of all customers
	Queue<T6Prescription> prescriptionQ = new LinkedList<>();

	//exponential arrival time, get next value
	public double service() {
		double serviceTime = serviceMin + (serviceMax - serviceMin) * slump.nextDouble();
		return serviceTime;
	}

	public void resetDay(){
		timeSpent = 0;
		accumulated = 0;
		noMeasurements = 0;
		leftQ = 0;
		totalArrivals = 0;
	}

	public void TreatSignal(T6Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				totalArrivals++;
				prescriptionQ.add(new T6Prescription(totalArrivals, time));
				
				if(time < ENDDAY){
					if (prescriptionQ.size() == 1){
						T6SignalList.SendSignal(READY, this, time + service());
					}
				}
			} break;

			case READY:{
				T6Prescription removed = prescriptionQ.remove();
				timeSpent += (time - removed.startTime);
				leftQ++;

				if (prescriptionQ.size() > 0){
					T6SignalList.SendSignal(READY, this, time + service());
				}

				if (prescriptionQ.size() == 0 && time >= ENDDAY){
					finished = true;
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				accumulated += prescriptionQ.size();
				
				T6SignalList.SendSignal(MEASURE, this, time + 30);
			} break;
		}
	}
}