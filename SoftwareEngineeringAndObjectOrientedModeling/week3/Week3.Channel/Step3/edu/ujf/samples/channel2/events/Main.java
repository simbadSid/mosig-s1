package edu.ujf.samples.channel2.events;

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

  static final int POLL_DELAY = 10;

  static int port = -1;
  static boolean accept = false;
  static boolean connect = false;

  private static void parseArgs(String args[]) {
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

    Executor executor;
    executor = new Executor();

    try {
      Producer p;
      if (accept)
        accept(executor);
      else
        connect(executor);

      executor.start();

    } catch (Throwable th) {
      System.err.println("Main Exception: " + th.getMessage());
      th.printStackTrace(System.err);
    }
  }

  static private void accept(Executor executor) throws IOException {
    System.out.println("Please connect to port=" + port);
    ServerSocket ssock = new ServerSocket(port);
    Socket sock = ssock.accept();
    InputStream in = sock.getInputStream();
    Consumer stdout;

    stdout = new Consumer(System.out);
    Channel stdch = new Channel(executor,in,stdout);
  }

  static private void connect(Executor executor) throws IOException {
    Socket sock = null;
    System.out.println("Connecting to port=" + port);
    while (sock == null)
      try {
        sock = new Socket("localhost", port);
      } catch (Exception ex) {
      }
    OutputStream out = sock.getOutputStream();
    Channel sockch = new Channel(executor,out);
    
    Producer stdin = new Producer("stdin", System.in, sockch);
    stdin.produce(executor);
  }
}
