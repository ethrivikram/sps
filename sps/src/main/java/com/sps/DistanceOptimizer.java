package com.sps;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.sps.DistanceOptimizer.Edge;

public class DistanceOptimizer implements IDistanceOptimizer {

  // Small epsilon value to comparing double values.
  private static final double EPS = 1e-6;

  // An edge class to represent a directed edge
  // between two nodes with a certain cost.
  public static class Edge {
    Double distance;
    String from, to;
    boolean bidrection;
    public Edge(String from, String to, Double distance, boolean bidrection) {
      this.from = from;
      this.to = to;
      this.distance = distance;
      this.bidrection=bidrection;
    }
  }

  // Node class to track the nodes to visit while running Dijkstra's
  public static class Node {
    String id;
    double value;
    public Node(String start, double value) {
      this.id = start; 
      this.value = value;
    }
  }

  //private int n;
  private Map<String,Double> dist;
  private Map<String, String> prev;
  private Map<String, List<Edge>> graph;

  private Comparator<Node> comparator = new Comparator<Node>() {
    @Override
    public int compare(Node node1, Node node2) {
      if (Math.abs(node1.value-node2.value) < EPS) return 0;
      return (node1.value - node2.value) > 0 ? +1 : -1;
    }
  };

  /**
   * Initialize the solver by providing the graph size and a starting node. 
   * Use the {@link #addEdge} method to actually add edges to the graph.
   * @param n     - The number of nodes in the graph.
   */
  public DistanceOptimizer() {
    //this.n = n;
    graph = new HashMap<String, List<Edge>>();
    dist = new HashMap<String,Double>();
   // createEmptyGraph();
  }

  public DistanceOptimizer(int n, Comparator<Node> comparator) {
    //this(n);
    if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null");
    this.comparator = comparator;
  }

  /**
   * Adds a directed edge to the graph.
   *
   * @param from - The index of the node the directed edge starts at.
   * @param to   - The index of the node the directed edge end at.
   * @param cost - The cost of the edge.
   */
  public void addConnection(String from, String to, Double distance, boolean bidirection) {
	
	  if(graph.get(from)==null){
		  graph.put(from,new ArrayList<Edge>());
	  }
	  
    graph.get(from).add(new Edge(from, to, distance,bidirection));
    
    if(bidirection){
    	if(graph.get(to)==null){
  		  graph.put(to,new ArrayList<Edge>());
  	  }
    	 graph.get(to).add(new Edge(to, from, distance,bidirection));
    }
    
    
    if(dist.get(from)==null){
    	dist.put(from, Double.POSITIVE_INFINITY);
    }
    
    if(dist.get(to)==null){
    	dist.put(to, Double.POSITIVE_INFINITY);
    }
  }

  // Use {@link #addEdge} method to add edges to the graph and use this method
  // to retrieve the constructed graph.
  public Map<String, List<Edge>> getGraph() {
    return graph;
  }

  /**
   * Reconstructs the shortest path (of nodes) from 'start' to 'end' inclusive.
   *
   * @return An array of nodes indexes of the shortest path from 'start' to 'end'.
   * If 'start' and 'end' are not connected then an empty array is returned.
   */
  public List<String> reconstructPath(String start, String end) {
    if (end ==null && graph.get(end)==null ) throw new IllegalArgumentException("Invalid node End");
    if (start==null && graph.get("start")==null) throw new IllegalArgumentException("Invalid node Start");
    double dist = computeShortestDistance(start, end);
    List<String> path = new ArrayList<>();

    if (dist == -9999d) return path;
    for(String at = end; at != null; at = prev.get(at)){
      path.add(at);
    }
    Collections.reverse(path);
    return path;
  }

