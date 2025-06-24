package com.app.view;

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
    System.out.println("║   1) Criar um mapa            ║           ░▒▓▓▐▓▓▓▓▓▒▒▒░                       ");
    System.out.println("║   2) Adicionar um destino     ║          ░▒▒▓▓▐▓▓▓▓▓▓▓▒▒▒░░                       ");
    System.out.println("║   3) Visualizar mapa          ║    ▀█▄▄▄▄▄    ▐       ▄▄▄▄▄▄▄▄▄           ");
    System.out.println("║   4) Rota global mais segura  ║     ▀████████████████████████         "); // arvore geradora minima
    System.out.println("║   5) Melhor caminho A->B      ║       ▀██████████████████▀▀           ");
    System.out.println("║   0) Sair                     ║      «▓▓▓▀▀██████████▀▀▀                ");
    System.out.println("╚═══════════════════════════════╝      «╜      ▀▀▀▀                      ");
  }


  public static void run() {
    Scanner scanner = new Scanner(System.in);
    Reader reader = new Reader();

    Menu.show();

    int opt = reader.scannerIntRead("", scanner);

    while (opt != 0) {
      if (opt > 0 && opt < 6) { // se acharem melhor, podem trocar por um switch-case

        Menu.clearConsole();

        if (opt == 1) { 
          RouteManager routeManager = new RouteManager();

          int numPorts = reader.scannerIntRead("Quantos portos o mapa vai possuir?", scanner);

          routeManager.populatePorts(numPorts);
        }
        else if ( opt == 2 ) { 
          RouteManager routeManager = new RouteManager();

          String portName = reader.scannerStrRead("Digite o nome do destino a ser adicionado:", scanner);

          routeManager.addPort(routeManager.generatePort(portName));
        }

        else if (opt == 3) { 
          RouteManager routeManager = new RouteManager();

          routeManager.getRoutes().displayPaths();
        }
        else if (opt == 4) { }
        else  { }
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
