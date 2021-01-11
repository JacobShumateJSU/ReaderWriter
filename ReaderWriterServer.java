/**
 * Jacob Shumate
 * November 12, 2020
 * Server class
 * 
 * This program is an implementation of a solution for the readers writers problem where mutual exclusion is needed
 * to ensure that the is not conflicting access to the critical section. The issue with the first solution using 
 * syncrhonization with wait and notify methods did not prevent writer starvation. To prevent this a boolean variable called 
 * writerRunning was defined in Reader.java and it used as a medium to check if there is an active writer thread scheduled to be ran
 * and if so, cause the reading thread to sleep until the writer is not running anymore
 */


public class ReaderWriterServer
{
   public static void main(String args[])
   {
      Database server = new Database();

      Reader[] readerArray = new Reader[NUM_OF_READERS];
      Writer[] writerArray = new Writer[NUM_OF_WRITERS];

      //creates array of reader threads
      for (int i = 0; i < NUM_OF_READERS; i++)
      {
         readerArray[i] = new Reader(i, server);
         //attaches string "reader" to thread name so that it can be identified in Reader.java to distinguish reader and writer threads
         readerArray[i].setName("Reader "+i);
         readerArray[i].start();
      }
      //creates array of writer threads
      for (int i = 0; i < NUM_OF_WRITERS; i++)
      {
         writerArray[i] = new Writer(i, server);
         readerArray[i].setName("Writer "+i);
         writerArray[i].start();
      }
   }

   private static final int NUM_OF_READERS = 15;
   private static final int NUM_OF_WRITERS = 3;
}
