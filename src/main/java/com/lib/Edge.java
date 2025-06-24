package com.lib;

public class Edge<T> {
    public Vertex<T> v1;//Vértice de origem
    public Vertex<T> v2;//Vértice de destino
    public float weight;//Peso da aresta

    public Edge(Vertex<T> v1, Vertex<T> v2, float weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
}
