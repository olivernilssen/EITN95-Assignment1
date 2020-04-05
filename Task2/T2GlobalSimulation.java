public class T2GlobalSimulation{
	
	// This class contains the definition of the events that shall take place in the
	// simulation. It also contains the global time, the event list and also a method
	// for insertion of events in the event list. That is just for making the code in
	// MainSimulation.java and State.java simpler (no dot notation is needed).
	
	public static final int ARRIVAL = 1, JOBA = 2, JOBB = 3, ENDA = 4, ENDB = 5, DELAY = 6, MEASURE = 7; // The events, add or remove if needed!
	public static double time = 0; // The global time variable
	public static T2EventListClass T2eventList = new T2EventListClass(); // The event list used in the program
	public static void insertEvent(int type, double TimeOfEvent){  // Just to be able to skip dot notation
		T2eventList.InsertEvent(type, TimeOfEvent);
	}


}