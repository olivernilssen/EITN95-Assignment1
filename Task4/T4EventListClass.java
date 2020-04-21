
public class T4EventListClass {
	
	private T4Event list, last; // Used to build a linked list
	
	T4EventListClass(){
		list = new T4Event();
    	last = new T4Event();
    	list.next = last;
	}
	
	// The method insertEvent creates a new event, and searches the list of events for the 
	// right place to put the new event.
	public void InsertEvent(int type, double TimeOfEvent) {
 	T4Event dummy, predummy;
	T4Event newEvent = new T4Event();
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
	public T4Event fetchEvent(){
		T4Event dummy;
		dummy = list.next;
		list.next = dummy.next;
		dummy.next = null;
		return dummy;
	}
}