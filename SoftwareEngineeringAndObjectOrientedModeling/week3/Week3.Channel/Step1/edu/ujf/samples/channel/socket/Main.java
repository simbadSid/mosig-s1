package edu.ujf.samples.channel.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

  private static final String PORT_ARG = "-port=";
  private static final String ACCEPT_ARG = "-accept";
  private static final String CONNECT_ARG = "-connect";

  static int port = -1;
  static boolean accept = false;
  static boolean connect = false;

  static void parseArgs(String args[]) {
    for (int i = 0; i < args.length; i++)
      if (args[i].startsWith(PORT_ARG))
        port = Integer.parseInt(args[i].substring(PORT_ARG.length()));
      else if (args[i].equals(ACCEPT_ARG))
        accept = true;
      else if (args[i].equals(CONNECT_ARG))
        connect = true;
    if (accept && connect) {
      System.err.println("Can't accept and connect at the same time, choose one.");
      System.exit(-1);
    }
    if (!accept && !connect) {
      System.err.println("Please either accept or connect.");
      System.exit(-1);
    }
    if (accept || connect) {
      if (port == -1) {
        System.err.println("Please specify a port number (1234 or 5555 for example).");
        System.exit(-1);
      }
    }
  }
  
  public static void main(String args[]) {
    parseArgs(args);
    try {
      if (accept) {
        System.out.println("Please connect to port=" + port);
        ServerSocket ssock = new ServerSocket(port);
        Socket sock = ssock.accept();
        InputStream in = sock.getInputStream();
        Consumer c = new Consumer(System.out, "> ");
        Channel ch = new Channel(c);
        Producer p = new Producer(in, ch);
        p.produce();
        sock.close();
        ssock.close();
      } else if (connect) {
        Socket sock = null;
        while (sock == null)
          try {
            sock = new Socket("localhost", port);
          } catch (Exception ex) {
          }
        OutputStream out = sock.getOutputStream();
        PrintStream ps = new PrintStream(out);
        Consumer c = new Consumer(ps, null);
        Channel ch = new Channel(c);
        Producer p = new Producer(System.in, ch);
        p.produce();
        sock.close();
      }
    } catch (IOException ex) {
      System.err.println("Exception: " + ex.getMessage());
      ex.printStackTrace(System.err);
      System.exit(-1);
    }
  }
}
