/**
 * Jacob Shumate
 * November 12, 2020
 * Reader class
 */

public class Reader extends Thread
{
   public Reader(int r, Database db)
   {
      readerNum = r;
      server = db;
   }
   
   public static boolean writerRunning;
   
   public void run()
   {
	   
   int c;
   Thread.State state;
   
     while (true)
      {
    	 //if no writer threads are waiting, then allow the reader thread to read
    	 if(writerRunning==false) {
    		 System.out.println("reader " + readerNum + " is sleeping.");
    		 Database.napping();

    		 System.out.println("reader " + readerNum + " wants to read.");
    		 c = server.startRead();

    		 //you have access to read from the database
    		 System.out.println("reader " + readerNum + " is reading. Reader Count = " + c);
    		 Database.napping();

    		 System.out.print("reader " + readerNum + " is done reading. ");

    		 c = server.endRead();
    		 System.out.println("reader " + readerNum + " is done reading. Count = " + c);
    	 }
    	 else {
    		 //otherwise put the thread to sleep
    		 Database.napping();
    	 }
    	 //Gets current threads name
    	 String checkWriter = Thread.currentThread().getName();
    	 //gets current thread's state, runnable, waiting, etc.
    	 state = Thread.currentThread().getState();
    	 //If it's a writer thread that is scheduled to be ran then set writerrunning to true, in order to make reader thread's wait.
    	 if(checkWriter.contains("Writer")==true && state.equals(Thread.State.RUNNABLE)) {
    		 writerRunning=true;
    	 }

      }
   }

   private Database	server;
   private int       readerNum;
}
