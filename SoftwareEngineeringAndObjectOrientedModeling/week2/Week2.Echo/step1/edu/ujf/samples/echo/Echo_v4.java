package edu.ujf.samples.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

public class Echo_v4 {

  public static void main(String args[]) {
    echo();
  }

  /**
   * Nota Bene:
   *             Slight change of specification...
   *             We want to echo each line with a prefix "> ".
   * Buggy or not, can you tell?
   */
  private static void echo() {
    System.out.println("Hello(echo_v4):");
    InputStreamReader in = new InputStreamReader(System.in);
    String encoding = in.getEncoding();
    System.out.println("Using encoding: " + encoding);
    char chars[] = new char[8];
    try {
      for (int c=0;c<10;c++) {
        System.out.print("> ");
        int nchars = in.read(chars);
        System.out.print(nchars + ":");
        for (int i = 0; i < nchars; i++)
          System.out.print(chars[i]);
      }
    } catch (IOException ex) {
      System.err.println("Echo3: " + ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }
}
