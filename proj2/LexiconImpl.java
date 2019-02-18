package edu.iastate.cs228.proj2;

import java.util.Comparator;

import java.util.Arrays;

/**
 * 
 * @author Lillian Krohn
 *
 */

public class LexiconImpl implements Lexicon, Comparator<String> {

    /***
     * Lookup table mapping characters in lexicographical order to
     * to their input order. This is public to support automated grading. 
     */
	public CharacterValue[] characterOrdering; 

    /***
     * Creates an array of CharacterValue from characterOrdering.  Sorts
     * it using java.util.Arrays.sort().
     * @param characterOrdering character order from configuration file
     */	
	public LexiconImpl(char[] characterOrdering) {
		//TODO -- 2 lines
		if(characterOrdering.length <=0 || characterOrdering == null) {
			throw new IllegalArgumentException("Cannot reference null");
		}
		this.characterOrdering = new CharacterValue[characterOrdering.length];
		for(int i = 0; i < this.characterOrdering.length; i++) {
			this.characterOrdering[i] = new CharacterValue(i, characterOrdering[i]); //keep parameters consistent
		}
		
		Comparator<CharacterValue> comp = new Comparator<CharacterValue>(){
			@Override 
			public int compare(CharacterValue firstVal, CharacterValue secondVal) {
				if(firstVal == secondVal || firstVal.equals(secondVal)) {
					return 0;
				}
				if(firstVal.character < secondVal.character) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}; //end def
		Arrays.sort(this.characterOrdering, comp);
	}


    /***
     * Compares two words based on the configuration
     * @param a first word
     * @param b second word
     * @return negative if a<b, 0 if equal, postive if a>b
     */
	@Override
	public int compare(String a, String b) {
		// TODO
		// could use math.min to determine the shortest string and that length will be
		// "n" in the for loop
		for (int i = 0; i < a.length() && i < b.length(); i++) {
			if (getCharacterOrdering(a.charAt(i)) == -1 || getCharacterOrdering(b.charAt(i)) == -1) {
				return -1; // in tutoring
			}
			if (getCharacterOrdering(a.charAt(i)) < getCharacterOrdering(b.charAt(i))) {
				return -1;
			} else if (getCharacterOrdering(a.charAt(i)) > getCharacterOrdering(b.charAt(i))) {
				return 1;
			}
		}
		// now check actual length
		if (a == b) {
			return 0;
		}
		if (a.length() < b.length()) {
			return -1;
		}
		return 1;
	}


	/**
	 * Uses binary search to find the order of key. aka Index of key
	 * @param key
	 * @return ordering value for key or -1 if key is an invalid character.
	 */
	public int getCharacterOrdering(char key) {
		//TODO
		//use pseudocode from slides
		//binary search here
		return binarySearch(this.characterOrdering, new CharacterValue(-1, key));
}

	/**
	 * Searches for key in characterOrdering.
	 * getCharacterOrdering will reference this method to binary search
	 * @param characterOrdering
	 * @param key
	 * @return negative value
	 */
	private int binarySearch(CharacterValue[] characterOrdering, CharacterValue key) {
		int low = 0;
		int high = characterOrdering.length-1;
		int mid = 0;
		while(low <= high) {
			mid = ((high + low) / 2);
			
			if (key.character < characterOrdering[mid].character) {
				high = mid-1;
			}
			else if(key.character > characterOrdering[mid].character) {
				low = mid + 1;
			}
			else {
				return characterOrdering[mid].value;
			}
		}
	return -1;
	}


	public static class CharacterValue {
		public int value;
		public char character;
		
		public CharacterValue(int value, char character) {
			this.value = value;
			this.character = character;
		}
		
		public boolean equals(Object o) {
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			CharacterValue other = (CharacterValue) o;
			return value == other.value && character == other.character;
		}
	}
	 
	/**
	 * Returns whether or not word is valid according to the alphabet
	 * known to this lexicon. 
	 * 
	 * @param word word to be checked.
	 *
	 * @return true if valid. false otherwise
	 */
	public boolean isValid(String word) {
		//TODO
		//char array
		//char[] a =
		//use psuedocode from slides
		for(int i = 0; i < word.length(); i++) {
			if(getCharacterOrdering(word.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
}
