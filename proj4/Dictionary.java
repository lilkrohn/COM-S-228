package edu.iastate.cs228.proj4;

import java.util.*;
import java.io.*;

/**
 * @author Lillian Krohn
 * 
 * 
 *         An application class that uses EntryTree class to process a file of
 *         commands (one per line). Each command line consists of the name of a
 *         public method of the EntryTree class followed by its arguments in
 *         string form if the method has arguments. The name of the file is
 *         available to the main method from its String[] parameter at index 0.
 *         You can assume that the command file is always in correct format. The
 *         main method creates an object of the EntryTree class with K being
 *         Character and V being String, reads each line from the command file,
 *         decodes the line by splitting into String parts, forms the
 *         corresponding arguments, and calls the public method from the
 *         EntryTree object with the arguments, and prints out the result on the
 *         console. Note that the name of a public method in the EntryTree class
 *         on each command line specifies that the public method should be
 *         called from the EntryTree object. A sample input file of commands and
 *         a sample output file are provided.
 * 
 *         The sample output file was produced by redirecting the console output
 *         to a file, i.e.,
 * 
 *         java Dictionary infile.txt > outfile.txt
 * 
 * 
 *         NOTE that all methods of EntryTree class can be present as commands
 *         except for getAll method inside the input file.
 * 
 * 
 */
public class Dictionary {
	public static void main(String[] args) throws FileNotFoundException {
		// TODO
		EntryTree<Character, String> tree = new EntryTree<Character, String>();
		File inputFile = null;
		try {
			inputFile = new File(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) { // for if file is not found
			throw new ArrayIndexOutOfBoundsException();
		}
		Scanner scan = new Scanner(inputFile);
		while (scan.hasNextLine()) {
			String nextLine = scan.nextLine();
			String[] sArray = nextLine.split("\\s+");
			// Go through each method:
			if (sArray[0].equalsIgnoreCase("add")) { // use ignore case?
				System.out.println("Command: add " + sArray[1] + " " + sArray[2] + "\nResult from an add: "
						+ tree.add(toArray(sArray[1]), sArray[2]));
			} else if (sArray[0].equalsIgnoreCase("remove")) {
				System.out.println(
						"Command: remove " + sArray[1] + "\nResult from a remove: " + tree.remove(toArray(sArray[1])));
			} else if (sArray[0].equalsIgnoreCase("showTree")) {
				System.out.println("Command: showTree\n\nResult from a showTree: ");
				tree.showTree();
			} else if (sArray[0].equalsIgnoreCase("search")) {
				System.out.println(
						"Command: search " + sArray[1] + "\nResult from a search: " + tree.search(toArray(sArray[1])));
			} else if (sArray[0].equalsIgnoreCase("prefix")) {
				System.out.println("Command: prefix " + sArray[1]);
				System.out.print("Result from a prefix: ");
				Character[] c = tree.prefix(toArray(scan.next()));
				for (int i = 0; i < c.length; i++) {
					System.out.print(c[i]);
				}
			}
			System.out.println("");
		}
		scan.close();
	}

	private static Character[] toArray(String string) {
		if (string == null) {
			return null;
		}
		Character[] array = new Character[string.length()];
		for (int i = 0; i < string.length(); i++) {
			array[i] = new Character(string.charAt(i));
		}
		return array;
	}

}
