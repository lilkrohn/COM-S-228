package edu.iastate.cs228.proj4;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author Lillian Krohn
 * 
 *         JUnit tests for project 4
 *
 */

public class TestEntryTree {

	EntryTree<Character, String> tree = new EntryTree<Character, String>(); // tester

	/**
	 * Tests if add correctly returns true
	 */
	@Test
	public void addTest() {
		Character[] keyarr = { 't', 'e', 's', 't' };
		assertFalse(tree.add(keyarr, null));
	}

	/**
	 * Checks that same length should change the value of the node
	 */
	@Test
	public void addTest2() {
		Character[] keyarr = { 't', 'e', 's', 't' };
		tree.add(keyarr, "change");
		tree.add(keyarr, "check");
		String expected = "check";
		assertEquals(tree.search(keyarr), expected);
	}

	/**
	 * Tests the generic functionality of removing a non-existent node
	 */
	@Test
	public void removeTest() {
		Character[] keyarr = { 't', 'e', 's', 't' };
		tree.add(keyarr, "check");
		Character[] keyarr2 = { 't', 'e', 's', 't', 'i', 'n', 'g' };
		tree.add(keyarr2, "checking");
		String expected = "checking";
		assertEquals(tree.remove(keyarr2), expected);
	}

	/**
	 * Tests that a value is returned from a given keyarr
	 */
	@Test
	public void searchTest() {
		Character[] keyarr = { 't', 'e', 's', 't' };
		tree.add(keyarr, "check");
		String expected = "check";
		assertEquals(tree.search(keyarr), expected);
	}

	/**
	 * Tests the basic functionality of prefix
	 */
	@Test
	public void prefixTest() {
		Character[] keyarr = { 't', 'e', 's', 't' };
		tree.add(keyarr, "check");
		Character[] keyarr2 = { 't', 'e', 's', 't', 'i', 'n', 'g' };
		assertArrayEquals(tree.prefix(keyarr2), keyarr);
	}

	/**
	 * Tests if the getAll method functions correctly
	 */
	@Test
	public void getAllTest() {
		String[][] arr = tree.getAll();
		String[][] expected = { { "1", "One" }, { "2", "Two" }, { "3", "Three" }, { "4", "Four" }, { "5", "Five" },
				{ "6", "Six" }, { "7", "Seven" } };
		for (int i = 0; i < 7; i++) {
			assertEquals(expected[i][0], arr[i][0]);
			assertEquals(expected[i][1], arr[i][1]);
		}
		tree = new EntryTree<>();

		assertEquals(0, tree.getAll().length);

	}
}