  // Run Dijkstra's algorithm on a directed graph to find the shortest path
  // from a starting node to an ending node. If there is no path between the
  // starting node and the destination node the returned value is set to be 
  // Double.POSITIVE_INFINITY.
  public Double computeShortestDistance(String start, String end) {
    // Maintain an array of the minimum distance to each node
    //dist = new HashMap<String,Double>();
    //Arrays.fill(dist, Double.POSITIVE_INFINITY);
    
    dist.put(start, 0d);

    // Keep a priority queue of the next most promising node to visit.
    PriorityQueue<Node> pq = new PriorityQueue<>(2*graph.size(), comparator);
    pq.offer(new Node(start, 0));

    // Array used to track which nodes have already been visited.
    Map visited = new HashMap<String, Boolean>();
     prev = new HashMap<String, String>();

    while(!pq.isEmpty()) {
      Node node = pq.poll();
      visited.put(node.id,true);

      // We already found a better path before we got to 
      // processing this node so we can ignore it.
      if (dist.get(node.id) < node.value) continue;

      List<Edge> edges = graph.get(node.id);
      for(int i = 0; edges!=null && i < edges.size(); i++) {
        Edge edge = edges.get(i);

        // You cannot get a shorter path by revisiting
        // a node you have already visited before.
        if (visited.get(edge.to)!=null && (Boolean)visited.get(edge.to)) continue;

        // Relax edge by updating minimum cost if applicable.
        double newDist = dist.get(edge.from) + edge.distance;

        if (dist.get(edge.to)!=null && newDist < dist.get(edge.to)) {
          prev.put((String)edge.to,(String)edge.from);

          dist.put(edge.to,newDist) ;
          pq.offer(new Node(edge.to, dist.get(edge.to)));
        }
      }
      
      for (Map.Entry<String,String> entry : prev.entrySet())  
          System.out.println("Key = " + entry.getKey() + 
                           ", Value = " + entry.getValue()); 
      
      
      // Once we've visited all the nodes spanning from the end 
      // node we know we can return the minimum distance value to 
      // the end node because it cannot get any better after this point.
      if (node.id == end) return dist.get(end);
    }
    // End node is unreachable

    return -9999d;
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  /*private void createEmptyGraph() {
    graph = new HashMap<String, List<Edge>>(n);
    for (int i = 0; i < n; i++) graph.put(key, new ArrayList<Edge>());
  }*/

  public static void main(String [] args){
	  
		 IDistanceOptimizer dop = new DistanceOptimizer();
	  
	  dop.addConnection("a","b",5d,true);
	  dop.addConnection("a","c",4d,true);
	  dop.addConnection("a","f",8d,true);
	  dop.addConnection("a","k",4d,false);
	  dop.addConnection("b","a",5d,true);
	  dop.addConnection("b","c",2d,true);
	  dop.addConnection("b","d",3d,true);
	  dop.addConnection("c","a",4d,true);
	  dop.addConnection("c","b",2d,true);
	  dop.addConnection("c","d",3d,true);
	  dop.addConnection("c","e",8d,true);
	  dop.addConnection("d","b",3d,true);
	  dop.addConnection("d","c",2d,true);
	  dop.addConnection("d","f",2d,true);
	  dop.addConnection("e","c",8d,true);
	  dop.addConnection("e","f",1d,true);
	  dop.addConnection("e","h",5d,true);
	  dop.addConnection("f","a",8d,true);
	  dop.addConnection("f","d",2d,true);
	  dop.addConnection("f","e",1d,true);
	  dop.addConnection("f","g",6d,true);
	  dop.addConnection("g","f",6d,true);
	  dop.addConnection("g","k",5d,true);
	  dop.addConnection("h","i",8d,true);
	  dop.addConnection("i","f",1d,true);
	  dop.addConnection("i","j",1d,true);
	  dop.addConnection("j","g",3d,true);
	  dop.addConnection("j","k",4d,true);
	  dop.addConnection("k","f",6d,true);
	//dop.addEdge(0, 5, 5);
	  
	 // double distance = dop.computeShortestDistance("i", "a");
	  

	
	  
  }
  
} 