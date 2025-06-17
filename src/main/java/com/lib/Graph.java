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
    this.vertices.add(vertice);
    return vertice;
  }

  public int getVertexIndex(T value) {
    Vertex<T> v;
    for (int i = 0; i < this.vertices.size(); i++) {
      v = this.vertices.get(i);
      if (v.getValue().equals(value)) {
        return i; // Return the index of the vertice
      }
    }
    return -1; // Return -1 if the vertice is not found
  }

  public void addEdge(T origin, T destination, float weight) {
    Vertex<T> originVertex, destinationVertex;

    // Search for origin vertice
    int originIndex = getVertexIndex(origin);
    if (originIndex == -1){
      originVertex = addVertex(origin);
      originIndex = this.vertices.indexOf(originVertex);
    }

    // Search for destination vertice 
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
}
