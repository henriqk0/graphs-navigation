package com.lib;

public class Vertex<T> {

  private T value;

  public T getValue(){
    return this.value;
  }

  public void setValue(T value){
    this.value = value;
  }

  public Vertex(T value) {
    this.value = value;
  }
}
