package edu.ujf.samples.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

public class Echo_v2 {

  public static void main(String args[]) {
    echo();
  }

  /**
   * Buggy echo, can you tell?
   * @throws IOException
   */
  private static void echo() {
    System.out.println("Hello(echo_v2):");
    try {
      for (;;)
        System.out.print((char) System.in.read());
    } catch (IOException ex) {
      System.err.println("Echo2: " + ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }

}
