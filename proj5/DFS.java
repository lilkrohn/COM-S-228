package edu.iastate.cs228.proj5;
/*
 *  @author Lillian Krohn
 *
 *
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class DFS {
	/**
	 * This method creates a color map and a pred map (see example Graph.java under
	 * Week 13 of Lecture notes) and an empty stack of type Stack<V>. It colors each
	 * vertex "white" and sets the value of each vertex to null in the pred map (see
	 * Graph.java). Then as long as there is a "white" vertex w left, the method
	 * calls visitDFS(...) on the vertex w along with other parameters. If
	 * visitDFS(...) returns false, then this method returns null. Otherwise, it
	 * returns the stack containing the list of all vertices in a topological order,
	 * which is produced by the visitDFS(...) method. If aGraph is null, then it
	 * throws IllegalArgumentException.
	 */
	public static <V> Stack<V> depthFirstSearch(DiGraph<V> aGraph) {
		// TODO
		if (aGraph == null) {
			throw new IllegalArgumentException();
		}
		HashMap<V, String> color = new HashMap<V, String>();
		HashMap<V, V> pred = new HashMap<V, V>();
		Stack<V> topoOrder = new Stack<V>();

		for (V vertW : aGraph.vertices()) {
			color.put(vertW, "white");
			pred.put(vertW, null);
		}
		for (V vertW : aGraph.vertices()) {
			if (color.get(vertW).equals("white")) {
				if (!visitDFS(aGraph, vertW, color, pred, topoOrder)) {
					return null;
				}
			}
		}

		return topoOrder;
	}

	/**
	 * 
	 * This method implements an iterative depth-first search algorithm for checking
	 * if the given graph is acyclic (has no cycles) and if so, generates a stack
	 * (named topoOrder) of all vertices in a topological order and returns true.
	 * Otherwise, it returns false. An iterative depth-first search algorithm is
	 * given in under lecture notes for an undirected graph (Week 13 of Lecture
	 * Notes). Here, you need to modify the algorithm to make it work for a directed
	 * graph. Note that the edge iterator citer (inside Graph.java under Lecture
	 * Notes) should be changed from type Iterator<V> to type Iterator<Edge<V,
	 * Integer>>, and that citer.next().getVertex(), instead of citer.next(), gives
	 * the vertex w. If the vertex w is "gray", then the graph has a cycle. So the
	 * method returns false. Whenever a vertex is colored "black", the vertex is
	 * pushed onto the stack topoOrder. If the graph has no cycles (the execution
	 * reaches the end of the method), then the method returns true.
	 */
	protected static <V> boolean visitDFS(DiGraph<V> aGraph, V s, HashMap<V, String> color, HashMap<V, V> pred,
			Stack<V> topoOrder) {
		// TODO
		color.put(s, "gray");
		Stack<V> nodes = new Stack<V>();
		Stack<Iterator<Edge<V, Integer>>> edges = new Stack<Iterator<Edge<V, Integer>>>();
		Iterator<Edge<V, Integer>> siter = aGraph.adjacentTo(s).iterator(); // "siter", "witer", and "citer" are the
																			// iterators for s, w, and c

		nodes.push(s);
		edges.push(siter);

		while (!nodes.isEmpty()) {
			V c = nodes.peek();
			Iterator<Edge<V, Integer>> citer = edges.peek();

			if (citer.hasNext()) {
				V w = citer.next().getVertex();

				if (color.get(w).equals("white")) {
					color.put(w, "gray");
					pred.put(w, c);
					Iterator<Edge<V, Integer>> witer = aGraph.adjacentTo(w).iterator();
					nodes.push(w);
					edges.push(witer);
				} else if (color.get(w).equals("gray")) {
					return false;
				}
			} else {
				color.put(c, "black");
				nodes.pop();
				edges.pop();
				topoOrder.push(c);
			}
		}
		return true;
	}
}
