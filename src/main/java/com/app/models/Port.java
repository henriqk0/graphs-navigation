package com.app.models;

public class Port {
  private String name;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Port(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
      return  "Nome: " + name;
  }
}
