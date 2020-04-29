class T6QueueList <Item > extends GlobalSimulation implements T6Queue <
Item >{
 private Customer front , rear ; // begin and end Customers
 private int size ; // number of items

 // nested class to define Customer
 private class Customer {
 Item item ;
 double startTime ;
 Customer next ;
 }

 // Zero argument constructor
 public T6QueueList (){
 front = null ;
 rear = null ;
 size = 0;
 }

 public boolean isEmpty (){
 return ( size == 0);
 }
 // Remove item from the beginning of the list .
 public Item delete (){
	Item item = front . item ;
	front = front . next ;
	if ( isEmpty ()){
		rear = null ;
	}
	size --;
	return item ;
}
  public void insert ( Item item , double startTime ){
	  // System . out. println (" Insertin " + item );
	  Customer oldRear = rear ;
	  rear = new Customer ();
	  rear . item = item ;
	  rear . startTime = startTime ;
	  rear . next = null ;
	 
	  if ( isEmpty ())
	  	{
	  front = rear ;
	  	}
	  else
	  	{
	  oldRear . next = rear ;
	  		}
	  size ++;
	  		}
	  public int size (){
	  return size ;
	  		}
	  public double startTime (){
	  return front . startTime ;
	  	}	 
	  public Item jobnr (){
	  return front . item ;
	 } 
}