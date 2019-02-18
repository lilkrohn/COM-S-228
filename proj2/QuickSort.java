package edu.iastate.cs228.proj2;

import java.util.Comparator;

/**
 * 
 * @author Lillian Krohn
 * 
 */
public class QuickSort extends SorterWithStatistics {

	// This method will be called by the base class sort() method to
	// actually perform the sort.
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		// TODO: implement QuickSort;
		quickSort(words, comp, 0, words.length - 1);
		wordsSorted = words.length;
	}

	/**
	 * This helper method will use the quick sort
	 * algorithm in order to sort the elements
	 * @param words
	 * @param comp
	 * @param firstVal
	 * @param lastVal
	 */
	private static void quickSort(String[] words, Comparator<String> comp, int firstVal, int lastVal) {
		if (firstVal >= lastVal) {
			return;
		}
		int mid = partition(words, comp, firstVal, lastVal);
		quickSort(words, comp, firstVal, mid);
		quickSort(words, comp, mid + 1, lastVal);

	}

	/**
	 * This helper method will help partition the 
	 * word list in order to be able to sort by parts
	 * @param words
	 * @param comp
	 * @param firstVal
	 * @param lastVal
	 * @return
	 */
	private static int partition(String[] words, Comparator<String> comp, int firstVal, int lastVal) {
		String pivotVal = words[firstVal];
		int leftVal = firstVal;
		int rightVal = lastVal;
		while (rightVal > leftVal) {
			while (comp.compare(words[leftVal], pivotVal) < 0) {
				leftVal++;
			}
			while (comp.compare(words[rightVal], pivotVal) > 0) {
				rightVal--;
			}
			if (leftVal < rightVal) {
				String temp = words[leftVal];
				words[leftVal++] = words[rightVal];
				words[rightVal--] = temp;
			} else {
				break;
			}
		}
		return rightVal;
	}
}
	
