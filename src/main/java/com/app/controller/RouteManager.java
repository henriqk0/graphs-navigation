package com.app.controller;

import java.util.Random;

import com.app.models.Port;
import com.lib.Graph;

public class RouteManager {
  final Random rand = new Random();
  private Graph<Port> routes;

  public Graph<Port> getRoutes() {
    return routes;
  }

  public void setRoutes(Graph<Port> routes) {
    this.routes = routes;
  }
    
  private static final String[] PREFIXES = {
    "San", "São", "New", "Porto de", "Port", "Cabo de"
  };

  private static final String[] SUFFIXES = {
    "Royale", "Harbor", "Xangai", "Vitoria", "Lisboa", "Aveiro", "Porto", "New York",
    "Escocia", "Seturbal", "Flandres", "Salvador", "Bahia", "Cingapura", "Indonesia",
    "Taiwan", "Antuerpia", "Roterda", "Hamburgo", "Petersburgo", "Kiev", "Durban",
    "Mombasa", "Said", "Luanda", "Colon", "Long Beach"
  };

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

  public Port generatePort(String nomeProcesso){

    String nome = nomeProcesso;

    return new Port(nome);
  }

  public void populatePorts(int numPorts) {
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
    int randomPortOrigin, randomPortDestiny;

    for (int i = 0; i < numPorts*4; i++) {
      randomRiskRate = 0 + r.nextFloat() * (20 - 0);
      randomPortOrigin = r.nextInt(numPorts);
      randomPortDestiny = r.nextInt(numPorts);

      this.routes.addEdge(this.routes.getVertices().get(randomPortOrigin).getValue(),
                          this.routes.getVertices().get(randomPortDestiny).getValue(),
                          randomRiskRate);
    }
  }
}
