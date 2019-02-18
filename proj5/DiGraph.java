package edu.iastate.cs228.proj5;
// This class is complete.

/**
 * @author Lillian Krohn
 */

import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * Directed graph with edges of Edge type.
 * 
 */
public class DiGraph<V>
{
  // symbol table: 
  //  key = string vertex,
  //  value = set of neighboring vertices
  private HashMap<V, HashSet<Edge<V, Integer>>> map;

  // number of edges
  private int E;

  // create an empty graph
  public DiGraph()
  {
	map = new HashMap<V, HashSet<Edge<V, Integer>>>();
  }

  // add t to f's set of neighbors.
  public void addEdge(V f, V t, Integer c)
  {
	if (!hasEdge(f, t))
	  E++;
	if (!hasVertex(f))
	  addVertex(f);
	map.get(f).add(new Edge<V, Integer>(t, c));
	if (!hasVertex(t))
	  addVertex(t);
  }

  // add a new vertex f with no neighbors
  // (if vertex does not yet exist)
  public void addVertex(V f)
  {
	if (!hasVertex(f))
	  map.put(f, new HashSet<Edge<V, Integer>>());
  }

  // return iterator over all vertices in graph
  public Iterable<V> vertices()
  {
	return map.keySet();
  }

  // return an iterator over the neighbors of vertex f
  public Iterable<Edge<V, Integer>> adjacentTo(V f)
  {
	// return empty set if vertex isn't in graph
	if (!hasVertex(f))
	  return new HashSet<Edge<V, Integer>>();
	else
	  return map.get(f);
  }

  // is f a vertex in the graph?
  public boolean hasVertex(V f)
  {
	return map.containsKey(f);
  }

  // is f-t an edge in the graph?
  public boolean hasEdge(V f, V t)
  {
	if (!hasVertex(f))
	  return false;
	for (Edge<V, Integer> e : map.get(f))
	{
	  if (t.equals(e.getVertex()))
		return true;
	}
	return false;
  }

  // not very efficient, intended for debugging only
  public String toString()
  {
	StringBuilder s = new StringBuilder("");
	for (V f : map.keySet())
	{
	  s.append(f.toString() + ": ");
	  for (Edge<V, Integer> e : map.get(f))
	  {
		s.append("[" + e.getVertex().toString() + ", " + e.getCost().toString() + "] ");
	  }
	  s.append("\n");
	}
	return s.toString();
  }

}
