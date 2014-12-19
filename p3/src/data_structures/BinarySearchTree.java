// NAME: RAY LUU

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {
	private Node<K,V> root;
	private int currentSize, modifiCounter;
	
	public BinarySearchTree() {
		root = null;
		currentSize = 0;
		modifiCounter = 0;
	}
	
	/*
	 * boolean contains(K key)
	 * Returns true if the dictionary has an object identified by
	 * key in it, otherwise false.
	 */
	public boolean contains(K key) {
		// if the root is null 
		// nothing in the list
		if(root == null)
			return false;
		
		Node<K,V> current = root;
		// while current is not null
		while(current != null) {
			@SuppressWarnings("unchecked")
			int temp = ((Comparable<K>)key).compareTo(current.key);
			// if found the key, return tree
			if(temp == 0)
				return true;
			else if(temp < 0)
			// if the current key is > input key
				// go left
				current = current.leftChild;
			else
			// if the current key is < input key
				// go right
				current = current.rightChild;
		}
		// if nothing is found, return false
		return false;
	}
	
	/*
	 * boolean add(K key, V value)
	 * Adds the given key/value pair to the dictionary.  Returns
	 * false if the dictionary is full, or if the key is a duplicate.
	 * Returns true if addition succeeded.
	 */
	public boolean add(K k, V v) {
		Node<K,V> newNode = new Node<K,V>(k,v);
		currentSize++;
		modifiCounter++;
		// if the root is null
		if(root == null){
			// add the new key to the root
			root = newNode;
			return true;
		}
		Node<K,V> current = root;
		// while current is not null
		while(current != null ) {
			@SuppressWarnings("unchecked")
			int temp = ((Comparable<K>)k).compareTo(current.key);
			// if the input key is < current key
			if(temp < 0) {
				// if left child is null
				if(current.leftChild == null) {
					// add the new key to the leftchild
					current.leftChild = newNode;
					break;
				}
				// go left
				current = current.leftChild;
			}
			else if(temp > 0) {
			// if the input key is > than current key
				// if the right child is null
				if(current.rightChild == null) {
					// add the new key to the right child
					current.rightChild = newNode;
					break;
				}
				// go right
				current = current.rightChild;
			}
			else {
			// if the key already exist in the tree 
				// dont add anything and revert the changes
				currentSize--;
				modifiCounter--;
				return false;
			}
		}
		return true; 
	}

	
	private boolean successfullyDeleted;
	/*
	 * boolean delete(K key) 
	 * Deletes the key/value pair identified by the key parameter.
	 * Returns true if the key/value pair was found and removed,
	 * otherwise false.
	 */
	public boolean delete(K key) {
        root = remove(key, root);
        if(successfullyDeleted) {
            modifiCounter++;
        	currentSize--;
        	return successfullyDeleted;
        }
        return false;
    }
	 
	/*
	 * V getValue(K key)
	 * Returns the value associated with the parameter key.  Returns
	 * null if the key is not found or the dictionary is empty.
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K key) {
		// if the tree is empty
		// return null
		if(root == null)
			return null;
		
		Node<K,V> current = root;
		// while current is not null
		while(current != null) {
			int temp = ((Comparable<K>)key).compareTo(current.key);
			// if key is found, return the value of the key
			if (temp == 0)
				return current.value;
			else if(temp < 0)
			// if the key is < current key
				// go left
				current = current.leftChild;
			else 
			// if the key is > current key
				// go right
				current = current.rightChild;
		}
		// if no key is found, return null
		return null;
	}
	
	private K keyHaveFound;
	/*
	 * K getKey(V value)
	 * Returns the key associated with the parameter value.  Returns
	 * null if the value is not found in the dictionary.  If more
	 * than one key exists that matches the given value, returns the
	 * first one found.
	 */
	public K getKey(V value) {
		// search for the key
		searchingForKey(value, root);
		//return keyHaveFound after calling searchingForKey(value, root)
		return keyHaveFound;
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
	 * boolean isEmpty()
	 * Returns true if the dictionary is empty
	 */
	public boolean isFull() {
		return false;
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
		// set the size of the tree to 0
		currentSize = 0;
		modifiCounter = 0;
		// sets the root to null
		root = null;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}
	
	/*
	 * @param <K> key
	 * @param <V> value
	 * Node with key and value inside
	 */
	@SuppressWarnings("hiding")
	private class Node<K,V> {
		private K key;
		private V value;
		private Node<K,V> leftChild;
		private Node<K,V> rightChild;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			leftChild = rightChild = null;
		}
	}
	
	/*
	 * @param key: input key
	 * @param currentPosition: Node Position
	 * @check recursively checking if the delete node has no child, one child or 2 children 
	 */
	private Node<K,V> remove(K key, Node<K,V> currentPosition) {
		// if the current position is null
		if(currentPosition == null){
			// set the flag to false
        	successfullyDeleted = false;
         	return currentPosition;  
        }
		@SuppressWarnings("unchecked")
		int temp = ((Comparable<K>) key).compareTo(currentPosition.key);
		// if the input key is < current key
		if(temp < 0)
			// search the left
			currentPosition.leftChild = remove(key,currentPosition.leftChild);
		else if(temp > 0)
		// if the input key is > current key
			// search the right
			currentPosition.rightChild = remove(key,currentPosition.rightChild);
		else if(currentPosition.leftChild != null && currentPosition.rightChild != null) {
		// if the current left and current right is not null
			// search for the most left child
			currentPosition.key = addtionalLeftNode(currentPosition.rightChild).key;
			// search the right
			currentPosition.rightChild = remove(currentPosition.key,currentPosition.rightChild);	
		}
		else {
			// the current right child is not null
			if(currentPosition.rightChild != null)
				currentPosition = currentPosition.rightChild;
			else 
				currentPosition = currentPosition.leftChild;
			// set the flag to true
			successfullyDeleted = true;
		}	
		// return the node that need to delete
		return currentPosition;
	}
	
	/*
	 * @param position: the position of delete node rightChild
	 * @return the most leftChild.
	 * if no leftChild, just return the input
	 */
	 private Node<K,V> addtionalLeftNode(Node<K,V> position) {
		 if(position.leftChild == null)
			 return position;
		 return addtionalLeftNode(position.leftChild);
	 }
	 /*
	  * @param value: input value
	  * @param currentPosition: the current position
	  * search right and search left each level of the tree until the first key is found
	  */
	 @SuppressWarnings("unchecked")
	 private void searchingForKey(V value, Node<K,V> currentPosition){
		 // if the current position is null, return
		 if(currentPosition == null) 
			return;
		int temp = ((Comparable<V>)value).compareTo(currentPosition.value);
		// if key is found
		if(temp == 0) {
			keyHaveFound = currentPosition.key;
			return;
		}
		// search right
		searchingForKey(value,currentPosition.rightChild);
		// search left
		searchingForKey(value,currentPosition.leftChild);
	 }
	
	@SuppressWarnings("hiding")
	class KeyIteratorHelper<K> implements Iterator <K>{
		protected int idx;
		protected long ModCheck;
		private Node<K,V> [] array;
		
		@SuppressWarnings("unchecked")
		public KeyIteratorHelper(){
			array = new Node[currentSize];
			idx = 0;
			ModCheck = modifiCounter;
			inOrderFillArray((Node<K, V>) root);
			idx = 0;
		}
		
		public boolean hasNext(){
			if(ModCheck != modifiCounter)
				throw new ConcurrentModificationException();
			return idx < currentSize;
		}
		
		public K next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return (K) array[idx++].key;
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
		
		private void inOrderFillArray(Node<K,V> n){
			if(n != null){
				inOrderFillArray(n.leftChild);
				array[idx++] = n;
				inOrderFillArray(n.rightChild);
			}
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
