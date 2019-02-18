package edu.iastate.cs228.proj2;

import java.util.Comparator;

/**
 * 
 * @author Lillian Krohn
 *
 */
public class SelectionSort extends SorterWithStatistics {
	
	//This method will be called by the base class sort() method to 
	// actually perform the sort. 
	//use psuedocode from slides
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		//TODO: implement SelectionSort -- reference slides
	 
		int min;
		String temp;
		for(int i = 0; i < words.length; i++){
			min = i;			
			for(int j = i + 1; j < words.length; j++){
				if(comp.compare(words[min], words[j]) == 1){
					min = j;
				}
			}
			if(min != i){ //swap vals here
				temp = words[i];
				words[i] = words[min];
				words[min] = temp;
			}
		}
		wordsSorted = words.length;
	}
}