package com.lib;

public class Vertice<T> {

  private T value;

  public T getValue(){
    return this.value;
  }

  public void setValue(T value){
    this.value = value;
  }

  public Vertice(T value) {
    this.value = value;
  }
}
