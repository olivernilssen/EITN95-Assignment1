public interface T6Queue <Item >{
 Item delete (); 								// removes an item from the front of the queue
 void insert ( Item item , double time ); 		// adds an item to the rear end of the queue
 boolean isEmpty(); 							// returns true if queue is empty , false otherwise
 int size (); 									// returns the number of items in the queue right now
 double startTime();
 Item jobnr();
 }