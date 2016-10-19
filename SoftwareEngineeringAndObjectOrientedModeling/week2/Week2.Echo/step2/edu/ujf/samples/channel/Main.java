
package edu.ujf.samples.channel;
import java.io.IOException;

public class Main {

  public static void main(String args[]) {
    Consumer c = new Consumer(System.out);
    Channel ch = new Channel(c);
    Producer p = new Producer(System.in, ch);
    try {
      p.produce();
    } catch (IOException ex) {
      System.err.println("Exception: " + ex.getMessage());
      ex.printStackTrace(System.err);
      System.exit(-1);
    }
    System.out.println("Bye.");
  }
}
