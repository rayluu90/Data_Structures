/*  PQTestObject.java
	Written by: Professor Alan Riggins
    CS310 Fall 2012
    Programming Assignment #2
*/

public class PQTestObject implements Comparable<PQTestObject> {
    private int priority;
    private long sequence_number;
    
    public PQTestObject(int p, long s) {
        priority = p;
        sequence_number = s;
        }
        
    public int compareTo(PQTestObject o) {
        return priority - o.priority;
        }
        
    public String toString() {
        return priority + " " + sequence_number;
        }
    }
