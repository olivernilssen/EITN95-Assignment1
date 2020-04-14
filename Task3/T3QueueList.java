import java.util.Iterator;

/* LinkedQueue.java */
class T3QueueList <Item> extends T3GlobalSimulation implements T3Queue <Item>
{
  private Customer front, rear; //begin and end Customers
  private int size; // number of items
 
  //nested class to define Customer
  private class Customer
  { 
    Item item;
    Double startTime;
    Customer next;
    
  }
 
  //Zero argument constructor
  public T3QueueList()
  {
    front = null;
	  rear = null;
    size = 0;
  }
 
  public boolean isEmpty()
  {
    return (size == 0);
  }
 
  //Remove item from the beginning of the list.
  public Item delete()
  {
    Item item = front.item;
    front = front.next;
    if (isEmpty()) 
    {
      rear = null;
    }
    size--;
    return item;
  }
 
  //Add item to the end of the list.
  public void insert(Item item, double startTime)
  {
    Customer oldRear = rear;
    rear = new Customer();
    rear.item = item;
    rear.startTime = startTime;
    rear.next = null;
    
    if (isEmpty()) 
    {
      front = rear;
    }
    else 
    {
      oldRear.next = rear;
    }
    size++;
  }
  
  //Iterator for traversing queue items
  public Iterator<Item> iterator()
  {
    return new LinkedQueueIterator();
  }
 
  //inner class to implement iterator interface
  private class LinkedQueueIterator implements Iterator <Item>
  {
    private int i = size;
    private Customer first = front; //the first node
 
    public boolean hasNext()
    {
      return (i > 0);
    }

    public Item next()
    {
      Item item = first.item;
      first = first.next;
      i--;
      return item;
    }
  }

  public int size()
  {
    return size;
  }

  public double startTime()
  {
    return front.startTime;
  }

  public Item jobnr()
  {
    return front.item;
  }

}