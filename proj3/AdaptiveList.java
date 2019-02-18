package edu.iastate.cs228.proj3;

import java.util.Arrays;

/**
 *  @author Lillian Krohn
 *
 *
 *  An implementation of List<E> based on a doubly-linked list 
 *  with an array for indexed reads/writes
 *
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class AdaptiveList<E> implements List<E> {
	public class ListNode {
		public E data;
		public ListNode next;
		public ListNode prev;

		public ListNode(E item) {
			data = item;
			next = prev = null;
		}
	}

	public ListNode head; // dummy node made public for testing.
	public ListNode tail; // dummy node made public for testing.
	private int numItems; // number of data items
	private boolean linkedUTD; // true if the linked list is up-to-date.

	public E[] theArray; // the array for storing elements
	private boolean arrayUTD; // true if the array is up-to-date.

	public AdaptiveList() {
		clear();
	}

	@Override
	public void clear() {
		head = new ListNode(null);
		tail = new ListNode(null);
		head.next = tail;
		tail.prev = head;
		numItems = 0;
		linkedUTD = true;
		arrayUTD = false;
		theArray = null;
	}

	public boolean getlinkedUTD() {
		return linkedUTD;
	}

	public boolean getarrayUTD() {
		return arrayUTD;
	}

	public AdaptiveList(Collection<? extends E> c) {
		// TODO
		this();
		for (E e : c) {
			add(e);
		}
		linkedUTD = true;
		arrayUTD = false;
		theArray = null;
	}

	// Removes the node from the linked list.
	// This method should be used to remove a node
	// from the linked list.
	private void unlink(ListNode toRemove) {
		if (toRemove == head || toRemove == tail)
			throw new RuntimeException("An attempt to remove head or tail");
		toRemove.prev.next = toRemove.next;
		toRemove.next.prev = toRemove.prev;
	}

	// Inserts new node toAdd right after old node current.
	// This method should be used to add a node to the linked list.
	private void link(ListNode current, ListNode toAdd) {
		if (current == tail)
			throw new RuntimeException("An attempt to chain after tail");
		if (toAdd == head || toAdd == tail)
			throw new RuntimeException("An attempt to add head/tail as a new node");
		toAdd.next = current.next;
		toAdd.next.prev = toAdd;
		toAdd.prev = current;
		current.next = toAdd;
	}

	private void updateArray() // makes theArray up-to-date.
	{
		if (numItems < 0)
			throw new RuntimeException("numItems is negative: " + numItems);
		if (!linkedUTD)
			throw new RuntimeException("linkedUTD is false");
		// TODO
		theArray = (E[]) new Object[numItems];
		for (int i = 0; i < theArray.length; i++) {
			theArray[i] = findNode(i + 1).data;
		}
		arrayUTD = true;
	}

	private void updateLinked() // makes the linked list up-to-date.
	{
		if (numItems < 0)
			throw new RuntimeException("numItems is negative: " + numItems);
		if (!arrayUTD)
			throw new RuntimeException("arrayUTD is false");

		if (theArray == null || theArray.length < numItems)
			throw new RuntimeException("theArray is null or shorter");

		// TODO
		head = new ListNode(null);
		tail = new ListNode(null);
		head.next = tail;
		tail.prev = head;
		numItems = 0;

		for (int i = 0; i < theArray.length; i++) {
			add(theArray[i]);
		}
	}

	@Override
	public int size() {
		// TODO
		return numItems;
	}

	@Override
	public boolean isEmpty() {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		return numItems == 0;
	}

	@Override
	public boolean add(E obj) {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		ListNode newNode = new ListNode(obj);
		link(tail.prev, newNode);
		numItems++;
		arrayUTD = false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		if (c.isEmpty()) {
			return false;
		}

		int size = c.size();
		int i = 0;
		for (E e : c) {
			add(e);
			i++;
			if (i >= size) {
				break;
			}
		}
		return true;

	}

	@Override
	public boolean remove(Object obj) {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		if (!contains(obj)) {
			return false;
		}
		/*
		 * for (int pos = 0; pos < numItems; pos++) for (ListNode node = head.next; node
		 * != tail && node != null; node = node.next) { if (node.data.equals(obj)) {
		 * unlink(node); return true; } } return false;
		 */
		int pos = indexOf(obj);
		if (pos == 0) {
			pos++;
		}
		unlink(findNode(pos));
		numItems--;

		arrayUTD = false;

		return true;
	}

	private void checkIndex(int pos) // a helper method
	{
		if (pos >= numItems || pos < 0)
			throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + numItems);
	}

	private void checkIndex2(int pos) // a helper method
	{
		if (pos > numItems || pos < 0)
			throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + numItems);
	}

	private void checkNode(ListNode cur) // a helper method
	{
		if (cur == null || cur == tail)
			throw new RuntimeException("numItems: " + numItems + " is too large");
	}

	private ListNode findNode(int pos) // a helper method
	{
		ListNode cur = head;
		for (int i = 0; i < pos; i++) {
			checkNode(cur);
			cur = cur.next;
		}
		checkNode(cur);
		return cur;
	}

	@Override
	public void add(int pos, E obj) {
		// TODO
		if (pos == size()) {
			add(obj);
		}
		if (pos != 0) {
			checkIndex2(pos);
		} else {
			if (!linkedUTD) {
				updateLinked();
			}
			if (pos == 0) {
				link(head, new ListNode(obj));
			} else {
				link(findNode(pos - 1), new ListNode(obj));
			}
			numItems++;
		}

		arrayUTD = false;

	}

	@Override
	public boolean addAll(int pos, Collection<? extends E> c) {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		if (size() != 0) {
			checkIndex2(pos);
		}
		if (c.isEmpty()) {
			return false;
		}

		int size = c.size();
		int i = 0;
		for (E e : c) {
			add(i + pos, e);
			i++;
			if (i >= size) {
				break;
			}
		}
		return true;
	}

	@Override
	public E remove(int pos) {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		checkIndex(pos);
		if (pos == 0) {
			if (head == null) {
				throw new NoSuchElementException();
			}
			E result = head.next.data;
			removeFirst();
			return result;
		}
		ListNode toRemove = findNode(pos);
		E returnValue = toRemove.data;
		unlink(toRemove);

		numItems--;
		return returnValue;

	}

	/**
	 * Helper method for remove(). Will run after each pass though.
	 */
	private void removeFirst() {
		if (head == null)
			throw new NoSuchElementException();
		unlink(head.next);
		numItems--;
	}

	@Override
	public E get(int pos) {
		// TODO
		if (!arrayUTD) {
			updateArray();
		}
		checkIndex(pos);
		return theArray[pos];
	}

	@Override
	public E set(int pos, E obj) {
		// TODO
		checkIndex(pos);
		if (!arrayUTD) {
			updateArray();
		}
		E returnVal = theArray[pos];
		theArray[pos] = obj;
		linkedUTD = false;
		return returnVal;
	}

	/**
	 * If the number of elements is at most 1, the method returns false. Otherwise,
	 * it reverses the order of the elements in the array without using any
	 * additional array, and returns true. Note that if the array is modified, then
	 * linkedUTD needs to be set to false.
	 */
	public boolean reverse() {
		// TODO //come back
		if (numItems <= 1) {
			return false;
		}
		if (!arrayUTD) {
			updateArray();
		}
		// int i = 0;
		int j = theArray.length - 1;
		for (int i = 0; i < theArray.length / 2; i++, j--) {
			E temp = theArray[i];
			theArray[i] = theArray[j];
			theArray[j] = temp;
		}
		linkedUTD = false;
		return true;
	}

	/**
	 * If the number of elements is at most 1, the method returns false. Otherwise,
	 * it swaps the items positioned at even index with the subsequent one in odd
	 * index without using any additional array, and returns true. Note that if the
	 * array is modified, then linkedUTD needs to be set to false.
	 */
	public boolean reorderOddEven() {
		// TODO //need to fix
		if (numItems <= 1) {
			return false;
		}
		if (!arrayUTD) {
			updateArray();
		}

		int i = 0;
		int j = theArray.length - 1;
		E temp = theArray[i];
		while (i < j) {
			if (numItems % 2 != 0) {
				i++;
			}
			if (numItems % 2 == 0) {
				j--;
			}
			temp = theArray[j - 1];
			theArray[j - 1] = theArray[j];
			theArray[j] = temp;
		}

		linkedUTD = false;
		return true;
	}

	@Override
	public boolean contains(Object obj) {
		// TODO

		ListNode cur;
		for (cur = head; cur != null; cur = cur.next) {
			if (cur != head && cur != tail) {
				if (obj == cur.data || obj != null && cur.data != null && obj.equals(cur.data)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO
		if (!linkedUTD) {
			updateLinked();
		}
		Iterator<?> iter = c.iterator();
		while (iter.hasNext()) {
			if (!contains(iter.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int indexOf(Object obj) {
		// TODO
		ListNode cur;
		int pos = 0;
		for (cur = head; cur != tail; cur = cur.next, pos++) {
			if (obj == cur.data || obj != null && obj.equals(cur.data)) {
				if (pos == 1) {
					return 0;
				}
				if (pos > 0 && pos != numItems) {
					pos--;
				}
				return pos;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object obj) {
		// TODO
		ListIterator<E> iter = listIterator(numItems);
		while (iter.hasPrevious()) {
			E data = iter.previous();
			if (obj == data || obj != null && obj.equals(data))
				return iter.nextIndex();
		}
		return -1;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO
		if (c == null)
			throw new NullPointerException();
		boolean change = false;
		Iterator<E> iter = this.iterator();
		while (iter.hasNext())
			if (c.contains(iter.next())) {
				iter.remove();
				change = true;
			}
		return change;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO
		if (c == null) {
			throw new NullPointerException();
		}
		boolean changed = false;
		ListIterator<E> iter = listIterator(0);

		while (iter.hasNext()) {
			E data = iter.next();
			if (!c.contains(data)) {
				iter.remove();
				changed = true;
			}
		}
		return changed;

	}

	@Override
	public Object[] toArray() {
		// TODO
		if (!arrayUTD) {
			updateArray();
		}
		return Arrays.copyOf(theArray, numItems);

	}

	/**
	 * In here you are allowed to use only java.util.Arrays.copyOf method.
	 */
	@Override
	public <T> T[] toArray(T[] arr) {
		// TODO
		if (numItems > arr.length) {
			arr = Arrays.copyOf(arr, numItems);
		}
		if (numItems < arr.length) {
			arr[numItems] = null;
		}
		return arr;
	}

	@Override
	public List<E> subList(int fromPos, int toPos) {
		throw new UnsupportedOperationException();
	}

	private class AdaptiveListIterator implements ListIterator<E> {
		private int index; // index of next node;
		private ListNode cur; // node at index - 1
		private ListNode last; // node last visited by next() or previous()
		private int lastNextPrev;
		private boolean nextCall = false;

		public AdaptiveListIterator() {
			if (!linkedUTD)
				updateLinked();
			// TODO
			cur = findNode(index - 1); // referencing the helper method
			last = null;
			index = 0;
		}

		public AdaptiveListIterator(int pos) {
			checkIndex2(pos); // might need to remove

			if (!linkedUTD) {
				updateLinked();
			}

			// TODO

			last = null;
			index = pos;
			if (pos == numItems) {
				cur = tail.prev;
			} else if (pos == 0) {
				cur = head;
			} else {
				cur = findNode(pos); // helper
			}
		}

		@Override
		public boolean hasNext() {
			// TODO
			return numItems > index;

		}

		@Override
		public E next() {
			// TODO

			E result = get(index);
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			if (index >= numItems) {
				throw new RuntimeException();
			}
			index++;

			if (cur == null) {
				last = head;
			} else {
				last = cur.next;
			}
			return result;

		}

		@Override
		public boolean hasPrevious() {
			// TODO
			return index > 0;
		}

		@Override
		public E previous() {
			// TODO
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (index <= 0) {
				throw new RuntimeException("Invalid index");
			}
			index--;

			last = cur;
			cur = cur.prev;
			nextCall = false;
			return last.data;

		}

		@Override
		public int nextIndex() {
			// TODO
			int result;
			if (hasNext()) {
				result = index;
			} else {
				result = numItems;
			}
			return result;
		}

		@Override
		public int previousIndex() {
			// TODO
			return index - 1;
		}

		@Override
		public void remove() {
			// TODO
			if (last == null) {
				throw new IllegalStateException();
			}
			cur = last;
			cur.next.prev = cur.prev;
			cur.prev.next = cur.next;
			last = null;

			index--;
		}

		@Override
		public void add(E obj) {
			// TODO

			ListNode toAdd = new ListNode(obj);
			toAdd.prev = cur.prev;
			toAdd.next = cur.next;

			cur.prev = toAdd;
			toAdd.prev.next = toAdd;
			cur.next.prev = toAdd;

			last = null;
			index++;

		}

		@Override
		public void set(E obj) {
			// TODO
			if (last == null) {
				throw new IllegalStateException();
			}
			last.data = obj;
		}
	} // AdaptiveListIterator

	@Override
	public boolean equals(Object obj) {
		if (!linkedUTD)
			updateLinked();
		if ((obj == null) || !(obj instanceof List<?>))
			return false;
		List<?> list = (List<?>) obj;
		if (list.size() != numItems)
			return false;
		Iterator<?> iter = list.iterator();
		for (ListNode tmp = head.next; tmp != tail; tmp = tmp.next) {
			if (!iter.hasNext())
				return false;
			Object t = iter.next();
			if (!(t == tmp.data || t != null && t.equals(tmp.data)))
				return false;
		}
		if (iter.hasNext())
			return false;
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new AdaptiveListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new AdaptiveListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int pos) {
		checkIndex2(pos);
		return new AdaptiveListIterator(pos);
	}

	// Adopted from the List<E> interface.
	@Override
	public int hashCode() {
		if (!linkedUTD)
			updateLinked();
		int hashCode = 1;
		for (E e : this)
			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
		return hashCode;
	}

	// You should use the toString*() methods to see if your code works as expected.
	@Override
	public String toString() {
		// Other options System.lineSeparator or
		// String.format with %n token...
		// Not making data field.
		String eol = System.getProperty("line.separator");
		return toStringArray() + eol + toStringLinked();
	}

	public String toStringArray() {
		String eol = System.getProperty("line.separator");
		StringBuilder strb = new StringBuilder();
		strb.append("A sequence of items from the most recent array:" + eol);
		strb.append('[');
		if (theArray != null)
			for (int j = 0; j < theArray.length;) {
				if (theArray[j] != null)
					strb.append(theArray[j].toString());
				else
					strb.append("-");
				j++;
				if (j < theArray.length)
					strb.append(", ");
			}
		strb.append(']');
		return strb.toString();
	}

	public String toStringLinked() {
		return toStringLinked(null);
	}

	// iter can be null.
	public String toStringLinked(ListIterator<E> iter) {
		int cnt = 0;
		int loc = iter == null ? -1 : iter.nextIndex();

		String eol = System.getProperty("line.separator");
		StringBuilder strb = new StringBuilder();
		strb.append("A sequence of items from the most recent linked list:" + eol);
		strb.append('(');
		for (ListNode cur = head.next; cur != tail;) {
			if (cur.data != null) {
				if (loc == cnt) {
					strb.append("| ");
					loc = -1;
				}
				strb.append(cur.data.toString());
				cnt++;

				if (loc == numItems && cnt == numItems) {
					strb.append(" |");
					loc = -1;
				}
			} else
				strb.append("-");

			cur = cur.next;
			if (cur != tail)
				strb.append(", ");
		}
		strb.append(')');
		return strb.toString();
	}
}
