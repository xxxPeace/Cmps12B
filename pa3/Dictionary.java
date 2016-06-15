//-----------------------------------------------------------------------------
// Dictionary.java
// Using Linked List to implementation Dictionary ADT
//-----------------------------------------------------------------------------

public class Dictionary implements DictionaryInterface {

   // private inner Node class
   private class Node {
      String key;
      String value;
      Node next;

      Node(String k, String v){
         key = k;
         value = v;
         next = null;
      }
   }

   // Fields for the Dictionary class
   private Node head;     // reference to first Node in List
   private int numItems;  // number of pair vaule in this Dictionary

   // Dictionary()
   // constructor for the Dictionary class
   public Dictionary(){
      head = null;
      numItems = 0;
   }


   // private helper function -------------------------------------------------


   // ADT operations ----------------------------------------------------------

   // isEmpty()
   // pre: none
   // post: returns true if this Dictionary is empty, false otherwise
   public boolean isEmpty(){
      return(numItems == 0);
   }

   // size()
   // pre: none
   // post: returns the number of elements in this Dictionary
   public int size() {
      return numItems;
   }

   // lookup()
   // returns a key vaule of that Node
   public String lookup(String k){
      Node N = head; 
      while (N != null){     
         if(N.key == k)
            return N.value;
         N = N.next;
      }
      return null;
   }
 
   // insert()
   // inserts newItem into this Dictionary at the end position
   // pre: lookup(k) == null
   // post: !isEmpty(), items to the right of newItem are renumbered
   public void insert(String k, String v)
      throws DuplicateKeyException{
      if (lookup(k) != null){
         throw new DuplicateKeyException(
                  "Dictionary Error: insert() called on invalid key: " + k);
      }
      Node N = head;
      if(numItems == 0){
         head = new Node(k,v);
      }else{
         while (N.next != null)
            N = N.next;
         N.next = new Node(k,v);
      }
      numItems++;
   }

   // delete()
   // deletes item at this key vaule from this Dictionary
   // pre: pre: lookup(k) != null
   // post: key to the right of deleted item are renumbered
   public void delete(String k)
      throws KeyNotFoundException{
      if (lookup(k) == null){
         throw new KeyNotFoundException(
            "Dictionary Error: delete() cannot delete non-existent key: " + k);
      }


      Node N = head;
      if (N.key == k)
         head = head.next;
      else{
         while(N.next.key != k)
            N = N.next;
         N.next  = N.next.next;
      }
      numItems--;
   }
   

   // makeEmpty()
   // pre: none
   // post: isEmpty()
   public void makeEmpty(){
      head = null;
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
         return (H.key +" "+ H.value + "\n" + printForward(H.next));
   }

   public String toString(){
       return printForward(head);
   }
}