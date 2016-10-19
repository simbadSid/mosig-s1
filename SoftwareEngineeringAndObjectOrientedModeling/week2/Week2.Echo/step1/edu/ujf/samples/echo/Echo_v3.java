package edu.ujf.samples.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

public class Echo_v3 {

  public static void main(String args[]) {
    echo();
  }

  /**
   * Buggy echo, can you tell?
   * @throws IOException
   */
  private static void echo() {
    System.out.println("Hello(echo_v3):");
    InputStreamReader in = new InputStreamReader(System.in);
    String encoding = in.getEncoding();
    System.out.println("Using encoding: " + encoding);
    try {
      for (;;)
        System.out.print((char) in.read());
    } catch (IOException ex) {
      System.err.println("Echo3: " + ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }
}
