package com.app.view;

import java.io.IOException;

public class Menu {



    public static void show() {
           //  ╢╟
        System.out.println("╔═════════════════════════════╗                                        ");
        System.out.println("║         BEM VINDO!          ║          «░░▒▒╗                       ");
        System.out.println("╠═════════════════════════════╣               ▐                        ");
        System.out.println("║     Selecione uma opção:    ║             ░▓▐▓▒░                      ");
        System.out.println("║ ─────────────────────────── ║            ░▒▓▐▓▓▓▒▒░                   ");
        System.out.println("║    1) Criar um mapa         ║           ░▒▓▓▐▓▓▓▓▓▒▒▒░                       ");
        System.out.println("║    2) Adicionar um destino  ║          ░▒▒▓▓▐▓▓▓▓▓▓▓▒▒▒░░                       ");
        System.out.println("║    3) Visualizar mapa       ║    ▀█▄▄▄▄▄    ▐       ▄▄▄▄▄▄▄▄▄           ");
        System.out.println("║    4) Volta ao mundo        ║     ▀████████████████████████         "); // arvore geradora minima
        System.out.println("║    5) Melhor caminho A->B   ║       ▀██████████████████▀▀           ");
        System.out.println("║    0) Sair                  ║      «▓▓▓▀▀██████████▀▀▀                ");
        System.out.println("╚═════════════════════════════╝      «╜      ▀▀▀▀                      ");

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
