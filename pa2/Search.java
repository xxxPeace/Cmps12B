//-----------------------------------------------------------------------------
// Search.java
// Find the word is in the file or not, and return the index 
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

class Search {

   public static void mergeSort(String[] A, int[] lineNumber, int p, int r){
      int q;
      if(p < r) {
         q = (p+r)/2;
         // System.out.println(p+" "+q+" "+r);
         mergeSort(A, lineNumber, p, q);
         mergeSort(A, lineNumber, q+1, r);
         merge(A, lineNumber, p, q, r);
      }
   }

   // merge()
   // merges sorted subarrays A[p..q] and A[q+1..r]
   public static void merge(String[] A, int[] lineNumber, int p, int q, int r){
      int n1 = q-p+1;
      int n2 = r-q;
      String[] L = new String[n1];
      String[] R = new String[n2];
      int[] leftNum = new int[n1];
      int[] rightNum = new int[n2];
      int i, j, k;

      for(i=0; i<n1; i++){
         L[i] = A[p+i];
         leftNum[i] = lineNumber[p+i];
      }
      for(j=0; j<n2; j++){ 
         R[j] = A[q+j+1];
         rightNum[j] = lineNumber[q+j+1];
      }
      i = 0; j = 0;
      for(k=p; k<=r; k++){
         if( i<n1 && j<n2 ){
            if( L[i].compareTo(R[j])<0 ){
               A[k] = L[i];
               lineNumber[k] = leftNum[i];
               i++;
            }else{
               A[k] = R[j];
               lineNumber[k] = rightNum[j];
               j++;
            }
         }else if( i<n1 ){
            A[k] = L[i];
            lineNumber[k] = leftNum[i];
            i++;
         }else{ // j<n2
            A[k] = R[j];
            lineNumber[k] = rightNum[j];
            j++;
         }
      }
   }

   public static int binarySearch(String[] A, int p, int r,  String target){
      int q;
      if(p > r) {
         return -1;
      }else{
         q = (p+r)/2;
         if(target.compareTo(A[q]) == 0) {
            return q;
         }else if(target.compareTo(A[q])<0) {
            return binarySearch(A, p, q-1, target);
         }else{ // target > A[q]
            return binarySearch(A, q+1, r, target);
         }
      }
   }

   public static void main(String[] args) throws IOException {
      //get the line count of the input file
      Scanner in = new Scanner(new File(args[0]));
      int lineCount = 0;
      while( in.hasNextLine() ){
         in.nextLine();
         lineCount++;
      }
      in.close();

      //Creat an array and read all the line from the file to the array
      String[] lines = new String[lineCount];
      int index = 0;
      
      // count the number of lines in file
      Scanner input = new Scanner(new File(args[0]));
      while( input.hasNextLine() ){
         lines[index] = input.nextLine();
         index++;
      }
      input.close();
      
      //Creat an array and assign the unmber to the array
      int[] listOfNum = new int[lineCount];
      for (int i = 0; i< listOfNum.length; i++){
         listOfNum[i] = i+1;
      }
      /*for (int i =0; i<listOfNum.length; i++){
         System.out.println(listOfNum[i]);
      }*/

      //Sort the String array and int array
      mergeSort(lines, listOfNum, 0, lines.length-1);

      for(int i = 1; i<args.length; i++){
         //check the word is found or not
         int checker = binarySearch(lines, 0, lines.length-1,args[i]);
         if( checker >= 0){
            System.out.println(args[i] +" found on line "+ listOfNum[checker]);
         }else{
            System.out.println(args[i] +" not found");
         }
      }      
   }
}
