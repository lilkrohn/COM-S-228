package edu.iastate.cs228.proj2;

import java.util.Comparator;

/**
 * 
 * @author Lillian Krohn
 *
 */
public abstract class SorterWithStatistics implements Sorter {

	private Stopwatch timer = new Stopwatch();
	protected int wordsSorted;
	protected int totalWordsSorted;
	protected int timeElapsed;
	protected int totalTimeElapsed;
	
	/***
	 * Default constructor
	 */
	public SorterWithStatistics() {
		// TODO
		//do nothing
	}

	/***
	 * Public interface to sortHelper that keeps track of performance
	 * statistics, including counting words sorted and timing sort instances.
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param comp
	 *            Comparator used to sort the input array.
	 */
	public void sort(String[] words, Comparator<String> comp) {
		// TODO
		wordsSorted = 0;
		totalWordsSorted = 0;
		timeElapsed = 0;
		timer.start();
		sortHelper(words, comp);
		timer.stop();
		
		
	}

	/**
	 * Sorts the array words.
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param comp
	 *            Comparator used to sort the input array.
	 */
	protected abstract void sortHelper(String[] words, Comparator<String> comp);

		
	/**
	 * Returns number of words sorted in last sort. Throws IllegalStateException
	 * if nothing has been sorted.
	 * 
	 * @return number of words sorted in last sort.
	 */
	public int getWordsSorted() {
		// TODO
		/**if(wordsSorted == 0) {
			throw new IllegalStateException();
		}
		return wordsSorted;*/ //-- no longer needed due to project change
		return 0;
	}

	/**
	 * Returns time the last sort took. Throws IllegalStateException if nothing
	 * has been sorted.
	 * 
	 * @return time last sort took.
	 */
	public long getTimeToSortWords() {
		// TODO
		//if(wordsSorted == 0) {
			//throw new IllegalStateException();
		//}
		//return time; -- no longer needed due to project change
		
		return 0L;
	}

	/**
	 * Returns total words sorted by this instance.
	 * 
	 * @return total number of words sorted.
	 */
	public int getTotalWordsSorted() {
		// TODO
		//return totalSorted; -- no longer needed due to project change
		return 0;
	}

	/**
	 * Returns the total amount of time spent sorting by this instance.
	 * 
	 * @return total time spent sorting.
	 */
	public long getTotalTimeToSortWords() {
		// TODO 
		//return totalTime;
		return timer.getElapsedTime();
	}

	/**
	 * @return a summary of statistics for the last sorting run.
	 * 
	 *         See the project description for details about what to include.
	 *         This method should NOT generate output directly.
	 */
	public String getReport() {
		// TODO
		//make the sort into a string
		//return the string
		//use getClass
		//return the time
		return getClass().getSimpleName() + ": " + getTotalTimeToSortWords(); //fix
		
	}
}
