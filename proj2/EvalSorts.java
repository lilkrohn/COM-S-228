package edu.iastate.cs228.proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Lillian Krohn
 *
 */

/**
 * 
 * Performance Response
 * 
 * The expected performance times of QuickSort, MergeSort, Selection Sort, and Binary Search are as follows:
 * - QuickSort: O(nlogn)
 * - MergeSort: O(nlogn)
 * - SelectionSort: O(n^2)
 * - Binary Search: O(logn)
 *	
 * Although my sorts were unable to run as expected, I could see that for the smaller 
 * word list lengths, selection sort runs the fastest, followed my merge sort and then
 * quick sort. However, for the larger data sets, quick sort runs the fastest followed
 * closely by merge sort. Quick sort maintains smaller constants and an O(nlogn) 
 * average, and it doesn't take up as much memory. Because of this, it is no surprise
 * that quick sort runs the fastest for larger data sets and selection sort runs 
 * the slowest. 
 */

public class EvalSorts {
	public static final int kNumberOfWordsToSort = 10000; // -- no longer needed due to project change

	/**
	 * main is responsible only for extracting fileNames from args, reading the
	 * files, and constructing an instance of the this class configured with the
	 * input data. FileNotFoundException and FileConfigurationException exceptions
	 * should be handled in main, i.e., print out appropriate message to the user.
	 */
	public static void main(String args[]) {
		char[] alphabet = null; // ref to the Lexicon it creates.
		String[] wordList = null; // ref to the list of words to be sorted.
		EvalSorts theApp = null; // ref to the app.
		LexiconImpl comp = null; // the concrete lexicon your app uses.

		// TODO
		/*
		 * 
		 * Here you should add code that extracts the file names from the args array,
		 * opens and reads the data from the files,constructs an instance of Lexicon
		 * from the character order file, and then create an instance of this class
		 * (EvalSorts) to act as a configured instance of the application. After you
		 * have constructed the configured instance, you should start it running (see
		 * below).
		 * 
		 * 
		 * 
		 * 
		 */
		
		try{
			comp = new LexiconImpl(readCharacterOrdering(args[0]));
			wordList = readWordsFile(args[1], comp);
		}
		catch(FileNotFoundException exception) {
			System.err.println("File was not found");
			return;
		}
		catch(FileConfigurationException exception) {
			System.err.println("File cannot be configured");
			return;
		}

		// configure an instance of the app
		theApp = new EvalSorts(comp, wordList, kNumberOfWordsToSort); // -- no longer need to use kNumberOFWordsToSort
																		// due to project change
		// now execute that instance
		theApp.runSorts();

	}

	private String[] words; // ref to the word list
	private Lexicon lex; // ref to the relevant lexicon
	private int numWordsToSort = kNumberOfWordsToSort;

	/**
	 * This constructor configures an instance of EvalSorts to sort input read my
	 * main, using the character order read by main and now embedded in an instance
	 * of Lexicon
	 * 
	 * @param lex
	 *            the instance of lexicon to be used
	 * @param wordList
	 *            the wordlist (as array of string) to be sorted
	 * @param numWordsToSort
	 *            each sort will be repeated until it has sorted this many words.
	 */
	public EvalSorts(Lexicon lex, String[] wordList, int numWordsToSort) { // -- no longer need to use NumWordsToSort to
																			// project change
		// TODO
	}

	/**
	 * runSorts() performs the sort evaluation.
	 * 
	 * Note: The three sorters extend a common base so they share the same interface
	 * for starting the sort and collecting statistics. Thus, you should create
	 * instances of the sorter and save references to each in an array of base type.
	 * This allows you to use a simple loop to run all the reports and collect the
	 * statistics.
	 */
	public void runSorts() {

		SorterWithStatistics[] sorters = new SorterWithStatistics[3];
		// TODO
		sorters[0] = new QuickSort();
		sorters[1] = new MergeSort();
		sorters[2] = new SelectionSort();

		//for loop to sort
		//getReport call in for loop
		for(int i = 0; i < sorters.length; i++) {
			sorters[i].sort(words, lex);
	        System.out.println(sorters[i].getReport());
	             
		}
	}
	/**
	 * Reads the characters contained in filename and returns them as a character
	 * array.
	 * 
	 * @param filename
	 *            the file containing the list of characters
	 * @returns an char array representing the ordering of characters to be used or
	 *          null if there is a FileNotFoundException.
	 */
	public static char[] readCharacterOrdering(String filename)
			throws FileNotFoundException, FileConfigurationException {
		// TODO
		if(filename == null || filename.length() == 0) {
			throw new FileNotFoundException();
		}
		File newFile = new File(filename);
		if (!newFile.exists()) {
			throw new FileNotFoundException("File was not found");
		}
		Scanner newScan = new Scanner(newFile);
		ArrayList<String> order = new ArrayList<>();
		String temp;
		while(newScan.hasNextLine()) {
			temp = newScan.nextLine();
			if (temp.length() < 1) {
				throw new FileConfigurationException("File cannot be configued");
			}
			order.add(temp);
		}
		char[] alphabetC = new char[order.size()];
		for (int i = 0; i < order.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (order.get(i).charAt(0) == alphabetC[j]) {
					throw new FileConfigurationException("File cannot be configured");
				}
			}

			alphabetC[i] = order.get(i).charAt(0);
		}
		return alphabetC;
	}

	/**
	 * Reads the words from the file and returns them as a String array.
	 * 
	 * @param filename
	 *            file containing words
	 * @return the words contained in the file or null if there was a
	 *         FileNotFoundException.
	 */
	public static String[] readWordsFile(String filename, Lexicon comp)
			throws FileNotFoundException, FileConfigurationException {
		// TODO
		String temp;
		File newFile = new File(filename);
		if (!newFile.exists()) {
			throw new FileNotFoundException("File was not found");
		}
		Scanner newScan = new Scanner(newFile);
		ArrayList<String> wordsFile = new ArrayList<>();
		while (newScan.hasNextLine()) {
			temp = newScan.nextLine();
			if (temp.length() < 1) {
				throw new FileConfigurationException("File cannot be configured");
			}
			wordsFile.add(temp);
		}
		String[] newString = new String[wordsFile.size()];
		wordsFile.toArray(newString);
		return newString;

	}

}
