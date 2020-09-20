package FinalProject_Template;

import java.util.ArrayList;


public class Twitter {
	
	
	private MyHashTable<String, ArrayList<Tweet>> author;
	private MyHashTable<String, ArrayList<Tweet>> date;
	private MyHashTable<String, String> stopWords;
	private MyHashTable<String, Integer> frequency;

		
	//
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		//initialising this.dates
		this.date = new MyHashTable<String,ArrayList<Tweet >>(tweets.size());
		this.author = new MyHashTable<String, ArrayList<Tweet>>(tweets.size());
		this.stopWords = new MyHashTable<String,String>(stopWords.size());
		for (int i = 0; i<stopWords.size();i++) {
			this.stopWords.put(stopWords.get(i).toLowerCase(), "Saad");
		}	
		//initialising this.frequency
		this.frequency = new MyHashTable<String, Integer>(tweets.size());

		for (int i=0; i<tweets.size();i++) {
			addTweet(tweets.get(i));
		}
	}
	
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//
		//Tweet x =this.author.get(t.getAuthor()); //[saad, "i am saad"]
		
		//[saad,"i go to mcgill"]
		ArrayList<Tweet> y = this.date.get(t.getDateAndTime().substring(0, 10));
		if (y==null) {
			ArrayList<Tweet> dateArray = new ArrayList<Tweet>();
			dateArray.add(t);
			this.date.put(t.getDateAndTime().substring(0, 10), dateArray);
		}
		else {
			y.add(t);
		}
	
		
		ArrayList<Tweet> x = this.author.get(t.getAuthor());
		if (x == null) {
			ArrayList<Tweet> z = new ArrayList<Tweet>();
			z.add(t);
			this.author.put(t.getAuthor(), z);
		}
		else {
			x.add(t);
		}
		
		ArrayList<String> words = getWords(t.getMessage());
		MyHashTable<String, Integer> wordTable = new MyHashTable<String,Integer>(words.size());
		for (int z=0; z<words.size(); z++) {
			wordTable.put(words.get(z).toLowerCase(), 1);
		}
		
		ArrayList<String> z = wordTable.keys();
		
		for (int i=0; i<z.size(); i++) {
			if (this.stopWords.size()!= 0) {
				if (! (this.stopWords.get(z.get(i)) == "Saad")) {
					if (this.frequency.get(z.get(i))== null) {
						this.frequency.put(z.get(i), 1);
					}
					else {
						int fe = this.frequency.get(z.get(i));
						this.frequency.put(z.get(i), fe+1);
					}
				}
		}
			else {
//				//create a hashtable for words
				if (this.frequency.get(z.get(i))== null) {
					this.frequency.put(z.get(i), 1);
				}
				else {
					int fe = this.frequency.get(z.get(i));
					this.frequency.put(z.get(i), fe+1);
				}
			}
		}
		

	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE
    	ArrayList<Tweet> x = this.author.get(author);
    	//Tweet x = this.author.get(author);
    	if (x == null) {
    		return null;
    	}
    	Tweet min = x.get(0);
    	for (Tweet z: x) {
    		if (z.compareTo(min) > 0) {
    			min = z;
    		}
    		
    	}
    	return min;
    	
        //
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	
    	ArrayList<Tweet> x = this.date.get(date);
    	if (x == null) {
    	
    		return null;
    	}
    	return x;
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //

    	ArrayList<String> x =  MyHashTable.fastSort(this.frequency);
    	return x;
    	
        //	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }
    



}
