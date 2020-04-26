import java.util.*;
import java.io.*;


//It inherits Proc so that we can use time and the signal names without dot notation 
class Gen extends Proc{
	//The random number generator is started:
	Random slump = new Random();

	//There are two parameters:
	public Proc sendTo;  //Says to which process we want to send customers
	public double lambda;  //uniform arrival time

	//What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:{
				SignalList.SendSignal(ARRIVAL, chooseQueue(sendTo), time);
				SignalList.SendSignal(READY, this, time + lambda*slump.nextDouble());
			} //(2.0/lambda)*slump.nextDouble() -> poisson?
			break;
		}
	}

	public static QS chooseQueue(int method) {
		Random rand = new Random();
		QS chosenQ = null;

		if (method == RAND) {
			chosenQ = allQueues[rand.nextInt(allQueues.length)];
			return chosenQ;
		} else if (method == ROUNDROBIN) {
			chosenQ = allQueues[RR - 1];
			RR++; 
			if (RR == 6){ RR = 1;}
			return chosenQ;
		}else if (method == SHORTEST){
			chosenQ = allQueues[0];
			for (QS queue : allQueues){
				if (chosenQ != queue) {
					if (chosenQ.numberInQueue < queue.numberInQueue){
						chosenQ = queue;
					}
					else if (chosenQ.numberInQueue == queue.numberInQueue){
						chosenQ = (rand.nextInt() % 2 == 0) ? chosenQ : queue;
						System.out.println(chosenQ);
					}
				}
			}
		}
		return allQueues[0];
	}
}