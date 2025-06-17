package com.lib;

import java.util.ArrayList;

public class Graph<T> {

  private final ArrayList<Vertex<T>> vertices; // List of vertices
  private ArrayList<ArrayList<Float>> edges; // Adjacency matrix to represent edges
  private boolean directed; // Default is false

  public Graph() {
    this.vertices = new ArrayList<>();
    this.edges = new ArrayList<>();
    this.directed = false;
  }

  public Graph(boolean directed) {
    this.vertices = new ArrayList<>();
    this.edges = new ArrayList<>();
    this.directed = directed;
  }

  public Vertex<T> addVertex(T value) {
    Vertex<T> vertex = new Vertex<>(value);
    this.vertices.add(vertex);

    // Initialize edges for the new vertex
    for (ArrayList<Float> row : this.edges){
      row.add((float) 0);
    }

    ArrayList<Float> newVertexEdges = new ArrayList<>();
    for (int i = 0; i < this.vertices.size(); i++) { 
      newVertexEdges.add((float) 0);
    }

    this.edges.add(newVertexEdges);

    return vertex;
  }

  public int getVertexIndex(T value) {
    Vertex<T> v;
    for (int i = 0; i < this.vertices.size(); i++) {
      v = this.vertices.get(i);
      if (v.getValue().equals(value)) {
        return i; // Return the index of the vertex
      }
    }
    return -1; // Return -1 if the vertex is not found
  }

  public void addEdge(T origin, T destination, Float weight) {
    Vertex<T> originVertex, destinationVertex;

    // Search for origin vertex
    int originIndex = getVertexIndex(origin);
    // If it doesn't exist, create it
    if (originIndex == -1){
      originVertex = addVertex(origin);
      originIndex = this.vertices.indexOf(originVertex);
    }

    // Search for destination vertex
    int destinationIndex = getVertexIndex(destination);
    // If it doesn't exist, create it
    if (destinationIndex == -1){
      destinationVertex = addVertex(destination);
      destinationIndex = this.vertices.indexOf(destinationVertex);
    }

    this.edges.get(originIndex).set(destinationIndex, weight);
  }

  public void displayMatrix(){
    for (ArrayList<Float> row : this.edges){
      for (float value : row){
        System.out.print(value + " ");
      }
      System.out.println();
    }
  }

  public void dijkstra(Vertex<T> origin, Vertex<T> destination){
    int[] distances = new int[this.vertices.size()];
    boolean[] visited = new boolean[this.vertices.size()];
    int[] parent = new int[this.vertices.size()];

    this.edges.get(this.getVertexIndex(origin.getValue()));

  }
}
