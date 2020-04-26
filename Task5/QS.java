import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int Qnumb;
	public int numberInQueue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	public double lambda = 0.5;
	Random slump = new Random();

	//exponential arrival time, get next value
	public double getNext() {
		return  Math.log(1-slump.nextDouble())/(-lambda);
	}

	public void TreatSignal(Signal x){

		switch (x.signalType){
			case ARRIVAL:{
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(READY, this, time + getNext());
				}
			} break;

			case READY:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + getNext());
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
}