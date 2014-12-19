// NAME: RAY LUU

import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook {
	private DictionaryADT<PhoneNumber,String> search ;

    // Constructor.  There is no argument-less constructor, or default size
    public PhoneBook(int maxSize){
        
    	// choosew which data structure to implement the dictionary
    	search = 
//    				new Hashtable<PhoneNumber,String>(maxSize);
    
    				new BinarySearchTree<PhoneNumber,String>();
    	
//    				new BalancedTree<PhoneNumber,String>();
        
    }
       
    // Reads PhoneBook data from a text file and loads the data into
    // the PhoneBook.  Data is in the form "key=value" where a phoneNumber
    // is the key and a name in the format "Last, First" is the value.
    public void load(String filename){
    	String line;
    	try{
    		BufferedReader inputFile = new BufferedReader(new FileReader(filename));
    		while((line = inputFile.readLine())!= null){
    			PhoneNumber k = new PhoneNumber(line.substring(0,12));
    			String v = line.substring(13);
    			search.add(k, v);
    		}
    		inputFile.close();
    	}
    	catch(IOException e) {}
    }

    // Returns the name associated with the given PhoneNumber, if it is
    // in the PhoneBook, null if it is not.
    public String numberLookup(PhoneNumber number){
    	return search.getValue(number);
    }
       
    // Returns the PhoneNumber associated with the given name value.
    // There may be duplicate values, return the first one found.
    // Return null if the name is not in the PhoneBook.
    public PhoneNumber nameLookup(String name){
    	return search.getKey(name);
    }
       
    // Adds a new PhoneNumber = name pair to the PhoneBook.  All
    // names should be in the form "Last, First".
    // Duplicate entries are *not* allowed.  Return true if the
    // insertion succeeds otherwise false (PhoneBook is full or
    // the new record is a duplicate).  Does not change the datafile on disk.
    public boolean addEntry(PhoneNumber number, String name) {
    	return search.add(number,name);
    }
       
    // Deletes the record associated with the PhoneNumber if it is
    // in the PhoneBook.  Returns true if the number was found and
    // its record deleted, otherwise false.  Does not change the datafile on disk.
    public boolean deleteEntry(PhoneNumber number) {
    	return search.delete(number);
    }
       
    // Prints a directory of all PhoneNumbers with their associated
    // names, in sorted order (ordered by PhoneNumber).
    public void printAll() {
    	Iterator<PhoneNumber> keys = search.keys();
    	while(keys.hasNext()){
    		PhoneNumber t = keys.next();
            System.out.println(t + " = " + search.getValue(t));
    	}
	}
    
    // Prints all records with the given Area Code in ordered
    // sorted by PhoneNumber.
    public void printByAreaCode(String code) {
    	Iterator<PhoneNumber> keys = search.keys();
    	while(keys.hasNext()){
    		PhoneNumber t = keys.next();
    		int temp = ((Comparable<String>)t.getAreaCode()).compareTo(code);
    		if(temp == 0)
    			System.out.println(t + " = " + search.getValue(t));
    	}
    }
    
    // Prints all of the names in the directory, in sorted order (by name,
    // not by number).  There may be duplicates as these are the values.       
    public void printNames(){
    	Iterator<String> values = search.values();
    	String [] sortName = new String[search.size()];
    	for(int i = 0; i < search.size(); i++)
    		sortName[i] = values.next();
    	sortName = shellSort(sortName);
    	for(int i = 0; i < sortName.length; i++)
    		System.out.println(sortName[i]);
    }

    //shellSort Algorithm
    private String[] shellSort(String array[]) {
		String [] n = array;
		int in, out, h=1;
		String  temp;
		int size = n.length;

		while(h <= size /3)
			h = h*3+1;
		while(h > 0){
			for(out = h; out < size; out++){
				temp = n[out];
				in = out;
				while(in > h-1 &&((Comparable<String>)
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
