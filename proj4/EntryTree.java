package edu.iastate.cs228.proj4;

import java.util.Arrays;

/**
 * 
 * @author Lillian Krohn
 *
 *
 *         An entry tree class.
 *
 *
 */
public class EntryTree<K, V> {
	// Dummy root node.
	// Made public for grading.
	public Node root;

	/**
	 * 
	 * You are allowed to add at most TWO more data fields to EntryTree class of int
	 * type ONLY if you need to.
	 * 
	 */
	int prefixTracker;

	// All made public for grading.
	public class Node implements EntryNode<K, V> {
		public Node child; // reference to the first child node
		public Node parent; // reference to the parent node
		public Node prev; // reference to the previous sibling
		public Node next; // reference to the next sibling
		public K key; // the key for this node
		public V value; // the value at this node

		public Node(K aKey, V aValue) {
			key = aKey;
			value = aValue;
			child = null;
			parent = null;
			prev = null;
			next = null;
		}

		@Override
		public EntryNode<K, V> parent() {
			// TODO
			return parent;
		}

		@Override
		public EntryNode<K, V> child() {
			// TODO
			return child;
		}

		@Override
		public EntryNode<K, V> next() {
			// TODO
			return next;
		}

		@Override
		public EntryNode<K, V> prev() {
			// TODO
			return prev;
		}

		@Override
		public K key() {
			// TODO
			return key;
		}

		@Override
		public V value() {
			// TODO
			return value;
		}
	}

	public EntryTree() {
		root = new Node(null, null);
	}

