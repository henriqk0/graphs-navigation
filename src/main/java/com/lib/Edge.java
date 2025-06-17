package com.lib;

public class Edge<T> {
    public Vertice<T> v1;//Vértice de origem
    public Vertice<T> v2;//Vértice de destino
    public float weight;//Peso da aresta

    public Edge(Vertice<T> v1, Vertice<T> v2, float weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
}
