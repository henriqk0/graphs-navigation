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
      return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Port other = (Port) obj;
    return name != null && name.equals(other.name);
  }

  @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

}
