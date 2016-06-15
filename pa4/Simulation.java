//-----------------------------------------------------------------------------
// SimulationStub.java
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class Simulation{

   public static Job getJob(Scanner in) {
      String[] s = in.nextLine().split(" ");
      int a = Integer.parseInt(s[0]);
      int d = Integer.parseInt(s[1]);
      return new Job(a, d);
   }

   public static void jobEnqueue(int m, Scanner in, Queue job, Queue copyQueue) {
      for(int i = 0; i < m; i++){
         Job A = getJob(in);
         job.enqueue(A);
         copyQueue.enqueue(A);
      }
   }

   public static void main(String[] args) throws IOException{
      Scanner in = null;
      PrintWriter report = new PrintWriter(new FileWriter(args[0]+".rpt"));
      PrintWriter trace = new PrintWriter(new FileWriter(args[0]+".trc"));
      int m;
      int time=0;
      int totalWait = 0, maxWait = 0;
      int samllQuene;
      int temSamllIndex;
      float averageWait;
      Job temJob,finishJob;
      boolean keepDoing = true;
      boolean allQueueFull = false;
      boolean toPrint = true;


      // check number of command line arguments is at least 1
      if(args.length < 1){
         System.out.println("Usage: TestQueue <input file>");
         System.exit(1);
      }
      
      // open files
      in = new Scanner(new File(args[0]));
      m = Integer.parseInt(in.nextLine());//get the number of jobs

      //allocate memory
      Queue[] queueArray = new Queue[m]; 
      for (int i = 0; i<m; i++){
         queueArray[i] = new Queue();
      }
      Queue copyQueue = new Queue();

      //get the input from input file and push into tow queues
      //one is in queue aarray [0], one for the copy of the input file
      //jobEnqueue(m,in,queueArray[0],copyQueue);

      for(int i = 0; i < m; i++){
         Job A = getJob(in);
         queueArray[0].enqueue(A);
         copyQueue.enqueue(A);
      }
      //output to the file
      report.println("Report file: " + args[0]+".rpt");
      report.println(m+" Jobs:");
      report.println(copyQueue);
      report.println();
      report.println("***********************************************************");

      trace.println("Trace file: " + args[0]+".trc");
      trace.println(m+" Jobs:");
      trace.println(copyQueue);
      trace.println();

      //start at 1 processor to m-1 processors
      for(int processor = 1; processor <= m-1; processor++){
              
        trace.println("*****************************");
        if(processor<2) trace.println(processor+" processor:");
        else trace.println(processor+" processors:");
        trace.println("*****************************");
        
        while(keepDoing){
           //-------------------------------------------------------------------
           //
           // part1 check all the processor isempty? is true and some jobs 
           // is finished then dequeue the job from the processor queue
           // compute the WaitTime, and maxWait then push back to the job queue
           //
           //-------------------------------------------------------------------
           for(int n = 1; n< m; n++){
             if(!queueArray[n].isEmpty()){
                if(queueArray[n].peek() instanceof Job){
                   temJob = (Job)queueArray[n].peek();

                   if(temJob.getFinish() == time){
                      toPrint = true;
                      //System.out.println("time="+time);
                      finishJob = (Job)queueArray[n].dequeue();

                      int tempWaitTime =  finishJob.getWaitTime();
                      //Get the max waiting time
                      if (tempWaitTime > maxWait) maxWait = tempWaitTime;
                      //Calculate the total waiing time                    
                      totalWait+=tempWaitTime;
                      //The jos is finished push back to the queue array[0].
                      queueArray[0].enqueue(finishJob);
                      //Look at the next job if has one then compute the finish time
                      if(!queueArray[n].isEmpty()){
                         temJob = (Job)queueArray[n].peek();
                         temJob.computeFinishTime(time);
                      }
                   }
                }
             }
          }
          //-------------------------------------------------------------------
          //       
          // part two, check all the in the job queue, if is arrival time 
          // dequeue the job from the queue and push to the empty processor 
          // and compute the finish time, if all processor not empty the job
          // push to the queue which contains less
          //
          //-------------------------------------------------------------------
          for(int n = 1; n<= m; n++){
            if(!queueArray[0].isEmpty()){
                if(queueArray[0].peek() instanceof Job){
                    temJob = (Job)queueArray[0].peek();
                    if(temJob.getArrival() == time){
                       //set toPrint equal true
                       toPrint = true;
                       temJob=(Job)queueArray[0].dequeue();
                       for(int i =1; i <=processor; i++){
                          if(queueArray[i].isEmpty()){
                             temJob.computeFinishTime(time);
                             queueArray[i].enqueue(temJob);
                             allQueueFull = false; 
                             break;
                          }else allQueueFull = true;                                       
                       }
                       if(allQueueFull){
                          samllQuene = queueArray[1].length();
                          //set the small index to state 1
                          temSamllIndex = 1;
                          for(int sn =1; sn<processor; sn++){                              
                             if (samllQuene > queueArray[sn+1].length()) {
                                 samllQuene = queueArray[sn+1].length();                                                              
                                 temSamllIndex = sn+1;
                              }
                          }
                          queueArray[temSamllIndex].enqueue(temJob);
                          allQueueFull = false;
                       }
                    }
                }
            }
          }//end for loop

          //output trace
          if(toPrint){
             trace.println("time="+time);
             //System.out.println("time="+time);
             for(int i = 0; i <= processor; i++){
                trace.println(i + ":"+queueArray[i]);
                //System.out.println(i + ":"+queueArray[i]);
             }
             trace.println();
             //System.out.println();
             toPrint = false;
          }

          time++;//increaming the time

          //check all the job is done
          if(!queueArray[0].isEmpty()){
             temJob = (Job)queueArray[0].peek();
             if(temJob.getFinish() == -1){
                keepDoing = true;
             }else{
                for(int check = 1; check < m; check++){
                   if(queueArray[check].isEmpty()){
                      keepDoing = false;
                   }else{
                      keepDoing = true;
                      break;
                   }
                 //System.out.println("end");
                }
             }
          }      
        }//end while

        //reste the finsh time to simulation with more processors
        for(int i =0; i < queueArray[0].length(); i++){
           temJob = (Job)copyQueue.dequeue();
           temJob.resetFinishTime();
           copyQueue.enqueue(temJob);
           queueArray[0].dequeue();
           queueArray[0].enqueue(temJob);       
        }
        
        averageWait = (float)totalWait/(float)m;
        //out put the report
        if(processor <2) report.println(processor+" processor: totalWait="+totalWait+", maxWait="+maxWait+", averageWait="+String.format("%.2f", averageWait));
        else report.println(processor+" processors: totalWait="+totalWait+", maxWait="+maxWait+", averageWait="+String.format("%.2f", averageWait));

        //reset valuables
        time = 0;
        keepDoing = true;
        toPrint = true;
        totalWait = 0;
        maxWait = 0;
      }//end for loop
        
      report.close();
      trace.close();
   }//end main
}//end class
