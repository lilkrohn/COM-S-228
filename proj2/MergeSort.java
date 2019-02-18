package edu.iastate.cs228.proj2;

import java.util.Comparator;

/**
 * 
 * @author Lillian Krohn
 *
 */

public class MergeSort extends SorterWithStatistics {
	
	//This method will be called by the base class sort() method to 
	// actually perform the sort. 
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		//TODO: implement mergeSort. 
		//String[] temp = new String[words.length];
		MergeSortRec(words, comp, 0, words.length-1); 
		wordsSorted = words.length;
		}
	
	/**
	 * This helper method will use the mergeSort algorithm 
	 * sort the array by sections and halves
	 * @param words
	 * @param comp
	 * @param start
	 * @param mid
	 * @param end
	 */
	private static void Merge(String[] words, Comparator<String> comp, int start, int mid, int end) {
		int left = mid - start + 1;
		int right = end - mid;
		String tempLeft[] = new String[left];
		String tempRight[] = new String[right];
		for(int i = 0; i < left; i++) {
			tempLeft[i] = words[start+i];
		}
		for(int j = 0; j < right; j++) {
			tempRight[j] = words[mid + 1 + j];
		}
		int i = 0;
		int j = 0;
		int k = start;
		while(i < left && j < right) {
			if(comp.compare(tempLeft[i], tempRight[j]) <=0) {
				words[k] = tempLeft[i];
				i++;
			}
			else {
				words[k] = tempRight[j];
				j++;
			}
			k++;	
		}
		while(i < left) {
			words[k] = tempLeft[i];
			i++;
			k++;
		}
		while(j < right) {
			words[k] = tempRight[j];
			j++;
			k++;
		}
	}
	
	/**
	 * This method will act as the main recursive
	 * method for implementing MergeSort.
	 * @param words
	 * @param comp
	 * @param start
	 * @param end
	 */
	private static void MergeSortRec(String[] words, Comparator<String> comp, int start, int end) {
		if(start < end) {
			int mid = (start+end)/2;
			MergeSortRec(words, comp, start, mid);
			MergeSortRec(words, comp, mid+1, end);
			Merge(words, comp, start, mid, end); // to combine the two above
		}
	}

}
