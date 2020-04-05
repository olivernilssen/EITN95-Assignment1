
public class T2EventListClass {
	
	private T2Event list, last; // Used to build a linked list
	
	T2EventListClass(){
		list = new T2Event();
    	last = new T2Event();
    	list.next = last;
	}
	
	// The method insertEvent creates a new event, and searches the list of events for the 
	// right place to put the new event.
	public void InsertEvent(int type, double TimeOfEvent){
 	T2Event dummy, predummy;
 	T2Event newEvent = new T2Event();
 	newEvent.eventType = type;
 	newEvent.eventTime = TimeOfEvent;
 	predummy = list;
 	dummy = list.next;
 	while ((dummy.eventTime < newEvent.eventTime) & (dummy != last)){
 		predummy = dummy;
 		dummy = dummy.next;
 	}
 	predummy.next = newEvent;
 	newEvent.next = dummy;
 }
	
	// The following method removes and returns the first event in the list. That is the
	// event with the smallest time stamp, i.e. the next thing that shall take place.
	public T2Event fetchEvent(){
		T2Event dummy;
		dummy = list.next;
		list.next = dummy.next;
		dummy.next = null;
		return dummy;
	}
}