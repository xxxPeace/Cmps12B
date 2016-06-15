//-----------------------------------------------------------------------------
// TestQueue.java
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class TestQueue{

   public static void main(String[] args) throws IOException{
      Queue test = new Queue();
      try{
        test.peek();
      }catch(QueueEmptyException e){
        System.out.println("Caught Exception " + e);
        System.out.println("Continuing without interuption");
      }
      for(int i =1; i < 10; i++){
        Job A = new Job(i, (i+1)*2);
        test.enqueue(A);
        System.out.println("queue length: "+test.length());
      }
      test.enqueue("String");
      test.enqueue('c');
      int num = 3;
      test.enqueue(num);
      
      System.out.println(test);
      System.out.println("peek: "+test.peek());
      System.out.println("dequeue: "+test.dequeue());
      
      System.out.println("Is queue empty?: "+test.isEmpty());
      System.out.println("queue length: "+test.length());
      System.out.println(test);
      System.out.println("dequeue three times");
      test.dequeue();
      test.dequeue();
      test.dequeue();
      System.out.println(test);
      System.out.println("Is queue empty?: "+test.isEmpty());
      System.out.println("queue length: "+test.length());
      System.out.println("dequeue all");
      test.dequeueAll();
      System.out.println("Is queue empty?: "+test.isEmpty());
      System.out.println("queue length: "+test.length());
      try{
        test.dequeue();
      }catch(QueueEmptyException e){
        System.out.println("Caught Exception " + e);
        System.out.println("Continuing without interuption");
      }

      try{
        test.dequeueAll();
      }catch(QueueEmptyException e){
            System.out.println("Caught Exception " + e);
        System.out.println("Continuing without interuption");
      }
   }
}
