
public class T3EventListClass {
	
	private T3Event list, last; // Used to build a linked list
	
	T3EventListClass(){
		list = new T3Event();
    	last = new T3Event();
    	list.next = last;
	}
	
	// The method insertEvent creates a new event, and searches the list of events for the 
	// right place to put the new event.
	public void InsertEvent(int type, double TimeOfEvent, double startTime) {
 	T3Event dummy, predummy;
	T3Event newEvent = new T3Event();
 	newEvent.eventType = type;
	newEvent.eventTime = TimeOfEvent;
	newEvent.startTime = startTime; 
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
	public T3Event fetchEvent(){
		T3Event dummy;
		dummy = list.next;
		list.next = dummy.next;
		dummy.next = null;
		return dummy;
	}
}