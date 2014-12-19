// NAME: RAY LUU

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedList <E> implements Iterable <E> {
	
	/*
	 * Creating inner class Node with generic to create 
	 * an object that hold the current data and reference that
	 * connect this current Node with the next Node
	 */
	@SuppressWarnings("hiding")
	class Node <E> {
		// Initialize the Collection type of the data 
		E data;
		
		// Initialize the following Node 
		Node<E> next;
		
		// The constructor of the Node class
		public Node (E data) {
			this.data = data;
			next = null;
		}
	}
	
	// Initialize head and tail
	private Node <E> head, tail;
	private int currentSize;
	
	// Constructor of the LinkedListDS
	public UnorderedList() { 
		// set head and tail to Null
		head = tail = null;
		currentSize = 0;
	}
	
	// Returns the object if it exists, otherwise null
    @SuppressWarnings("unchecked")
	public E find(E obj){
		Node<E> temp = head;
		// while not end of the list
		while(temp != null){
			// if the obj is found
			// return it
			if(((Comparable<E>)obj).compareTo(temp.data) == 0)
				return temp.data;
			// otherwise, continue iterate through the list
			temp = temp.next;
		}
		return null;
    }
   
	/*
	 * void addFirst(E obj)
	 * Create a new object Node 
	 * add to the front of the list 
	 */
	public void addFirst(E obj) { 
		// create a new object
		Node<E> newNode = new Node<E>(obj);
		// if the list is empty
		// set head and tail to the new node
		if (head == null)
			head = tail = newNode;			
		else {
		// if list is not empty
			// put the new node in front of the list
			newNode.next = head;
			// update the head
			head = newNode;
		}
		// increment the size  of the list
		currentSize++; 
	}
	
	/*
	 * void addLast(E obj)
	 * Create a new object Node 
	 * add to the last of the list 
	 */
	public void addLast(E obj) { 
		Node<E> newNode = new Node<E>(obj);
		// if the list is empty
		// set head and tail to the new node
		if(head == null)
			head = tail = newNode;	
		else {
		// if the list is not emppty
			// put the new node on the list of the list
			tail.next = newNode;
			// update the tail
			tail = newNode;
			}
		// increament the size of the list
		currentSize++;
	}
	
	/*
	 * E removeFirst()
	 * remove the first thing in the list
	 */
	public E removeFirst() {
		// if the list is empty  
		// nothing is removed 
		if (head == null)
			return null;
		// get the data of the head
		E temp = head.data;
		// there is only one item in the list
		// set it to null
		if (head == tail)
			head = tail = null;	
		else
		// if the list more than 1 item
			// set the head to the next head
			head = head.next;
		// decremet the size of the list
		currentSize--;
		return temp;
	}
	
	/*
	 * E removeLast()
	 * remove the list thing in the list
	 */
	public E removeLast() {
		// if there is nothing in the list
		// nothing is removed
		if(head == null)
			return null;
		// get the data of the tail
		E temp = tail.data;
		// set a variable to keep track of the previous node
		Node<E> previous = null, current = head; 
		
		// if there is only one item in the list
		if(current == tail) {
			// set the head and tail to null
			head = tail = null;
			// deremeent the size of the list
			currentSize--;
			// return the data of the tail
			return temp;
		}
		
		/*
		 * if there are more than 1 item in the list
		 * set a pointer next to last of the list since
		 * there is no way to go back to any node in the list
		 * in the singly linkedlist 
		*/
		
		// while the current node is not = tail
		// iterate through the list
		while(current!=tail){
			previous = current;
			current = current.next;
		}
		
		// delete the last item in list
		previous.next = null;
		// update the tail pointer
		tail = previous;
		// decrement the size
		currentSize--;	
		
		// reture the deleted tail data
		return temp;
	}
	
	/*
	 * E peekFirst()
	 * get first item in the list
	 */
	public E peekFirst() {
		// if no item in the list
		if(head == null)
			// return null
			return null;
		// otherwise return the first item data in the list
		return head.data;		
	}
	
	/*
	 * E peekLast()
	 * get first item in the list
	 */
	public E peekLast() {
		// nothing in the list
		// return null
		if(tail == null)
			return null;
		// otherwise return the tail data
		return tail.data;
	}
	
	/*
	 * boolean remove(E obj)
	 * remove the input obj in the list
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(E obj) {	
		// if no item in the list
		// nothing is removed
		if(head == null)
			return false;
		
		// if the head is the item wanted to remove
		if( ((Comparable<E>)obj).compareTo(head.data)==0) {
			// if there is 1 item in the list 
			if (head == tail) 
			// remove the head and tail
				head = tail = null;
			else
			// if more than 1 iiem in the list
			// the item is the head
				// remove the head
				head = head.next;
			
			// decrement the size of the list
			currentSize--;
			return true;
		}
		
		Node<E> previous = null, current = head;
		
		// if more than 2 items in the list 
		// while not the end of the list
		while (current != null) {
			// if the current is the tail and the tail data is not the desired item
			// item is not found
			if(current == tail && ((Comparable<E>)obj).compareTo(current.data)!=0)
				return false;
			// if the item is found
			// break out of the loop
			else if( ((Comparable<E>)obj).compareTo(current.data) == 0 )
				break;
			
			// iterate through the list
			previous = current;
			current = current.next;
		}
		
		// if the found item is the tail
		// remove the last item
		// update the tail pointer
		if(current == tail){
			previous.next = null;
			tail = previous;
		}
		else 
		// if the found item is not the tail
		// delete the item by using the previous pointer
			previous.next = current.next;
		
		// Decrement the size of the list
		currentSize--;
		return true;
	}
	
	/*
	 * void makeEmpty() 
	 * empty the whole entire list
	 */
	public void makeEmpty() {
		// set the head and tail to null
		head = tail = null;
		// set the size to 0
		currentSize = 0;
	}

	/*
	 * boolean contains(E obj) 
	 * check through the list if the contain the item 
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(E obj) {
		Node<E> temp = head;
		// while not the end of the list
		while(temp != null){
			// if the item is found, return true
			// otherwise continue iterate through the list
			if(((Comparable<E>)obj).compareTo(temp.data) == 0)
				return true;
			temp = temp.next;
		}
		return false;
	}
	
	/*
	 * boolean isEmpty() 
	 * check the size the list is 0
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/*
	 * boolean isFull(E obj) 
	 * the list would not get full 
	 * since this implement using a linkedlist
	 * no fix size
	 */
	public boolean isFull() {
		return false;
	}
	
	/*
	 * int size()
	 * return the current of the list
	 */
	public int size(){
		return this.currentSize;
	}

	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E>{
		Node<E> IteratorPointer;
		
		public IteratorHelper(){
			IteratorPointer =  head;
		}
		
		public boolean hasNext(){
			return IteratorPointer!= null;
		}
		
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			E temp = IteratorPointer.data;
				IteratorPointer = IteratorPointer.next;
			return temp;
		}
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}	
}	
