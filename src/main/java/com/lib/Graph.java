package com.lib;

import java.util.ArrayList;

public class Graph<T> {

  private final ArrayList<Vertice<T>> vertices; // List of vertices
  private final float edges[][]; // Adjacency matrix to represent edges

  public Graph(int verticesAmount) {
    this.vertices = new ArrayList<>();
    this.edges = new float[verticesAmount][verticesAmount];
  }

  public Vertice<T> addVertice(T value) {
    Vertice<T> vertice = new Vertice<>(value);
    this.vertices.add(vertice);
    return vertice;
  }

  public int getVerticeIndex(T value) {
    Vertice<T> v;
    for (int i = 0; i < this.vertices.size(); i++) {
      v = this.vertices.get(i);
      if (v.getValue().equals(value)) {
        return i; // Return the index of the vertice
      }
    }
    return -1; // Return -1 if the vertice is not found
  }

  public void addEdge(T origin, T destination, float weight) {
    Vertice<T> originVertice, destinationVertice;

    // Search for origin vertice
    int originIndex = getVerticeIndex(origin);
    if (originIndex == -1){
      originVertice = addVertice(origin);
      originIndex = this.vertices.indexOf(originVertice);
    }

    // Search for destination vertice 
    int destinationIndex = getVerticeIndex(destination);
    if (destinationIndex == -1){
      destinationVertice = addVertice(destination);
      destinationIndex = this.vertices.indexOf(destinationVertice);
    }

    this.edges[originIndex][destinationIndex] = weight;
  }
}
