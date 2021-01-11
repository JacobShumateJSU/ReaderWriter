/**
 * Jacob Shumate
 * November 12, 2020
 * Database class
 */

 
public class Database
{  
   public Database()
   {
      readerCount = 0;
      dbReading = false;
      dbWriting = false;
   }

   // readers and writers will call this to nap
   public static void napping()
   {
     int sleepTime = (int) (NAP_TIME * Math.random() );
     try { Thread.sleep(sleepTime*1000); } 
     catch(InterruptedException e) {}
   }


   // reader will call this when they start reading
   public synchronized int startRead()
   { 
      while (dbWriting == true)
      {
         try { wait(); }
         catch(InterruptedException e) {}
      }

      ++readerCount;

      // if I am the first reader tell all others
      // that the database is being read
      if (readerCount == 1)
         dbReading = true;
     
      return readerCount;
   }

   // reader will call this when they finish reading
   public synchronized int endRead()
   { 
      --readerCount;

      // if I am the last reader tell all others
      // that the database is no longer being read
      if (readerCount == 0)
         dbReading = false;
      
      notifyAll();

	System.out.println("Reader Count = " + readerCount);

      return readerCount;
   }
   
   // writer will call this when they start writing
    public synchronized void startWrite()
   { 
      while (dbReading == true || dbWriting == true)
      {
         try { wait(); }
         catch(InterruptedException e) {}
      }

      // once there are either no readers or writers
      // indicate that the database is being written
      dbWriting = true;
   }

   // writer will call this when they start writing
   public synchronized void endWrite()
   { 
      dbWriting = false;
      //RESETS WRITER RUNNING VARIABLE SO READ THREADS CAN RUN
      Reader.writerRunning=false;
      notifyAll();
   }

   // the number of active readers
   private int readerCount;

   // flags to indicate whether the database is
   // being read or written
   private boolean dbReading;
   private boolean dbWriting; 
    
   private static final int NAP_TIME = 5;
}
