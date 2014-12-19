// NAME: RAY LUU

package data_structures;

import java.util.Iterator;

public class Queue<E> implements Iterable<E> {
    private ListADT<E> list;
    
    // Using linkedlist to implement the queue
    //  instansiate the linkedlist class in the constructor
    public Queue() {
    	list = new LinkedListDS<E>();
    }
    
    //  Adds the Object obj to the beginning of the list
    public void enqueue(E obj) {
    	list.addLast(obj);
    }   
    
	//  Removes the first Object in the list and returns it.
	//  Returns null if the list is empty.
    public E dequeue() {
    	return list.removeFirst();
    }   
    
    //  Returns the number of Objects currently in the list.
    public int size() {
    	return list.size();
    }
    
    //  Returns true if the list is empty, otherwise false
    public boolean isEmpty() {
    	return list.isEmpty();
    }
    
    //  Returns true if the list is full, otherwise false
    public boolean isFull() {
    	return list.isFull();
    }
    
	//  Returns the first Object in the list, but does not remove it.
	//  Returns null if the list is empty.
    public E peek(){
    	return list.peekFirst();
    }
    
	//  Returns the last Object in the list, but does not remove it.
	//  Returns null if the list is empty.
    public E peekLast(){
    	return list.peekLast();
    }
    
    //  Returns true if the list contains the Object obj, otherwise false
    public boolean contains(E obj) {
    	return list.contains(obj);
    }
    
	//  Removes the first Object in the list and returns it.
	//  Returns null if the list is empty.
    public boolean remove(E obj){
    	return list.remove(obj);
    }
    
    //  The list is returned to an empty state.
    public void makeEmpty() {
    	list.makeEmpty();
    } 
    
	//  Returns an Iterator of the values in the list, presented in
	//  the same order as the list.
    public Iterator<E> iterator() {
    	return list.iterator();
    }
}