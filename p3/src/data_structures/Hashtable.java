// NAME: RAY LUU

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Hashtable<K,V> implements DictionaryADT<K,V> {
	private int tableSize, currentSize, maxSize, modifiCounter;
	private UnorderedList<Wrapper<K,V>> [] list;

	@SuppressWarnings("unchecked")
	public Hashtable(int n){
		modifiCounter = 0;
		currentSize = 0;
		maxSize = n;
		tableSize = (int) (maxSize*1.3f);
		list =  new UnorderedList[tableSize];
		// initialize a linkedlist in each slot of the hashtable
		for(int i = 0; i < tableSize; i++)
			list[i] = new UnorderedList<Wrapper<K,V>>();
	}
	
	/*
	 * boolean contains(K key)
	 * Returns true if the dictionary has an object identified by
	 * key in it, otherwise false.
	 */
	public boolean contains(K key) {
		return list[getIndex(key)].contains(new Wrapper<K,V>(key, null));
	}
	
	/*
	 * boolean add(K key, V value)
	 * Adds the given key/value pair to the dictionary.  Returns
	 * false if the dictionary is full, or if the key is a duplicate.
	 * Returns true if addition succeeded.
	 */
	public boolean add(K key, V value) {
		// if the table is full
		// don't add anythings
		if(isFull())
			return false;
		// if the table has already contained the key
		// don't add the key to the list
		if(contains(key))
			return false;
		// add the key to the hashtable
		list[getIndex(key)].addFirst(new Wrapper<K,V>(key,value));
		currentSize++;
		modifiCounter++;
		return true;
	}
	
	/*
	 * boolean delete(K key) 
	 * Deletes the key/value pair identified by the key parameter.
	 * Returns true if the key/value pair was found and removed,
	 * otherwise false.
	 */
	public boolean delete(K key) {
		// if successully removed the key
		// decrement the size of the hashtable
		if(list[getIndex(key)].remove(new Wrapper<K,V>(key,null))) {
			currentSize--;
			modifiCounter++;
			return true;
		}
		// otherwise, return false
		return false;
	}

	/*
	 * V getValue(K key)
	 * Returns the value associated with the parameter key.  Returns
	 * null if the key is not found or the dictionary is empty.
	 */
	public V getValue(K key) {
		// find the value base on the key
		Wrapper <K,V> tmp = list[getIndex(key)].find(new Wrapper<K,V>(key, null));
		// if the value of the key is null, return null
		if(tmp == null)
			return null;	
		// otherwise, return the value of the key
		return tmp.value; 
	}

	/*
	 * K getKey(V value)
	 * Returns the key associated with the parameter value.  Returns
	 * null if the value is not found in the dictionary.  If more
	 * than one key exists that matches the given value, returns the
	 * first one found.
	 */
	@SuppressWarnings("unchecked")
	public K getKey(V value) {
		// for each linkedlink in the hashtable
		for(int i = 0; i < tableSize; i++)
			// for each key contains in the linkedlist
			for(Wrapper<K,V> n : list[i])
				// if the value of the key == input value
				// return the key
				if(((Comparable<V>)value).compareTo(n.value) == 0)
					return n.key;		
		// if nothing is found, return null
		return null;
	}
	
	/*
	 * int size()
	 * Returns the number of key/value pairs currently stored
	 * in the dictionary
	 */
	public int size() {
		return currentSize;
	}

	/*
	 *  boolean isFull()
	 *  Returns true if the dictionary is at max capacity
	*/
	public boolean isFull() {
		return currentSize == maxSize;
	}

	/*
	 * boolean isEmpty()
	 * Returns true if the dictionary is empty
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	/*
	 * void clear()
	 * Returns the Dictionary object to an empty state.
	 */
	public void clear() {
		// empty out each of the linkedlist in the hashtable
		for(int i = 0; i < tableSize; i++)
			list[i].makeEmpty();
		// set size to 0
		currentSize = 0;
		modifiCounter = 0;
	}

	/*
	 * Iterator<K> keys()
	 * Returns an Iterator of the keys in the dictionary, in ascending
	 * sorted order.  The iterator must be fail-fast.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}

	/*
	 * Iterator<V> values()
	 * Returns an Iterator of the values in the dictionary.  The
	 * order of the values must match the order of the keys.
	 * The iterator must be fail-fast.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}
	
	/*
	 * @param key: input key
	 * @return array index after MOD it with the table Size
	 */
	private int getIndex(K key){
		return ((key.hashCode() & 0x7FFFFFFF) % tableSize);
	}
	
	/*
	 * @param <K> key
	 * @param <V> value
	 * a Wrapper class with key and value inside
	 */
	@SuppressWarnings("hiding")
	private class Wrapper<K,V> implements Comparable<Wrapper<K,V>>{
		K key;
		V value;
		
		public Wrapper(K key, V value){
			this.key = key;
			this.value = value;
		}
		@SuppressWarnings("unchecked")
		public int compareTo (Wrapper<K,V>node){
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
	}
	
	@SuppressWarnings("hiding")
	class KeyIteratorHelper<K> implements Iterator <K>{
		private Wrapper <K,V> [] nodes;
		private int idx;
		private long ModCheck;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public KeyIteratorHelper(){
			nodes = new Wrapper[currentSize];
			idx = 0;
			ModCheck = modifiCounter;
			int j = 0;
			for(int i = 0; i < tableSize; i++)
				for(Wrapper n: list[i])
					nodes[j++] = n;
			nodes = shellSort(nodes);
		}
		
		public boolean hasNext(){
			if(ModCheck != modifiCounter)
				throw new ConcurrentModificationException();
			return idx < currentSize;
		}
		
		public K next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return nodes [idx++].key;
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
		
		/*
		 * Wrapper[] shellSort(Wrapper array[])
		 * Using shellshort algorithm to sort the wrapper 
		 * object in order
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Wrapper[] shellSort(Wrapper array[]) {
			Wrapper [] n = array;
			int in, out, h=1;
			Wrapper  temp;
			int size = n.length;

			while(h <= size /3)
				h = h*3+1;
			while(h > 0){
				for(out = h; out < size; out++){
					temp = n[out];
					in = out;
					while(in > h-1 &&((Comparable<Wrapper>)
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
	}
	
	@SuppressWarnings("hiding")
	class ValueIteratorHelper<V> implements Iterator <V>{
		Iterator<K> keyIter;
		
		public ValueIteratorHelper(){
			keyIter = keys();
		}
		
		public boolean hasNext(){
			return keyIter.hasNext();
		}
		
		@SuppressWarnings("unchecked")
		public V next(){
			return (V) getValue(keyIter.next());
		}
		
		public void remove(){
			keyIter.remove();
		}
	}
}

