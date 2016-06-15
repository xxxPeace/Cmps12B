//-----------------------------------------------------------------------------
// DictionaryTest.java
// Client module for testing Dictionary ADT
//-----------------------------------------------------------------------------

public class DictionaryTest{

   public static void main(String[] args){
      String v;
      Dictionary A = new Dictionary();
      A.insert("1","a");
      System.out.println(A);
      A.delete("1");
      System.out.println(A);
      A.insert("1","a");
      A.insert("2","b");
      System.out.println(A);
      A.delete("1");
      System.out.println(A);
      A.insert("1","a");
      System.out.println(A);
      A.delete("2");
      System.out.println(A);
      System.out.println("isEmpty ?" + A.isEmpty());
      System.out.println("size : " + A.size());
      A.makeEmpty();
      System.out.println();
      A.insert("1","a");
      A.insert("2","b");
      A.insert("3","c");
      A.insert("4","d");
      A.insert("5","e");
      A.insert("6","f");
      A.insert("7","g");
      A.insert("8","peace");
      System.out.println(A);

      try{
         A.insert("2","f");  // causes DuplicateKeyException
      }catch(DuplicateKeyException e){
         System.out.println("Caught Exception " + e);
         System.out.println("Continuing without interuption");
      }

      v = A.lookup("1");
      System.out.println("key=1 "+(v==null?"not found":("value="+v)));
      v = A.lookup("4");
      System.out.println("key=3 "+(v==null?"not found":("value="+v)));
      v = A.lookup("6");
      System.out.println("key=7 "+(v==null?"not found":("value="+v)));
      v = A.lookup("8");
      System.out.println("key=8 "+(v==null?"not found":("value="+v)));
      v = A.lookup("9");
      System.out.println("key=9 "+(v==null?"not found":("value="+v)));
      System.out.println();

      
      A.delete("1");
      A.delete("4");
      A.delete("6");
      System.out.println(A);

      try{
         A.delete("6");  // causes KeyNotFoundException
      }catch(KeyNotFoundException e){
         System.out.println("Caught Exception " + e);
         System.out.println("Continuing without interuption");
      }

      System.out.println("isEmpty ?" + A.isEmpty());
      System.out.println("size : " + A.size());
      A.makeEmpty();
      System.out.println("isEmpty ?" + A.isEmpty());
      System.out.println(A);

   }
}