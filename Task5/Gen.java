import java.util.*;

//It inherits Proc so that we can use time and the signal names without dot notation 
class Gen extends Proc{
	//The random number generator is started:
	public Random slump = new Random();
	public double allR = 0, amountR = 0;
	//There are two parameters:
	public Proc sendTo;  //Says to which process we want to send customers
	public double lambda;  //uniform arrival time
	public int method;

	public double arrival(double L) {
		double random = slump.nextDouble()*(L*2); //lower-bound being 0 and upperbound being L*2
		return (random);
	}

	//What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:{
				Proc sendTo = chooseQueue(method);
				SignalList.SendSignal(ARRIVAL, sendTo, time);
				SignalList.SendSignal(READY, this, time + arrival(lambda));
			}
			break;
		}
	}

	public static QS chooseQueue(int method) {
		Random rand = new Random();
		QS chosenQ = allQueues[0];

		//randomly choose between any of the queues we have running
		if (method == RAND) {
			chosenQ = allQueues[rand.nextInt(allQueues.length)];
		} 
		//chose queue based on round robin method
		//RR goes +1 each round, if higher than 5 then it 
		//equals 0
		else if (method == ROUNDROBIN) {
			chosenQ = allQueues[RR - 1];
			RR++; 
			if (RR > allQueues.length){RR = 1;}
		}
		//find shortest queue in all queues
		else if (method == SQF){
			QS [] temp = new QS[5];
			temp[0] = allQueues[0];

			int i = 1;
			for (QS queue : allQueues){
				if (temp[0] != queue) {
					if (temp[0].queue.size() == queue.queue.size()){
						temp[i] = queue;
						i++;
					}
					else if (temp[0].queue.size() > queue.queue.size()){
						temp = new QS[5];
						temp[0] = queue;
						i = 1;
					}
				}
			}
			//if only one queue has lowest number, chosen that one
			//else randomly choose between all queues with the lowest number
			if (i == 1){
				chosenQ = temp[0];
			}
			else {
				chosenQ = temp[rand.nextInt(i)];
			}
		}
		return chosenQ;
	}
}