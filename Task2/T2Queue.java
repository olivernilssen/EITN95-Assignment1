public interface T2Queue <Item> extends Iterable <Item>
{
    
  double[] delete(); // removes an item from the front of the queue
  void insert(Item jobItem, int priority, double time, boolean Arunning); // adds an item to the rear end of the queue
  boolean isEmpty(); // returns true if queue is empty, false otherwise
  int size();  // returns the number of items in the queue right now
  double startTime();
  int priority();
  double actionTime();
  int jobNumber();
}