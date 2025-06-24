package com.app.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.app.controller.RouteManager;

public class Menu {

  public static void show() {
    System.out.println("╔═══════════════════════════════╗                                        ");
    System.out.println("║           BEM VINDO!          ║          «░░▒▒╗                       ");
    System.out.println("╠═══════════════════════════════╣               ▐                        ");
    System.out.println("║      Selecione uma opção:     ║             ░▓▐▓▒░                      ");
    System.out.println("║ ───────────────────────────── ║            ░▒▓▐▓▓▓▒▒░                   ");
    System.out.println("║   1) Criar um mapa            ║           ░▒▓▓▐▓▓▓▓▓▒▒▒░                ");
    System.out.println("║   2) Adicionar um porto       ║          ░▒▒▓▓▐▓▓▓▓▓▓▓▒▒▒░░             ");
    System.out.println("║   3) Adicionar uma rota       ║    ▀█▄▄▄▄▄    ▐       ▄▄▄▄▄▄▄▄▄         ");
    System.out.println("║   4) Visualizar mapa          ║     ▀████████████████████████         ");
    System.out.println("║   5) Rota global mais segura  ║       ▀██████████████████▀▀           ");
    System.out.println("║   6) Melhor caminho A->B      ║      «▓▓▓▀▀██████████▀▀▀               ");
    System.out.println("║   0) Sair                     ║      «╜      ▀▀▀▀                      ");
    System.out.println("╚═══════════════════════════════╝                                        ");
  }


  public static void run() {
    Scanner scanner = new Scanner(System.in);
    Reader reader = new Reader();

    Menu.show();

    int opt = reader.scannerIntRead("", scanner);
    RouteManager routeManager = new RouteManager();

    while (opt != 0) {
      if (opt > 0 && opt < 7) { // se acharem melhor, podem trocar por um switch-case

        Menu.clearConsole();

        if (opt == 1) {
          // If true, reads a file to generate the graph
          // otherwise, generates a random graph 
          final boolean RANDOM_GRAPH = true;

          if (RANDOM_GRAPH){
            int numPorts = reader.scannerIntRead("Quantos portos o mapa vai possuir?", scanner);
            routeManager.populatePorts(numPorts);
          }
          else {
            String filePath = "ports.txt";
            routeManager.readFile(filePath);
          }
      
        }
        else if ( opt == 2 ) { 
          String portName = reader.scannerStrRead("Digite o nome do porto a ser adicionado:", scanner);

          routeManager.addPort(routeManager.generatePort(portName));
        }

        else if (opt == 3) { 
          String originName = reader.scannerStrRead("Digite o nome do porto de origem:", scanner);

          // Verifies if the origin port exists
          if (routeManager.getPort(originName) != null){
            String destinationName = reader.scannerStrRead("Digite o nome do porto de destino:", scanner);

            // Verifies if the destination port exists
            if ((routeManager.getPort(destinationName) != null)) {
              // Verifies if the origin and destination ports are different
              if (!originName.equals(destinationName)) {
                // Verifies if the route already exists
                // If it does, warns the user about overwriting it
                if (routeManager.hasRoute(routeManager.getPort(originName), routeManager.getPort(destinationName))){
                  System.out.println("Rota ja existente. O nivel de risco sera atualizado.");
                }
                float riskRate = reader.scannerFloatRead("Digite o nivel de risco da rota:", scanner);
                routeManager.addRoute(routeManager.getPort(originName), routeManager.getPort(destinationName), riskRate);
              }
              else {System.out.println("Porto de origem e destino nao podem ser iguais.");}
            }
            else {System.out.println("Porto inexistente.");}
          }
          else {System.out.println("Porto inexistente.");}
        }

        else if (opt == 4) { 
          routeManager.getRoutes().displayPaths();
        }

        else if (opt == 5) { }

        else  {
          routeManager.getAllPortNames();
          String portA = reader.scannerStrRead("Digite o nome da origem:", scanner);
          String portB = reader.scannerStrRead("Digite o nome do destino:", scanner);
          
          while (true) {
            try {
              if (routeManager.getPort(portA) == null || routeManager.getPort(portB) == null) {
                throw new Exception(); 
              }
              routeManager.bestPath(routeManager.getPort(portA), routeManager.getPort(portB));
              
              break;
            } catch (Exception e) {
              if (routeManager.getPort(portA) == null) {
                portA = reader.scannerStrRead("Porto de origem inexistente. Digite um nome valido:", scanner); }
              if (routeManager.getPort(portB) == null) {
                portB = reader.scannerStrRead("Porto de destino inexistente. Digite um nome valido::", scanner);
              }
            }
          }
        }
      }
      else { System.out.println("Opcao invalida!"); }

      Menu.show();
      opt = reader.scannerIntRead("", scanner);
    }

    scanner.close();
  }

  public static void clearConsole() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      }
      else { System.out.print("\033\143"); }
    } catch (IOException | InterruptedException ex) {}
  }
}
