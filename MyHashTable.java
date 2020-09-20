package FinalProject_Template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;



public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
    	this.buckets = new ArrayList<>();
    	this.numBuckets = initialCapacity;
    	this.numEntries=0;
    	for(int i=0; i<this.numBuckets;i++) {
    		this.buckets.add(new LinkedList<HashPair<K,V>>());
    	}
    	
        
        
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        
    	int hashFunction = hashFunction(key);
    	LinkedList<HashPair<K, V>> first = this.buckets.get(hashFunction);
    	
    	if (first != null) {
    	
	    	for (int i=0; i<first.size(); i++) {
	    		
	    		if(first.get(i).getKey().equals(key)) {
	    			V val = first.get(i).getValue();
	    			first.get(i).setValue(value);
	    			return val;
	    			}	
    			}
    	}
    		
    		if((1.0*size())/numBuckets()>=this.MAX_LOAD_FACTOR) {
        		this.rehash();
        	}
    	//in case chain insertion is required
    	numEntries++;
    	hashFunction = hashFunction(key);
    	first = this.buckets.get(hashFunction);
    	HashPair<K,V> newHash = new HashPair<K,V>(key,value);
    	first.add(newHash);
  
    	return null;
        
        //  
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
    	//finding the key's hashFunction
    	int hashFunction = hashFunction(key);
    	LinkedList<HashPair<K, V>> first = this.buckets.get(hashFunction);
    	
    	//while (first !=null) {
    	if (first!=null) {
    		for (HashPair<K, V> z : first) {
	    		if (z.getKey().equals(key)){
	    			return z.getValue();
	    		}
    		}
    	}
        
    	return null;
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        
    	int hashFunction = hashFunction(key);
    	LinkedList<HashPair<K, V>> first = this.buckets.get(hashFunction);
    	
    	
    	if (first == null) {
    		return null;
    	}
 
    	for (int i=0; i<first.size(); i++) {
    		if(first.get(i).getKey().equals(key)) {
    			V temp = first.get(i).getValue();
    			numEntries--;
    			first.remove(i); 
    			return temp;
    		}
    			
    	}
    	
    	return null;
    	
        //
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //
    	ArrayList<LinkedList<HashPair<K,V>>> temp = new ArrayList<LinkedList<HashPair<K,V>>>();
    	for (int i=0; i<this.numBuckets*2; i++) {
    		temp.add(new LinkedList<HashPair<K, V>>());
    	}
    	
		
		this.numBuckets = 2*this.numBuckets;
		for (LinkedList<HashPair<K, V>> list: this.buckets) {
			for (HashPair<K, V> pair: list) {
				temp.get(hashFunction(pair.getKey())).add(pair);
			}
			
		}
		this.buckets = temp;
        //
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //
    	ArrayList<K> z = new ArrayList<>(); 
    	ArrayList<LinkedList<HashPair<K,V>>> temp = this.buckets;
    	for (LinkedList<HashPair<K,V>> x: temp) {
    		for (HashPair<K,V> y: x) {
    			z.add(y.getKey());
    		}
    	}
    	
    	return z;
    	
        //
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
    	MyHashTable<V, V> temp = new MyHashTable<V, V>(this.numEntries);

        for (LinkedList<HashPair<K, V>> list: this.buckets) {
        	for (HashPair<K,V> pair: list) {
        		temp.put(pair.getValue(), pair.getValue());
        		
        	}
        }
        return temp.keys();

    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE
    	ArrayList<K> newList = new ArrayList<K>();
    	newList.addAll(results.keys()) ;
    	
    	return Sort(newList,results);
  	
        //
    }
    private static <K, V extends Comparable<V>> ArrayList<K> Sort(ArrayList<K> merge,MyHashTable<K, V> pairs) {
    	ArrayList<K> l = new ArrayList<K>();
		ArrayList<K> r = new ArrayList<K>();
    	if (merge.size() == 1 ) {//|| merge.size() ==0) { // check for 0 too
    		return merge;
    	}
    	else {
    		int mPoint = (merge.size()-1)/2 ;
    		for (int j = 0 ; j<merge.size() ; j++) {
    			if (j<= mPoint) {
    				l.add(merge.get(j));
    			}
    			else {
    				r.add(merge.get(j));
    			}
    		}
    		r = Sort(r,pairs);
    		l = Sort(l,pairs);
    		
    		return merge(l,r,pairs);
    		
    	}	
    
    }
private static <K,V extends Comparable<V>> ArrayList<K> merge( ArrayList<K> l , ArrayList<K> r , MyHashTable<K,V> pairs) {
    	
    	
    	int k=0;
    	int z=0;
    	ArrayList<K> first = new ArrayList<K>() ;
    	while (k<l.size() && z<r.size()) {
    		if (pairs.get(l.get(k)).compareTo(pairs.get(r.get(z))) > 0 ) {
    			first.add(l.get(k));
    			k++;
    			}
    		else {
    			first.add(r.get(z));
    			z++;
    		}
    	}
    	while (k<l.size()) {
    		first.add(l.get(k));
    		k++;
    	}
    	while (z<r.size()) {
    		first.add(r.get(z));
    		z++;
    	}
    	return first;
    	
    	}
    
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
    	int index;
    	ArrayList<HashPair<K,V>> iterList; 	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	iterList = new ArrayList<HashPair<K,V>>(size());
        	for (LinkedList<HashPair<K,V>> x: getBuckets()) {
        		for (HashPair<K,V> y : x) {
        			iterList.add(y);
        		}
        	}
        	this.index = 0;
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {

        	return (index < size());
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {   	 
        	//buckets
              
              if (index ==iterList.size()) {
              	return null;
              } else {
            	  HashPair<K,V> temp = iterList.get(index);
            	  index++;
              	return temp;
              }
        }

        
       //
        
    }
    
    
}
