package com.app.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.app.models.Port;
import com.lib.Graph;
import com.lib.Vertex;

public class RouteManager {
  final Random rand = new Random();
  private Graph<Port> routes = new Graph<>();

  public Graph<Port> getRoutes() {
    return routes;
  }

  public void setRoutes(Graph<Port> routes) {
    this.routes = routes;
  }
    
  private static final String[] PREFIXES = {
    "New", "Porto de", "Port", "Cabo de", "Terminal de", "Puerto"
  };

  private static final String[] SUFFIXES = {
    "Royale", "Harbor", "Xangai", "Vitoria", "Lisboa", "Aveiro", "Porto", "New York",
    "Escocia", "Seturbal", "Flandres", "Salvador", "Bahia", "Cingapura", "Indonesia",
    "Taiwan", "Antuerpia", "Roterda", "Hamburgo", "Petersburgo", "Kiev", "Durban",
    "Mombasa", "Said", "Luanda", "Colon", "Long Beach", "Rosales"
  };

  public void readFile(String filePath){
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      // Reads the number of ports
      int numberPorts = Integer.parseInt(reader.readLine());

      for (int i = 0; i < numberPorts; i++) {
          String portName = reader.readLine();
          this.routes.addVertex(new Port(portName));
      }

      // Reads the number of edges
      int numberEdges = Integer.parseInt(reader.readLine());

      for (int i = 0; i < numberEdges; i++) {
          String origin = reader.readLine();
          String destination = reader.readLine();
          String weightStr = reader.readLine();
          
          float weight = Float.parseFloat(weightStr);

          this.routes.addEdge(this.routes.getVertices().get(this.routes.getVertexIndex(new Port (origin))).getValue(), 
                              this.routes.getVertices().get(this.routes.getVertexIndex(new Port (destination))).getValue(), 
                              weight);
      }

    } catch (IOException e) {
        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
    }
  }

  public static String generateRandomPortNames() {
    Random rand = new Random();
    String prefix = PREFIXES[rand.nextInt(PREFIXES.length)];
    String suffix = SUFFIXES[rand.nextInt(SUFFIXES.length)];
    return prefix + " " + suffix;
  }

  public Port generatePort(){
    String name = generateRandomPortNames();
    return new Port(name);
  }

  public Port generatePort(String portName){
    String nome = portName;
    return new Port(nome);
  }
  
  public Port addPort(Port p) {
    this.routes.addVertex(p);
    System.out.println("Porto " + p + " adicionado com sucesso!");
    return p;
  }

  public void getAllPortNames() {
    ArrayList<Vertex<Port>> ports = this.routes.getVertices();
    System.out.println("Portos disponíveis:\n");
    for (int i = 0; i < ports.size(); i++) {
      System.out.println(ports.get(i).getValue().getName());
    }
    System.out.println("");
  }

  public Port getPort(String portName) {
    ArrayList<Vertex<Port>> ports = this.routes.getVertices();
    for (int i = 0; i < ports.size(); i++) {
      if (ports.get(i).getValue().getName().equals(portName)) {
        return ports.get(i).getValue();
      }
    }
    return null;
  }

  public void addRoute(Port origin, Port destination, Float weight) {
    this.routes.addEdge(origin, destination, weight);
    System.out.println("Rota de " + origin.getName() + " para " + destination.getName() + " adicionada com sucesso!");
  }

  public boolean hasRoute(Port origin, Port destination) {
    return this.routes.hasEdge(origin, destination);
  }
  
  public void bestPath(Port a, Port b) {
    float bestPathSomatory = this.routes.dijkstra(a, b);
    System.out.println(String.format("Nível total de risco: %.2f", bestPathSomatory));
  }

  public void populatePorts(int numPorts) {
    setRoutes(new Graph<Port>());
    for (int i = 0; i < numPorts; i++) {
      Port port = this.generatePort();
      System.out.println("Nome do porto: " + port.getName());
      this.routes.addVertex(port);
    }
    populateRoutes(numPorts);
  }

  private void populateRoutes(int numPorts){
    Random r = new Random();
    float randomRiskRate;

    for (int i = 0; i < numPorts - 1 ; i++) {

      for (int j = i+1; j < numPorts; j++) { 
        randomRiskRate = 0 + r.nextFloat() * (20 - 0);
        randomRiskRate = (float)(((int)(Math.pow(10,2)*randomRiskRate))/Math.pow(10,2)); // to only 2 decimal places

        this.routes.addEdge(this.routes.getVertices().get(i).getValue(),
                            this.routes.getVertices().get(j).getValue(),
                            randomRiskRate);
      }
    }
  }
}

