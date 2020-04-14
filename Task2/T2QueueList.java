import java.util.Iterator;

/* LinkedQueue.java */
class T2QueueList <Item> extends T2GlobalSimulation implements T2Queue <Item>{
  private Customer front, rear; // begin and end Customers
  private int size; // number of items
  private double Xa = 0.002, Xb = 0.004;
 
  //nested class to define Customer
  private class Customer{ 
    Item jobItem;
    int jobNumber;
    int priority;
    double startTime;
    double actionTime;
    Customer next;
  }
 
  //Zero argument constructor
  public T2QueueList(){
    front = null;
	  rear = null;
    size = 0;
  }
 
  public boolean isEmpty(){
    return (size == 0);
  }
 
  //Remove item from the beginning of the list.
  public double[] delete(){
    // System.out.println("size before delete: " + size);

    Customer temp = front;
    front = front.next;
    
    if (isEmpty()) {
      rear = null;
    }

    double [] customerID = new double[2];
    customerID[0] = (double) temp.jobNumber;
    customerID[1] = temp.startTime;

    size--;
    return customerID;
  }

  public double actionTime(){
    return front.actionTime;
  }
 
  //Add item to the end of the list.
  public void insert(Item jobItem, int priority, double startTime, boolean ARunning){
    Customer temp = new Customer ();
    Customer oldFront = front;
    // System.out.println("job priority " + priority + " job number: " + (int) jobItem);
    temp.jobItem = jobItem;
    temp.priority = priority;
    temp.startTime = startTime;
    temp.jobNumber = (int) jobItem;
    temp.next = null;

    int p = priority; //p = priority; lower number = higher priority

    if (p == JOBA) {
      temp.actionTime = Xa;
    } else {
      temp.actionTime = Xb;
    }

    if (front == null) {
      rear = temp;
      (front) = rear;
    }
    else if (p == 2 || p == 0){
      temp.next = null;
      rear.next = temp;
      rear = temp;
    }
    else if ((front).priority > p && !ARunning) {  
      // Insert New Node before head
      temp.next = front;  
      (front) = temp;  
    }
    else {
      boolean done = false;
      if(ARunning)
      {
        if (front.next == null){
          rear = temp;
          front.next = rear;
          done = true;
        }
        else{
          front = front.next;
        }
      }

      if (!done){
        while (front.next != null && front.next.priority < p) {  
          front = front.next; 
        }

        if(front.next == null){
          front.next = rear;
        }
        
        temp.next = front.next;  
        front.next = temp; 
        front = oldFront;
        }
      }
    size++;
  }
  
  //Iterator for traversing queue items
  public Iterator<Item> iterator(){
    return new LinkedQueueIterator();
  }
 
  //inner class to implement iterator interface
  private class LinkedQueueIterator implements Iterator <Item>{
    private int i = size;
    private Customer first = front; //the first node
 
    public boolean hasNext(){
      return (i > 0);
    }

    public Item next(){
      Item item = first.jobItem;
      first = first.next;
      i--;
      return item;
    }
  }

  public int size(){
    return size;
  }

  public double startTime(){
    return front.startTime;
  }

  public int priority() {
    return front.priority;
  }

  public int jobNumber() {
    return front.jobNumber;
  }
}