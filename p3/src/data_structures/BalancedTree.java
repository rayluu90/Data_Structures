// NAME: RAY LUU

package data_structures;

import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTree<K,V> implements DictionaryADT<K,V> {
	private TreeMap <K,V> treeMap;
	
	/*
	 * BalancedTree()
	 * implement Dictionary using the built inTreeMap
	 */
	public BalancedTree() {
		treeMap = new TreeMap<K,V>();
	}

	/*
	 * boolean contains(K key)
	 * Returns true if the dictionary has an object identified by
	 * key in it, otherwise false.
	 */
	public boolean contains(K key) {
		// call the contain method of the TreeMap class
		return treeMap.containsKey(key);
	}

	/*
	 * boolean add(K key, V value)
	 * Adds the given key/value pair to the dictionary.  Returns
	 * false if the dictionary is full, or if the key is a duplicate.
	 * Returns true if addition succeeded.
	 */
	public boolean add(K key, V value) {
		// call the contain method in this class
		// if already contained the key
		// do not add
		if(contains(key))
			return false;
		// if no key found, add the key to the lis
		treeMap.put(key, value);
		return true;
	}
	
	/*
	 * boolean delete(K key) 
	 * Deletes the key/value pair identified by the key parameter.
	 * Returns true if the key/value pair was found and removed,
	 * otherwise false.
	 */
	public boolean delete(K key) {
		// if the key is in the list
		if(contains(key)){
			// remove the key from the list
			treeMap.remove(key);
			return true;
		}
		// if the key is not in the list
		// return false
		return false;
	}

	/*
	 * V getValue(K key)
	 * Returns the value associated with the parameter key.  Returns
	 * null if the key is not found or the dictionary is empty.
	 */
	public V getValue(K key) {
		return treeMap.get(key);
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
		// key iterator
		Iterator<K> iterKey = treeMap.keySet().iterator();
		// value iterator
		Iterator<V> iterValue = treeMap.values().iterator();
		// while there is still key and value in each iterator
		while(iterKey.hasNext() && iterValue.hasNext()){
			K keyNext = iterKey.next();
			V valueNext = iterValue.next();
			// compare the input value with the each value iterator
			// if both value are equal
			// return the key
			if(((Comparable<V>)value).compareTo(valueNext) == 0)
				return keyNext;
		}
		// return null if no value found
		return null;
	}

	/*
	 * int size()
	 * Returns the number of key/value pairs currently stored
	 * in the dictionary
	 */
	public int size() {
		return treeMap.size();
	}
	
	
	/*
	 *  boolean isFull()
	 *  Returns true if the dictionary is at max capacity
	*/
	public boolean isFull() {
		return false;
	}

	/*
	 * boolean isEmpty()
	 * Returns true if the dictionary is empty
	 */
	public boolean isEmpty() {
		return treeMap.isEmpty();
	}

	/*
	 * void clear()
	 * Returns the Dictionary object to an empty state.
	 */
	public void clear() {
		treeMap.clear();
	}

	/*
	 * Iterator<K> keys()
	 * Returns an Iterator of the keys in the dictionary, in ascending
	 * sorted order.  The iterator must be fail-fast.
	 */
	public Iterator<K> keys() {
		return treeMap.keySet().iterator();
	}

	/*
	 * Iterator<V> values()
	 * Returns an Iterator of the values in the dictionary.  The
	 * order of the values must match the order of the keys.
	 * The iterator must be fail-fast.
	 */
	public Iterator<V> values() {
		return treeMap.values().iterator();
	}
}
