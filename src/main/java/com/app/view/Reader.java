package com.app.view;
import java.util.Scanner;


public class Reader {
  public String scannerStrRead(String msg, Scanner scn) {
    System.out.println(msg);
    String line = scn.nextLine();
    return line;
  }

  public int scannerIntRead(String msg, Scanner scn) {
    String line = scannerStrRead(msg, scn);

    while (true) {
      try {
        int num = Integer.parseInt(line);
        return num;
      } catch (Exception e) {
        System.out.println("Quantidade invalida. Digite novamente");
        line = scn.nextLine();
      }
    }
  }

  public float scannerFloatRead(String msg, Scanner scn) {
    String line = scannerStrRead(msg, scn);

    while (true) {
      try {
        float num = Float.parseFloat(line);
        return num;
      } catch (Exception e) {
        System.out.println("Quantidade invalida. Digite novamente");
        line = scn.nextLine();
      }
    }
  }
}
