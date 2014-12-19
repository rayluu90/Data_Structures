/*  PQTester.java
	Written by: Professor Alan Riggins
    CS310 Fall 2012
    Programming Assignment #2
*/

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import data_structures.*;

public class PQTester {
    public static void main(String [] args) {
        final int SIZE = 10;
        long sequence_number = 0;
        int priority;
        
//////////////////////////////////////////////////////////////////////
///  Select the implementation you wish to test        
        PriorityQueue<PQTestObject> queue = 
            new ArrayPriorityQueue<PQTestObject>(SIZE);       
//////////////////////////////////////////////////////////////////////  
        System.out.println("Now testing the ARRRAY implementation");
        PQTestObject [] array = new PQTestObject[SIZE];
        for(int i=0; i < SIZE; i++) 
            array[i] = new PQTestObject((int) ((1000*Math.random() % 20) +1),
                                         sequence_number++);
       // array[0] = new PQTestObject((int) (2),1);
        //array[1] = new PQTestObject((int) (1),2);
        //array[2] = new PQTestObject((int) (2),3);
        //array[3] = new PQTestObject((int) (1),4);
        //array[4] = new PQTestObject((int) (1),5);
        //array[5] = new PQTestObject((int) (2),6);
                                         
        for(int i=0; i < SIZE; i++)
            queue.insert(array[i]);
          
        // check to see what happens if insertion beyond capacity  
        try {
            if(queue.insert(new PQTestObject(1,1)))
                throw new RuntimeException("ERROR, inserted beyond capacity");
            }
        catch(Exception e) {
            e.printStackTrace();
            }
            
        if(queue.size() != SIZE)
            System.out.println("ERROR, wrong size!");
	 
        System.out.println("Now printing with the iterator.  " +
            "They should come out in any order.\n" +
            "Priorty  SequenceNumber");   
    	Iterator<PQTestObject> iter = queue.iterator();
    	while(iter.hasNext())
    		System.out.print(iter.next()+ "   ");
        System.out.println();		
    	try {
    	    PQTestObject obj = iter.next();
    	    }
    	catch(Exception e) {
    	    System.out.println("Good, iterator passed.");
    	    }
        for(PQTestObject o : queue) 
            ;
        System.out.println("==============================================");   
    	System.out.println("Now removing them all...");	    
            for(int i=0; i < SIZE; i++)
                System.out.print(queue.remove()+ "   "); 
        System.out.println();	
        if(!queue.isEmpty())
            System.out.println("ERROR, queue should be empty");    
    	queue.clear();	

	   
        System.out.println("==============================================");
    	queue.insert(new PQTestObject(25,1));
    	queue.insert(new PQTestObject(25,2));
    	queue.insert(new PQTestObject(1,1));
    	queue.insert(new PQTestObject(1,2));
    	queue.insert(new PQTestObject(25,3));        	    
    	System.out.println("Now emptying remaining elements");  
    	while(!queue.isEmpty())
    		System.out.println(queue.remove());	    	                       

        
        
//////////////////////////////////////////////////////////////////////        
 queue = new HeapPriorityQueue<PQTestObject>(SIZE);        
//////////////////////////////////////////////////////////////////////  
        System.out.println("\n\nNow testing the HEAP implementation");        
       // array = new PQTestObject[SIZE];      
      // for(int i=0; i < SIZE; i++) 
       // array[i] = new PQTestObject((int) ((1000*Math.random() % 20) +1),
                                    //     sequence_number++);
        
        array[0] = new PQTestObject((int) (2),1);
        array[1] = new PQTestObject((int) (1),2);
        array[2] = new PQTestObject((int) (2),3);
        array[3] = new PQTestObject((int) (1),4);
        array[4] = new PQTestObject((int) (1),5);
        array[5] = new PQTestObject((int) (2),6);
        array[6] = new PQTestObject((int) (5),1);
        array[7] = new PQTestObject((int) (3),2);
        array[8] = new PQTestObject((int) (10),3);
        array[9] = new PQTestObject((int) (7),4);
      
       
        for(int i=0; i < SIZE; i++)
            queue.insert(array[i]);
          
        // check to see what happens if insertion beyond capacity  
        try {
            if(queue.insert(new PQTestObject(1,1)))
                throw new RuntimeException("ERROR, inserted beyond capacity");
            }
        catch(Exception e) {
            e.printStackTrace();
            }
            
        if(queue.size() != SIZE)
            System.out.println("ERROR, wrong size!");
	 
        System.out.println("Now printing with the iterator.  " +
            "They should come out in any order.\n" +
            "Priorty  SequenceNumber");   
    	iter = queue.iterator();
    	while(iter.hasNext())
    		System.out.print(iter.next()+ "   ");
        System.out.println();		
    	try {
    	    PQTestObject obj = iter.next();
    	    }
    	catch(Exception e) {
    	    System.out.println("Good, iterator passed.");
    	    }
        for(PQTestObject o : queue) 
            ;
        System.out.println("==============================================");   
    	System.out.println("Now removing them all...");	  
  // System.out.println(queue.size());
            for(int i=0; i < SIZE; i++)
                System.out.print(queue.remove()+ "   "); 
        System.out.println();	
        if(!queue.isEmpty())
            System.out.println("ERROR, queue should be empty");    
    	queue.clear();	

	   
        System.out.println("==============================================");
    	queue.insert(new PQTestObject(25,1));
    	queue.insert(new PQTestObject(25,2));
    	queue.insert(new PQTestObject(1,1));
    	queue.insert(new PQTestObject(1,2));
    	queue.insert(new PQTestObject(25,3));        	    
    	System.out.println("Now emptying remaining elements");  
    	while(!queue.isEmpty())
    		System.out.println(queue.remove());	    	                       
        }        	
}    
