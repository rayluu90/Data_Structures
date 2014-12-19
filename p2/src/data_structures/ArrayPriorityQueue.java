// NAME: RAY LUU

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class ArrayPriorityQueue <E> implements PriorityQueue<E> {
	private int currentSize, maxSize;
	private E [] storage;
	private long modifiCounter;
	
	/*
	 * ArrayPriorityQueue (int size)
	 * initialize the constructor with user input size
	 */
	@SuppressWarnings("unchecked")
	public ArrayPriorityQueue (int size){
		currentSize = 0;
		maxSize = size;		
		modifiCounter = 0;
		storage = (E[]) new Object [maxSize];
	}
	
	/*
	 * ArrayPriorityQueue()
	 * initialize the default constructor with default size
	 */
	public ArrayPriorityQueue(){
		this(DEFAULT_MAX_CAPACITY);
	}	
	
	/*
	 * insert(E object)
	 * insert the object into the array
	 * Since the array element is in order
	 * use binary search to find the insertion point
	 * shift the array over for the insertion point
	 */
	public boolean insert(E object) {
		// if the array is full
		// cannot insert anything
		if(isFull())
			return false;
		// use binary search to find the insertion point
		int whereInsert = findInsertionIndex(object, 0, currentSize -1);
		// for each element in the array, shift over one positioin
		for(int i = currentSize -1; i >= whereInsert; i--){
			storage [i+1] = storage[i];
		}
		// insert the element
		storage[whereInsert] = object;
		modifiCounter++;
		currentSize++;
		return true;
	}
	
    /* 
     * E remove()
     * Removes the object of highest priority that has been in the
     * PQ the longest, and returns it.  Returns null if the PQ is empty.
     */
	public E remove() {
		if(isEmpty())
			return null;
		modifiCounter++;			
		return storage[--currentSize];
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
		return storage[currentSize-1];
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
		return new IteratorHelper();
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
		return size() == 0;
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
	 * Use binary search to find the index which the object belong to
	 * @param object – insert the desired object 
	 * @param lo - lowest index in the array
	 * @param hi - highest index in the array
	 * @return - the index to insert the object
	 */
	@SuppressWarnings("unchecked")
	private int findInsertionIndex(E object, int lo, int hi){
		if(hi < lo)
			return lo;
		int mid = (lo + hi) >> 1;
		if(((Comparable<E>)object).compareTo(storage[mid]) >= 0)
			return findInsertionIndex(object, lo, mid -1);
		return findInsertionIndex(object, mid+1, hi);
	}
	
	class IteratorHelper implements Iterator<E>{
		int index;
		long ModCheck;

		public IteratorHelper(){
			index = currentSize -1;
			ModCheck = modifiCounter;
		}
		public boolean hasNext(){
			if(ModCheck != modifiCounter)
				throw new ConcurrentModificationException();
			return index >= 0;
		}
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return storage[index--];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}

