package edu.iastate.cs228.proj3;

/**
 * author Lillian Krohn
 * 
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TestAdaptiveIterator {

	private AdaptiveList<String> myList;
	private ListIterator<String> iterAtBegin;
	private ListIterator<String> iterAtMiddle;
	private ListIterator<String> iterAtEnd;

	/**
	 * Common variables are initialized here
	 */
	@Before
	public void init() {
		myList = new AdaptiveList<>(Arrays.asList(null, "l", "e", "l", "lk", null));
		iterAtBegin = myList.listIterator();
		iterAtMiddle = myList.listIterator(2);
		iterAtEnd = myList.listIterator(6);
	}

	/**
	 * Tests the constructors initial state
	 */
	@Test
	public void BeginConstructorTest1() {
		int nextIndex = iterAtBegin.nextIndex();
		int expected = 0;
		assertEquals("The iterator should be at 0.", expected, nextIndex);
		assertTrue("The iterator should have a next value.", iterAtBegin.hasNext());
	}

	/**
	 * Tests the state of the iterator in the middle
	 */
	@Test
	public void MidConstructorTest1() {
		int nextIndex = iterAtMiddle.nextIndex();
		int expected = 2;
		assertEquals("The iterator should begin at 2 (nextIndex = 2).", expected, nextIndex);
		assertTrue("The iterator should have a next value.", iterAtMiddle.hasNext());
	}

	/**
	 * Tests the state of the iterator at the end
	 */
	@Test
	public void EndConstructorTest1() {
		int nextIndex = iterAtEnd.nextIndex();
		int expected = 6;
		assertEquals("The iterator should begin at 2(nextIndex = 6).", expected, nextIndex);
		assertFalse("The iterator should not have a next value.", iterAtEnd.hasNext());
	}

	/**
	 * Tests next with a null val
	 */
	@Test
	public void NextTest1() {
		String next1 = iterAtBegin.next();
		String expected1 = null;
		assertEquals("The next value should be null.", expected1, next1);
	}

	/**
	 * Tests has next with initial iterator
	 */
	@Test
	public void HasNextTest1() {
		while (iterAtBegin.hasNext())
			iterAtBegin.next();
	}

	/**
	 * Tests that nextIndex functions correctly
	 */
	@Test
	public void NextIndexTest1() {
		while (iterAtBegin.nextIndex() < 6)
			iterAtBegin.next();
	}

	/**
	 * Tests with non-null value
	 */
	@Test
	public void PrevTest1() {
		iterAtEnd.previous();
		String next2 = iterAtEnd.previous();
		String expected2 = "lk";
		assertEquals("The previous value should be \"lk\".", expected2, next2);
	}

	/**
	 * Tests if this functions correctly in a loop
	 */
	@Test
	public void HasPrevTest1() {
		while (iterAtEnd.hasPrevious())
			iterAtEnd.previous();
	}

	/**
	 * Tests if this functions correctly with a loop
	 */
	@Test
	public void PrevIndexTest1() {
		while (iterAtEnd.previousIndex() >= 0)
			iterAtEnd.previous();
	}

	/**
	 * Makes sure add increases the value to be returned
	 */
	@Test
	public void AddTest1() {
		iterAtMiddle.add("Something");
		int nextIndex = iterAtMiddle.nextIndex();
		int expected = 3;
		assertEquals("The nextIndex should be increased by one.", expected, nextIndex);
	}

	/**
	 * Makes sure a call to set does not alter remove
	 */
	@Test
	public void RemoveTest1() {
		iterAtMiddle.next();
		iterAtMiddle.set("00");
		iterAtMiddle.remove();
	}

	/**
	 * Checks if after a call to add, set fails
	 */
	@Test(expected = IllegalStateException.class)
	public void SetTest1() {
		iterAtMiddle.next();
		iterAtMiddle.add("p");
		iterAtMiddle.set("p");
	}
}
