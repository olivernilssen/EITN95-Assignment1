// This class defines the signal list. If one wants to send more information than here,
// one can add the extra information in the Signal class and write an extra sendSignal method 
// with more parameters. 
public class T6SignalList{
	private  static T6Signal list, last;

	T6SignalList(){
    	list = new T6Signal();
    	last = new T6Signal();
    	list.next = last;
	}

	public static void SendSignal(int type, T6Proc dest, double arrtime){
		T6Signal dummy, predummy;
		T6Signal newSignal = new T6Signal();
		newSignal.signalType = type;
		newSignal.destination = dest;
		newSignal.arrivalTime = arrtime;
		predummy = list;
		dummy = list.next;
		while ((dummy.arrivalTime < newSignal.arrivalTime) & (dummy != last)){
			predummy = dummy;
			dummy = dummy.next;
		}
		predummy.next = newSignal;
		newSignal.next = dummy;
	}

	public static T6Signal FetchSignal(){
		T6Signal dummy;
		dummy = list.next;
		list.next = dummy.next;
		dummy.next = null;
		return dummy;
	}
}
