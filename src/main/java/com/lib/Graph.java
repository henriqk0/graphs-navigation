package com.lib;

import java.util.ArrayList;
import java.util.Collections;

// TODO: criar uma maneira de printar o grafo a partir dos predecessores, tanto para o dijkstra quanto para a árvore geradora mínima
public class Graph<T> {

  private final ArrayList<Vertex<T>> vertices; // List of vertices
  private ArrayList<ArrayList<Float>> edges; // Adjacency matrix to represent edges
  private boolean directed; // Default is false

  public Graph() {
    this.vertices = new ArrayList<>();
    this.edges = new ArrayList<>();
    this.directed = false;
  }

  public ArrayList<Vertex<T>> getVertices() {
    return vertices;
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
    for (ArrayList<Float> row : this.edges) {
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
    if (originIndex == -1) {
      originVertex = addVertex(origin);
      originIndex = this.vertices.indexOf(originVertex);
    }

    // Search for destination vertex
    int destinationIndex = getVertexIndex(destination);
    // If it doesn't exist, create it
    if (destinationIndex == -1) {
      destinationVertex = addVertex(destination);
      destinationIndex = this.vertices.indexOf(destinationVertex);
    }

    this.edges.get(originIndex).set(destinationIndex, weight);
    if (this.directed == false) {
      this.edges.get(destinationIndex).set(originIndex, weight); } // Not directed, then add the path back
  }

  // Breadth First Search (Busca em Largura)
  public void bfs(){
    boolean visited[] = new boolean[this.vertices.size()];
    int current = 0;
    ArrayList<Integer> queue = new ArrayList<>();

    queue.add(current);
    while (!queue.isEmpty()){
      current = queue.get(0);
      queue.remove(0);
      visited[current] = true;
      System.out.println(this.vertices.get(current).getValue());
      for (int dest=0; dest<this.vertices.size(); dest++){
        // If node is adjacent
        if (edges.get(current).get(dest) > 0)
          // If it hasn't been visited e it's not in the queue
          // add it to the queue
          if (!visited[dest] && !queue.contains(dest))
            queue.add(dest);   
      }
    }
  }

  public void displayMatrix() {
    for (ArrayList<Float> row : this.edges) {
      for (float value : row) {
        System.out.print(value + "    ");
      }
      System.out.println();
    }
  }

  public void displayPaths() {
    int indexPrimNode = 0;
    for (int i = 0; i < this.edges.size(); i++) {
      // Adds the respective node of the row to the beggining of the string
      String paths = vertices.get(indexPrimNode).getValue() + " {\n  ";
      for (int j = 0; j < this.edges.size(); j++) {
        float weight = edges.get(i).get(j);
        if (weight != 0) {
          paths += vertices.get(j).getValue() + "(" + weight + ")" + ", ";
        }
      }
      if (paths.endsWith(", ")){
        paths = paths.substring(0, paths.length() - 2);
      }
      System.out.println(paths + "\n" + "}");
      indexPrimNode++;
    } 
  }

  private float[] toFullFloatArray(int lengthArray, int value) {
    float[] toBeIterable = new float[lengthArray];
    for (int i = 0; i < lengthArray; i++) {
      toBeIterable[i] = value;
    }
    return toBeIterable;
  }

  private int[] toFullIntArray(int lengthArray, int value) {
    int[] toBeIterable = new int[lengthArray];
    for (int i = 0; i < lengthArray; i++) {
      toBeIterable[i] = value;
    }
    return toBeIterable;
  }

  public float dijkstra(T origin, T destination) { // assuming that the destination is reachable from the origin vertex
    try {
      int originIndex = getVertexIndex(origin);
      int destinationIndex = getVertexIndex(destination);

      if (originIndex == -1 || destinationIndex == -1) { 
        throw new Exception();
      }

      float[] distances = toFullFloatArray(this.edges.size(), 99999);
      int[] predecessors = toFullIntArray(this.edges.size(), 0);
      boolean[] visited = new boolean[this.edges.size()];

      distances[originIndex] = 0;
      predecessors[originIndex] = -1;
     
      int i = originIndex;
      float minDistance = 99999;

      // TODO: Varios testes, para ver se não é necessário, de fato, de usar fila de prioridade, por exemplo
      visited[originIndex] = true;
      while (visited[destinationIndex] == false) { 

        for (int j = 0; j < this.edges.size(); j++) { // to pick all weight values from `i` vertex
          float pathWeight = this.edges.get(i).get(j); 

          if (pathWeight != 0) { // existence of an edge to `j`
            if (visited[j] == false && pathWeight + distances[i] < distances[j] ) { // new distance < previous stored distance
              distances[j] = pathWeight + distances[i];  // then, update the minimal distance to vertex `j`
              predecessors[j] = i;  // update the last predecessor from `j`
            }
          }
        }

        visited[i] = true; 
        for (int j = 0; j < this.edges.size(); j++) { // pick next vertex with minimal distance before destination
          if (visited[j] == false && distances[j] < minDistance) {
            minDistance = distances[j];
            i = j;
          }
        }
      }

      int iPredecessors = destinationIndex;
      ArrayList<Integer> destinationsOrdered = new ArrayList<>();
      while (iPredecessors != -1) {
        destinationsOrdered.add(iPredecessors);
        iPredecessors = predecessors[iPredecessors];
      }
      Collections.reverse(destinationsOrdered);

      System.out.println("Melhor rota de " + origin + " até " + destination + ":");
      for (int idx = 0; i < destinationsOrdered.size() - 1; i++) {
        System.out.println(this.vertices.get(destinationsOrdered.get(idx)) + " -(Perigo: "
        + this.edges.get(destinationsOrdered.get(idx)).get(destinationsOrdered.get(idx+1)) +")->" 
        + this.vertices.get(destinationsOrdered.get(idx+1)));
      }

      return distances[destinationIndex];

    } catch (Exception e) {
      System.out.println("ERROR: Unable to use this algorithm to inexistent values");
    }
    return 0;
  }

  // Prim  -> abuso de:  https://www.geeksforgeeks.org/dsa/prims-minimum-spanning-tree-mst-greedy-algo-5/
  public float minnimalSpanningTree(){
    if (this.edges.isEmpty()) return 0;

    else {
      float[] weights = toFullFloatArray(this.edges.size(), 99999);
      float[] predecessors = toFullFloatArray(this.edges.size(), 0);
      boolean[] itsInTree = new boolean[this.edges.size()];

      itsInTree[0] = true;
      predecessors[0] = -1;

      for (int aux = 0; aux < itsInTree.length - 1; aux++) {

        int indx; float minWeight = 99999;
        int j = 0;
        for (indx = 0; indx < itsInTree.length; indx++) {
          if (itsInTree[indx] == false && this.edges.get(aux).get(indx) < minWeight) {
            minWeight = weights[indx];
            j = indx;
          }
        }

        itsInTree[j] = true;

        for (int w = 0; w < itsInTree.length; w++) {
          if (this.edges.get(j).get(w) > 0 && itsInTree[w] == false &&
            this.edges.get(j).get(w) < weights[w] ) {
            predecessors[w] = j;
            weights[w] = this.edges.get(j).get(w);
          }
        }
      }

      float total = 0;
      for (float wgt : weights) {
        total += wgt;
      }
      return total;
    }
  }
}