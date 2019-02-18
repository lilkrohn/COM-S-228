package edu.iastate.cs228.proj3;

/**
 * 
 * @author Lillian Krohn
 *
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class TestAdaptiveList {
	private AdaptiveList<String> list;
	private Collection<String> myList;

	/**
	 * initializes the commonly used variables in the tests
	 */
	@Before
	public void init() {
		list = new AdaptiveList<>();
		myList = Arrays.asList(null, "l", "e", "l", "lk", null);
	}

	/**
	 * Tests the constructors default size
	 */
	@Test
	public void ConstructorTest1() {
		int initSize = list.size();
		int expected = 0;
		assertEquals("The initalSize should be zero.", expected, initSize);
	}

	/**
	 * checks the initial state of the constructor
	 */
	@Test
	public void ConstructorTest2() {
		boolean linkedUTD = list.getlinkedUTD();
		boolean expected = true;
		assertEquals("The linked list should initially be up to date.", expected, linkedUTD);
	}

	/**
	 * checks if the constructor adds correctly
	 */
	@Test
	public void CollectionTest1() {
		list = new AdaptiveList<>(myList);
		Iterator<String> oIter = myList.iterator(), lIter = list.iterator();
		boolean areEqual = true;
		while (oIter.hasNext() && lIter.hasNext()) {
			String oString = oIter.next(), lString = lIter.next();
			System.out.println("OtherList: " + oString + "   List: " + lString);
			if (oString != lString || (oString != null && !oString.equals(lString))) {
				areEqual = false;
			}
		}
		boolean expected = true;
		assertEquals("After calling the collection constructor, the list should" + " same contents as the collection.",
				expected, areEqual);
	}

	/**
	 * Checks to see if size changes
	 */
	@Test
	public void AddTest1() {
		list.add("bu");
		int newSize = list.size();
		int expected = 1;
		assertEquals("After calling add(\"bu\") on the empty list, the size should have increased to 1.", expected,
				newSize);
	}

	/**
	 * Checks the status of the array(if its up to date)
	 */
	@Test
	public void AddTest2() {
		list.add("bu");
		boolean newSize = list.getarrayUTD();
		boolean expected = false;
		assertEquals("After calling add, the array should be out of date.", expected, newSize);
	}

	/**
	 * Checks at add end
	 */
	@Test
	public void AddPosTest1() {
		list.add("cat");
		list.add(1, "bu");
		int newSize = list.size();
		int expected = 2;
		assertEquals("After calling add(1,\"bu\") on the a list with 1 element, the size should have " + "increased",
				expected, newSize);
	}

	/**
	 * Checks add at beginning
	 */
	@Test
	public void AddPosTest2() {
		list.add("cat");
		list.add(0, "bu");
		int newSize = list.size();
		int expected = 2;
		assertEquals("After calling add(0,\"bu\") on list, the size should have " + "increased.", expected, newSize);
	}

	/**
	 * Tests if the size changes correctly
	 */
	@Test
	public void AddAllTest1() {
		list.add("sop");
		list.addAll(myList);
		int newSize = list.size();
		int expected = myList.size() + 1;
		assertEquals("After calling addAll(otherList) on the list with 1 element, the list should "
				+ "have increased in size by otherList's size.", expected, newSize);
	}

	/**
	 * Tests return false
	 */
	@Test
	public void AddAllTest2() {
		boolean added = list.addAll(new AdaptiveList<String>());
		assertFalse("After adding a list, addAll should be false", added);
	}

	/**
	 * Tests return true
	 */
	@Test
	public void AddAllTest3() {
		boolean added = list.addAll(myList);
		assertTrue("After adding a list, addAll should be true.", added);
	}

	/**
	 * Tests return false
	 */
	@Test
	public void AddAllPosTest1() {
		boolean added = list.addAll(0, new AdaptiveList<String>());
		assertFalse("After adding a list, addAll should be false.", added);
	}

	/**
	 * Tests return true
	 */
	@Test
	public void AddAllPosTest2() {
		boolean added = list.addAll(0, myList);
		assertTrue("After adding a list, addAll should be true.", added);
	}

	/**
	 * Tests that contains works with consecutive adds before it
	 */
	@Test
	public void ContainsTest1() {
		list.add("897");
		list.add("554");
		list.add("3");
		boolean not = list.contains("897");
		assertTrue("After adding(897), the list should contain 897.", not);
	}

	/**
	 * Tests that contains returns false for multiple adds
	 */
	@Test
	public void ContainsTest2() {
		list.add("897");
		list.add("554");
		list.add("3");
		boolean not = list.contains("o");
		assertFalse("After adding non \"o\" elements, the list should not contain \"o\".", not);
	}

	/**
	 * Tests adding the otherList for containsAll
	 */
	@Test
	public void ContainsAllTest1() {
		list.addAll(myList);
		boolean not = list.containsAll(myList);
		assertTrue("A list that just added otherList should be true.", not);
	}

	/**
	 * Tests if containsAll works with otherList
	 */
	@Test
	public void ContainsAllTest2() {
		list.add("z");
		list.addAll(myList);
		boolean not = list.containsAll(myList);
		assertTrue("A list that just added otherList should be true.", not);
	}

	/**
	 * Tests if the correct value is returned for get
	 */
	@Test
	public void GetTest1() {
		list.add("sop");
		String listWord = list.get(0);
		String expected = "sop";
		assertEquals("A list that added sop should be sop at index 0.", expected, listWord);
	}

	/**
	 * Tests if the correct value is returned
	 */
	@Test
	public void IndexOfTest1() {
		list.add("or");
		list.add("or");
		int not = list.indexOf("or");
		int expected = 0;
		assertEquals("IndexOf should be the first index of the requested elt.", expected, not);
	}

	/**
	 * Tests to see if true is returned
	 */
	@Test
	public void IsEmptyTest1() {
		list.add("s");
		list.remove(0);
		boolean isEmpty = list.isEmpty();
		assertTrue("The list should be empty after adding and then removing the elt.", isEmpty);
	}

	/**
	 * Checks to see if there is a match
	 * 
	 */
	@Test
	public void LastIndexOfTest1() {
		list.add("or");
		int not = list.lastIndexOf("or");
		int expected = 0;
		assertEquals("LastIndexOf should return the 0 index of requested element " + "on the list.", expected, not);
	}

	/**
	 * Tests to see if size changes accordingly
	 */
	@Test
	public void RemovePosTest1() {
		list.add("sop");
		list.remove(0);
		int newSize = list.size();
		int expected = 0;
		assertEquals("Should have size 0.", expected, newSize);
	}

	/**
	 * Tests if the list changes accordingly
	 */
	@Test
	public void RemoveObjTest1() {
		list.add("sop");
		list.remove(0);
		int newSize = list.size();
		int expected = 0;
		assertEquals("Should have size 0.", expected, newSize);
	}

	/**
	 * Checks if remove all works on itself
	 */
	@Test
	public void RemoveAllTest1() {
		list.removeAll(list);
		int newSize = list.size();
		int expected = 0;
		assertEquals("After removing itself, should be empty.", expected, newSize);
	}

	/**
	 * Tests if adding otherList works correctly
	 */
	@Test
	public void RetainAllTest1() {
		list.addAll(myList);
		list.retainAll(myList);
		int newSize = list.size();
		int expected = myList.size();
		assertEquals("After add & retain, the size should be the same as " + "the added list size.", expected, newSize);
	}

	/**
	 * Tests the set method's functionality
	 */
	@Test
	public void SetTest1() {
		list.add("sop");
		list.set(0, "else");
		String listWord = list.get(0);
		String expected = "else";
		assertEquals("A list set to 0 should be index 0.", expected, listWord);
	}

	/**
	 * Tests the toArray method with otherList as contents
	 */
	@Test
	public void ToArrayDefaultTest1() {
		list.addAll(myList);
		String result = Arrays.toString(list.toArray());
		String expected = "[null, l, e, l, lk, null]";
		assertEquals("This should return an Object array.", expected, result);
	}

	/**
	 * Tests the reverse method's functionality
	 */
	@Test
	public void ReverseTest1() {
		list.reverse();
		assertEquals(
				"A sequence of items from the most recent array:\n[5, 4, 3, 2, 1]\nA sequence of items from the most recent linked list:\n(1, 2, 3, 4, 5)",
				list.toString());
	}

}