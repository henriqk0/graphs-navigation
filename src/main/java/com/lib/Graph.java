package com.lib;

import java.util.ArrayList;

public class Graph<T> {

  private final ArrayList<Vertex<T>> vertices; // List of vertices
  private final float edges[][]; // Adjacency matrix to represent edges

  public Graph(int verticesAmount) {
    this.vertices = new ArrayList<>();
    this.edges = new float[verticesAmount][verticesAmount];
  }

  public Vertex<T> addVertex(T value) {
    Vertex<T> vertex = new Vertex<>(value);
    this.vertices.add(vertex);
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

  public void addEdge(T origin, T destination, float weight) {
    Vertex<T> originVertex, destinationVertex;

    // Search for origin vertex
    int originIndex = getVertexIndex(origin);
    if (originIndex == -1){
      originVertex = addVertex(origin);
      originIndex = this.vertices.indexOf(originVertex);
    }

    // Search for destination vertex
    int destinationIndex = getVertexIndex(destination);
    if (destinationIndex == -1){
      destinationVertex = addVertex(destination);
      destinationIndex = this.vertices.indexOf(destinationVertex);
    }

    this.edges[originIndex][destinationIndex] = weight;
  }

  public void displayMatrix(){
    for (float[] row : this.edges){
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
