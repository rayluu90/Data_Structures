// NAME: RAY LUU

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapPriorityQueue <E> implements PriorityQueue <E> {
	private int currentSize, maxSize, sequenceNumber;
	long modifiCounter ;
	private Wrapper <E> [] storage;

	/*
	 * HeapPriorityQueue (int size)
	 * initialize the constructor with user input size
	 * implement the priority queue using a heap
	 */
	@SuppressWarnings("unchecked")
	public HeapPriorityQueue(int size){
		sequenceNumber = 0;
		currentSize = 0;
		modifiCounter = 0;
		maxSize = size;	
		storage = new Wrapper [maxSize];
	}
	
	/*
	 * HeapPriorityQueue()
	 * initialize the default constructor with default size
	 */
	public HeapPriorityQueue(){
		this(DEFAULT_MAX_CAPACITY);
	}
	/*
	 * Inserts a new object into the priority queue.  Returns true if
	 * the insertion is successful.  If the PQ is full, the insertion
	 * is aborted, and the method returns false.
	 */
	public boolean insert(E object) {
		// if the heap is full,
		// cannot insert
		if(isFull())
			return false;
		// create a new wrapper object
		Wrapper<E> newEntry = new Wrapper<E>(object);
		// insert the object to the heap
		storage[currentSize] = newEntry;
		currentSize++;
		modifiCounter++;
		// rearrange the inserted item
		// swapping the lower value with the higher value
		trickleUp();
		return true;
	}

    /* 
     * E remove()
     * Removes the object of highest priority that has been in the
     * PQ the longest, and returns it.  Returns null if the PQ is empty.
     */
	public E remove() {
		// if the heap is empty
		// nothing to remove
		if(isEmpty())
			return null;
		@SuppressWarnings("unused")
		Wrapper <E> last = storage[currentSize-1];
		Wrapper<E> first = storage[0];	
		last = first;
		currentSize--;
		modifiCounter++;
		
		// Swapping the higher value with the lower value
		trickleDown();
		return first.data;
	}

	/*
	 * E peek()
     * Returns the object of highest priority that has been in the
     * PQ the longest, but does NOT remove it. 
     * Returns null if the PQ is empty.
	 */
	public E peek() {
		if(isEmpty())
			return null;
		return storage[0].data;
	}

	/*
	 * int size() 
	 * Returns the number of objects currently in the PQ.
	 */
	public int size() {
		return currentSize;
	}

	/*
	 * Iterator<E> iterator()
	 * Returns an iterator of the objects in the PQ, in no particular
	 * order.  The iterator must be fail-fast.
	 */
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	/*
	 * Iterator<E> orderedIterator()
     * Returns an iterator of the objects in the PQ, in exactly the
     * same order in which they will be dequeued.  The iterator must
     * be fail-fast.
	 */
	public Iterator<E> orderedIterator() {
		return new orderedIteratorHelper();
	}

	/*
	 * void clear()
	 * Returns the PQ to an empty state.
	 */
	public void clear() {
		currentSize = 0;
		modifiCounter = 0;
	}

	/*
	 * boolean isEmpty() 
	 * Returns true if the PQ is empty, otherwise false
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/*
	 * boolean isFull()
     * Returns true if the PQ is full, otherwise false.  List based
     * implementations should always return false.
	 */
	public boolean isFull() {
		return currentSize == maxSize;
	}
	
	/*
	 * The purpose of this Wrapper class is to distinguish which 
	 * element has been in the queue the longest with the same priority number
	 * sequenceNumber gets incremented every time inserting a new number
	 */
	@SuppressWarnings("hiding")
	protected class Wrapper<E> implements Comparable<Wrapper<E>>{
		long insertNumber;
		E data;
	
		// the wrapper contains the sequence number
		// to indicate which element the list the longes
		public Wrapper(E d){
			insertNumber = sequenceNumber++;
			data = d;
		}
		
		// compareTo method is to compare two objects
		public int compareTo(Wrapper <E> w){
			@SuppressWarnings("unchecked")
			// comparing 2 objects
			int comp = ((Comparable<E>)data).compareTo(w.data);
			// if two object has the same value
			if(comp == 0)
				// compare them based on their insertNumber
				return (int) (insertNumber - w.insertNumber);
			return comp;
		}
	}

	/*
	 * ShellSort with using wrapper class will repeatedly sort small 
	 sections of the array until the array is sorted from small to biggest
	 * @param array - array that need to be sorted
	 * @return return smallest number or object
	 */
	private Wrapper<E>[] shellSort(Wrapper<E> array[]) {
		Wrapper <E> [] n = array;
		int in, out, h=1;
		Wrapper <E> temp;
		int size = n.length;

		while(h <= size /3)
			h = h*3+1;
		while(h > 0){
			for(out = h; out < size; out++){
				temp = n[out];
				in = out;
				while(in > h-1 &&((Comparable<Wrapper<E>>)
						n[in-h]).compareTo(temp) >= 0){
					n[in] = n[in - h];
					in -= h;
				}
				n[in] = temp;
			}
			h = (h-1)/3;
		}
		return n;
	}
	
	/*
	 * swapping the lower value with the higher value for the insert() method
	 */
	private void trickleUp(){
		int newIndex = currentSize -1;
		int parentIndex = (newIndex -1) >> 1;
		Wrapper<E> newValue = storage[newIndex];

		while(parentIndex >= 0 && ((Comparable<Wrapper<E>>)
				newValue).compareTo(storage[parentIndex]) < 0){
			storage[newIndex] = storage[parentIndex];
			newIndex = parentIndex;
			parentIndex = (parentIndex -1) >> 1;
		}
		storage[newIndex] = newValue;
	}
	
	/*
	 *Swapping the higher value with the lower value for the remove() method
	 */
	private void trickleDown(){
		storage[0] = storage [currentSize];
		int current = 0;
		int child = getNextChild(current);

		while(child != -1 && ((Comparable<Wrapper<E>>)
				storage[current]).compareTo(storage[child]) > 0){
			int childToCurrent = current;
			Wrapper <E>  childToCurrentValue = storage[childToCurrent];	
			storage[current] = storage[child];
			storage[child] = childToCurrentValue;
			current = child;
			child = childToCurrent;
			child = getNextChild(current);
		}
		storage[current] = storage[currentSize];
	}
	
	/*
	 * get the next index with the lowest value
	 * @param current - index after swapping for remove() method
	 * @return left if left value is smaller, otherwise, right.
	 * return -1 if is no more element
	 */
	private int getNextChild(int current){
		int left = (current << 1) + 1;
		int right = left+1;

		if(right < currentSize){ 	// 	there are two children
			if(((Comparable<Wrapper<E>>)
					storage[left]).compareTo(storage[right]) < 0)
				return left; 	// 	the left child is smaller
			return right; 	// 	the right child is smaller
		}
		if(left < currentSize) 	// 	there is only one child
			return left;
		return -1; 	// 	no children
	}
	
	class IteratorHelper implements Iterator <E>{
		int index;
		long ModCheck;
		
		public IteratorHelper(){
			index = 0;
			ModCheck = modifiCounter;
		}
		public boolean hasNext(){
			if(ModCheck != modifiCounter)
				throw new ConcurrentModificationException();
			return index <= currentSize-1;
		}
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return storage[index++].data;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	class orderedIteratorHelper implements Iterator<E>{
		int index;
		long ModCheck;
		Wrapper <E> [] temperArray;

		@SuppressWarnings("unchecked")
		public orderedIteratorHelper(){
			index = 0;
			ModCheck = modifiCounter;
			temperArray = new Wrapper [currentSize];

			for(int i = 0; i < currentSize; i++)
				temperArray [i] = storage [i];
			temperArray = shellSort(temperArray);
		}
		public boolean hasNext(){
			if(ModCheck != modifiCounter)
				throw new ConcurrentModificationException();
			return index <= currentSize-1;
		}
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return temperArray[index++].data;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
