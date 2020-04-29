import java.util.*;

//It inherits Proc so that we can use time and the signal names without dot notation 
class T6Gen extends T6Proc{
	//The random number generator is started:
	Random slump = new Random();

	//There are two parameters:
	public T6Proc sendTo;  //Says to which process we want to send customers
	public double lambda;  //uniform arrival time

	public double poissonArrival(double L) {
		double seconds = lambda/3600;
		return (Math.log(1.0-Math.random())/-(seconds));
	}

	//What to do when a signal arrives
	public void TreatSignal(T6Signal x){
		switch (x.signalType){
			case READY:{
				double nextArrival = poissonArrival(lambda);
				
				if(time + nextArrival <= FINISHTIME && !finished){
					ENDOFDAY = true;
					T6SignalList.SendSignal(ARRIVAL, sendTo, time);
					T6SignalList.SendSignal(READY, this, time + nextArrival);
				}
			}
			break;
		}
	}
}