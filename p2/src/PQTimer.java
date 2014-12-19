/*  PQTimer.java
	Written by: Professor Alan Riggins
    CS310 Fall 2012
    Programming Assignment #2
*/

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import data_structures.*;

public class PQTimer {
    public static void main(String [] args) {
    
///////////////////////////////////////////////////////////    
/// Change this variable to something smaller if timing takes too long.
    final int INITIAL_SIZE = 15000;
///////////////////////////////////////////////////////////    

	final int ITERATIONS = 10000;
	final int NUMBER_OF_STEPS = 15;
	final int MAX_SIZE = INITIAL_SIZE * NUMBER_OF_STEPS +1;
	final int NUMBER_OF_PRIORITIES = 20;
	
	int size = INITIAL_SIZE;      
    long sequence_number = 0;
	long start, stop;
    int priority;

        
        PQTestObject [] array = new PQTestObject[MAX_SIZE];
        for(int i=0; i < MAX_SIZE; i++) 
            array[i] = new PQTestObject((int) ((10000*Math.random() %
	    NUMBER_OF_PRIORITIES) +1), sequence_number++);
	    
        for(int j=0; j < 15; j++) {
            PriorityQueue<PQTestObject> queue = 
                new HeapPriorityQueue<PQTestObject>(MAX_SIZE);
    		queue.clear();
	    	for(int i = 0; i < size; i++)
		        queue.insert(array[i]);
		    
    		start = System.currentTimeMillis(); // start the timer
	    	for(int i = 0; i < ITERATIONS; i++) {
		    	queue.insert(array[(int)(100000*Math.random() % MAX_SIZE)]);
			    queue.remove();
    			}
    		stop = System.currentTimeMillis();
		
    		System.out.println("HeapPQ, Time for n=" + size + ": " + (stop-start));

		
    		queue.clear();
            queue = new ArrayPriorityQueue<PQTestObject>(MAX_SIZE);
    		for(int i = 0; i < size; i++)
	    	    queue.insert(array[i]);
		    
    		start = System.currentTimeMillis(); // start the timer
    		for(int i = 0; i < ITERATIONS; i++) {
	    		queue.insert(array[(int)(100000*Math.random() % MAX_SIZE)]);
		    	queue.remove();
			    }
    		stop = System.currentTimeMillis();
		
	    	System.out.println("ArrayPQ, Time for n=" + size + ": " + (stop-start)+"\n");

		    size += INITIAL_SIZE;
        }                		   
	}	
}    
