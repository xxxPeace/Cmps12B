//-----------------------------------------------------------------------------
// Queue.java
// This Queue ADT will be based on an underlying linked listdata structure
// the queue elements are Objects
//-----------------------------------------------------------------------------

public class Queue implements QueueInterface{

   // private inner Node class
   private class Node {
      Object item;
      Node next;

      Node(Object x){
         item = x;
         next = null;
      }
   }

   // Fields for the Queue class
   private Node front;    // reference to first Node in Queue
   private Node tail;     // reference to last Node in Queue
   private int numItems;  // number of items in this Queue

   // Queue()
   // constructor for the IntegerList class
   public Queue(){
      front = null;
      tail = null;
      numItems = 0;

   }

   // private helper function -------------------------------------------------

   // isEmpty()
   // pre: none
   // post: returns true if this Queue is empty, false otherwise
   public boolean isEmpty(){
      return (numItems == 0);
   }

   // length()
   // pre: none
   // post: returns the length of this Queue.
   public int length(){
      return numItems;
   }

   // enqueue()
   // adds newItem to back of this Queue
   // pre: none
   // post: !isEmpty()
   public void enqueue(Object newItem){
      Node N = front;
      Node T = tail;
         if(numItems == 0){
            front = new Node(newItem);
            tail = front;
          }else{
            T.next = new Node(newItem);
            tail = T.next;
          }
          numItems++;
   }

   // dequeue()
   // deletes and returns item from front of this Queue
   // pre: !isEmpty()
   // post: this Queue will have one fewer element
   public Object dequeue() 
      throws QueueEmptyException{
      if (isEmpty()){
         throw new QueueEmptyException(
             "Queue Error: dequeue() called on the queue is empty");
      }
      Object returnObject = front.item;
      front = front.next;
      numItems--;
      return returnObject;
   }

   // peek()
   // pre: !isEmpty()
   // post: returns item at front of Queue
   public Object peek() 
      throws QueueEmptyException{
      if (isEmpty()){
         throw new QueueEmptyException(
             "Queue Error: peek() called on the queue is empty");
      }
      return front.item;
   }

   // dequeueAll()
   // sets this Queue to the empty state
   // pre: !isEmpty()
   // post: isEmpty()
   public void dequeueAll() 
      throws QueueEmptyException{
      if (isEmpty()){
         throw new QueueEmptyException(
            "Queue Error: dequeueAll() called on the queue is empty");
      }
      front = null;
      numItems = 0;
   }


   //--------------------------------------------------------------------------
   // This is a another version of toString() that uses recursion.  A private
   // recursive function called printForward() does the real work, then is called
   // by toString()
   private String printForward(Node H){
      if( H == null )
         return "";
      else
         return (H.item.toString()+ " " + printForward(H.next));
   }

   // toString()
   // overrides Object's toString() method
   public String toString(){
      return printForward(front);
   }
}