	/**
	 * A helper method to minimize the duplicate code in methods for checking
	 * exceptions.
	 * 
	 * @param arrayVal
	 */
	public void exceptionChecker(K[] keyarr) {
		for (int i = 0; i < keyarr.length; i++) {
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}
		}
	}

	/**
	 * Returns the value of the entry with a specified key sequence, or {@code null}
	 * if this tree contains no entry with this key sequence.
	 * 
	 * This method returns {@code null} if {@code keyarr} is null or if its length
	 * is {@code 0}. If any element of {@code keyarr} is {@code null}, then the
	 * method throws a {@code NullPointerException}. The method returns the value of
	 * the entry with the key sequence in {@code keyarr} or {@code null} if this
	 * tree contains no entry with this key sequence. An example is given in
	 * provided sample input and output files to illustrate this method.
	 *
	 * @param keyarr
	 *            Read description.
	 * @return Read description.
	 * @throws NullPointerException
	 *             Read description.
	 */
	public V search(K[] keyarr) {
		// TODO
		Node cur = root;
		updatePrefixTracker(keyarr);
		if (keyarr == null || keyarr.length == 0 || prefixTracker < keyarr.length) {
			return null;
		}
		for (int i = 0; i < keyarr.length; i++) {
			exceptionChecker(keyarr);
			cur = locateKids(cur, keyarr[i]);
			if (cur == null) {
				return null;
			}
		}
		return cur.value;
	}

	/**
	 * Locates the child node with the desired key.
	 * 
	 * @param cur
	 * @param key
	 * @return
	 */
	private Node locateKids(Node cur, K key) {
		if (cur.child == null) {
			return null;
		}
		cur = cur.child;
		while ((cur != null) && !cur.key.equals(key)) {
			cur = cur.next;
		}
		return cur;

	}

	/**
	 * 
	 * This method returns an array of type {@code K[]} with the longest prefix of
	 * the key sequence specified in {@code keyarr} such that the keys in the prefix
	 * label the nodes on the path from the root to a node. The length of the
	 * returned array is the length of the longest prefix.
	 * 
	 * This method returns {@code null} if {@code keyarr} is {@code null}, or if its
	 * length is {@code 0}. If any element of {@code keyarr} is {@code null}, then
	 * the method throws a {@code NullPointerException}. A prefix of the array
	 * {@code keyarr} is a key sequence in the subarray of {@code keyarr} from index
	 * {@code 0} to any index {@code m>=0}, i.e., greater than or equal to; the
	 * corresponding suffix is a key sequence in the subarray of {@code keyarr} from
	 * index {@code m+1} to index {@code keyarr.length-1}. The method returns an
	 * array of type {@code K[]} with the longest prefix of the key sequence
	 * specified in {@code keyarr} such that the keys in the prefix are,
	 * respectively, with the nodes on the path from the root to a node. The lngth
	 * of the returned array is the length of the longest prefix. Note that if the
	 * length of the longest prefix is {@code 0}, then the method returns
	 * {@code null}. This method can be used to select a shorted key sequence for an
	 * {@code add} command to create a shorter path of nodes in the tree. An example
	 * is given in the attachment to illustrate how this method is used with the
	 * {@code #add(K[] keyarr, V aValue)} method.
	 * 
	 * NOTE: In this method you are allowed to use {@link java.util.Arrays}'s
	 * {@code copyOf} method only.
	 * 
	 * @param keyarr
	 *            Read description.
	 * @return Read description.
	 * @throws NullPointerException
	 *             Read description.
	 */
	public K[] prefix(K[] keyarr) {
		// TODO

		if (keyarr == null || keyarr.length == 0) {
			return null;
		}
		exceptionChecker(keyarr);
		Node cur = root;
		for (int i = 0; i < keyarr.length; i++) {
			if (cur.child != null) {
				cur = cur.child;
				while (cur.key != keyarr[i] && cur.next != null) {
					cur = cur.next;
				}
			}
			if (cur.key != keyarr[i]) {
				K[] prefix = (K[]) new Object[i + 1];
				for (int j = 0; j < keyarr.length; j++) {
					prefix[j] = keyarr[i];
					return Arrays.copyOf(keyarr, i); // allowed to use in this method
				}
			}
			if (cur.child == null && keyarr[i] != cur.key) {
				return Arrays.copyOf(keyarr, i);
			}
		}
		return keyarr;
	}

	/**
	 * A helper method to update the lengthOfPrefix variable
	 * 
	 * @param keyarr
	 */
	private void updatePrefixTracker(K[] keyarr) {
		prefixTracker = 0;
		Node cur = root;
		for (int i = 0; i < keyarr.length; i++) {
			exceptionChecker(keyarr);
			cur = locateKids(cur, keyarr[i]);
			if (cur == null) {
				break;
			}
			prefixTracker++;

		}
	}

	/**
	 * 
	 * This method returns {@code false} if {@code keyarr} is {@code null}, its
	 * length is {@code 0}, or {@code aValue} is {@code null}. If any element of
	 * {@code keyarr} is {@code null}, then the method throws a
	 * {@code NullPointerException}.
	 * 
	 * This method locates the node {@code P} corresponding to the longest prefix of
	 * the key sequence specified in {@code keyarr} such that the keys in the prefix
	 * label the nodes on the path from the root to the node. If the length of the
	 * prefix is equal to the length of {@code keyarr}, then the method places
	 * {@code aValue} at the node {@code P} (in place of its old value) and returns
	 * {@code true}. Otherwise, the method creates a new path of nodes (starting at
	 * a node {@code S}) labelled by the corresponding suffix for the prefix,
	 * connects the prefix path and suffix path together by making the node
	 * {@code S} a child of the node {@code P}, and returns {@code true}. An example
	 * input and output files illustrate this method's operation.
	 *
	 * NOTE: In this method you are allowed to use {@link java.util.Arrays}'s
	 * {@code copyOf} method only.
	 * 
	 * @param keyarr
	 *            Read description.
	 * @param Read
	 *            description.
	 * @return Read description.
	 * @throws NullPointerException
	 *             Read description.
	 */
	public boolean add(K[] keyarr, V aValue) {
		// TODO
		Node cur = root;
		if (keyarr == null || keyarr.length == 0 || aValue == null) {
			return false;
		}
		for (int i = 0; i < keyarr.length; i++) {
			exceptionChecker(keyarr);
			Node children = locateKids(cur, keyarr[i]);

			if (children == null) {
				children = new Node(keyarr[i], null);
				children.parent = cur;

				if (cur.child == null) {
					cur.child = children;
				} else {
					Node sibs = cur.child;
					while (sibs.next != null) {
						sibs = sibs.next;
					}
					sibs.next = children;
					children.prev = sibs;
				}
			}
			cur = children;
		}
		cur.value = aValue;
		return true;
	}

	/**
	 * Removes the entry for a key sequence from this tree and returns its value if
	 * it is present. Otherwise, it makes no change to the tree and returns
	 * {@code null}.
	 * 
	 * This method returns {@code null} if {@code keyarr} is {@code null} or its
	 * length is {@code 0}. If any element of {@code keyarr} is {@code null}, then
	 * the method throws a {@code NullPointerException}. The method returns
	 * {@code null} if the tree contains no entry with the key sequence specified in
	 * {@code keyarr}. Otherwise, the method finds the path with the key sequence,
	 * saves the value field of the node at the end of the path, sets the value
	 * field to {@code null}.
	 * 
	 * The following rules are used to decide whether the current node and higher
	 * nodes on the path need to be removed. The root cannot be removed. Any node
	 * whose value is not {@code null} cannot be removed. Consider a non-root node
	 * whose value is {@code null}. If the node is a leaf node (has no children),
	 * then the node is removed. Otherwise, if the node is the parent of a single
	 * child and the child node is removed, then the node is removed. Finally, the
	 * method returns the saved old value.
	 * 
	 * 
	 * @param keyarr
	 *            Read description.
	 * @return Read description.
	 * @throws NullPointerException
	 *             Read description.
	 * 
	 */
	public V remove(K[] keyarr) {
		// TODO
		updatePrefixTracker(keyarr);
		if (keyarr == null || keyarr.length == 0 || prefixTracker < keyarr.length) {
			return null;
		}
		Node cur = root;
		V returnVal = null;
		for (int i = 0; i < keyarr.length && cur != null; i++) {
			exceptionChecker(keyarr);
			cur = locateKids(cur, keyarr[i]);
		}
		if (cur != null) {
			returnVal = cur.value;
			cur.value = null;
			removeHelper(cur);
		}
		return returnVal;
	}

	/**
	 * A helper method for remove to check if there are nodes of keyarr that need to
	 * get removed.
	 * 
	 * @param cur
	 */
	private void removeHelper(Node cur) {
		if (cur == null) {
			return;
		}
		if (cur.child == null && cur.value == null) {
			if (cur.parent != null && cur.parent.child.equals(cur)) {
				cur.parent.child = cur.next;
				removeHelper(cur.parent);
			}
			if (cur.next != null) {
				cur.next.prev = cur.prev;
			}
			if (cur.prev != null) {
				cur.prev.next = cur.next;
			}
		}

	}

	/**
	 * 
	 * This method prints the tree on the console in the output format shown in
	 * provided sample output file. If the tree has no entry, then the method just
	 * prints out the line for the dummy root node.
	 * 
	 */
	// come back and fix
	public void showTree() {
		// TODO
		Node newNode = root;
		showTreeHelper(0, newNode);

	}

	// come back and fix
	private void showTreeHelper(int space, Node cur) {
		if (cur == null) {
			return;
		}
		while (cur != null) {
			for (int i = 0; i < space; i++) {
				System.out.print("  ");
			}
			System.out.print(cur.key + "::" + cur.value + "\n");
			showTreeHelper(space + 1, cur.child);
			cur = cur.next;
		}
	}

	/**
	 * 
	 * Returns all values in this entry tree together with their keys. The order of
	 * outputs would be similar to level order traversal, i.e., first you would get
	 * all values together with their keys in first level from left to right, then
	 * second level, and so on. If tree has no values then it would return
	 * {@code null}.
	 *
	 * For the example image given in description, the returned String[][] would
	 * look as follows:
	 * 
	 * {{"IA","Grow"}, {"ISU","CS228"}}
	 * 
	 * NOTE: In this method you are allowed to use {@link java.util.LinkedList}.
	 * 
	 * 
	 */
	// come back and fix
	public String[][] getAll() {
		// TODO
		Node temp = root;
		int val1 = nodesWithVals(temp);
		String[][] result = new String[val1][2];
		if (val1 != 0) {
			@SuppressWarnings("unchecked")
			V[] val2 = (V[]) new Object[val1];
			String[] keys = new String[val1];
			locateN(temp.child, val2, keys, 0);

			for (int i = 0; i < val1; i++) {
				result[i][0] = keys[i];
				result[i][1] = val2[i].toString();
			}
		}
		return result;
	}

	/**
	 * A helper method to record the number of nodes that have values.
	 * 
	 * @param cur
	 * @return number of nodes w/ values
	 */
	private int nodesWithVals(Node cur) {
		int value = 0;
		if (cur == null) { // base
			return 0;
		}
		if (cur.value != null) {
			value = 1;
		}
		return value + nodesWithVals(cur.next) + nodesWithVals(cur.child); // traverses all nodes

	}

	/**
	 * A helper method that gets the key of the node to return it as a string in the
	 * getAll() method.
	 * 
	 * @param cur
	 * @return the key string
	 */
	private String locateK(Node cur) {
		String keyString = "";
		boolean confirm = true;
		while (cur != root) {
			if (confirm) {
				keyString = cur.key() + keyString;
			}
			if (cur.parent != null) {
				confirm = true;
				cur = cur.parent;
			} else {
				confirm = false;
				cur = cur.prev;
			}
		}
		return keyString;
	}

	/**
	 * A helper method to find the nodes that have values and add them to the
	 * arrays.
	 * 
	 * @param cur
	 * @param keyArray
	 * @param valArray
	 * @param index
	 */
	private void locateN(Node cur, V[] valArray, String[] keyArray, int index) {
		if (cur == null) {
			return;
		}
		int i = index;
		while (cur != null) {
			if (cur.value() != null) {
				while (valArray[i] != null) {
					i++;
				}
				valArray[i] = cur.value();
				keyArray[i] = locateK(cur);
			}
			locateN(cur.next, valArray, keyArray, i);
			cur = cur.child;
		}
	}

}
