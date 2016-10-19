package edu.ujf.samples.netcat.threads;

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

  static int port = 5555;
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
  }

  private static final boolean BURST_MODE = false;

  public static void main(String args[]) {

    parseArgs(args);

    try {
      Producer p;
      if (accept) {
        System.out.println("Please connect to port=" + port);
        ServerSocket ssock = new ServerSocket(port);
        Socket sock = ssock.accept();
        setup(sock);
      } else if (connect) {
        Socket sock = null;
        System.out.println("Connecting to port=" + port);
        while (sock == null)
          try {
            sock = new Socket("localhost", port);
          } catch (Exception ex) {
          }
        setup(sock);
      }

    } catch (Throwable th) {
      System.err.println("Main Exception: " + th.getMessage());
      th.printStackTrace(System.err);
      System.exit(-1);
    }
    return;
  }

  static private void setup(final Socket sock) throws IOException {
    Thread thread;

    thread = new Thread(new Runnable() {
      Channel sockch;
      Consumer stdout;
      InputStream in;

      @Override
      public void run() {
        try {
          in = sock.getInputStream();
          sockch = new Channel(in);
          stdout = new Consumer(System.out, sockch);
          sockch.listen();
        } catch (IOException ex) {
          System.err.println("Exception: " + ex.getMessage());
          ex.printStackTrace(System.err);
          System.exit(-1);
        }
      }
    }, "Thread(sockin/stdout)");
    thread.start();

    thread = new Thread(new Runnable() {
      OutputStream out;
      PrintStream ps;
      Channel sockch;
      Producer stdin;

      @Override
      public void run() {
        try {
          out = sock.getOutputStream();
          ps = new PrintStream(out);
          sockch = new Channel(out);
          stdin = new Producer("stdin", System.in, sockch);
          stdin.produce();

        } catch (IOException ex) {
          System.err.println("Client(connect) Exception: " + ex.getMessage());
          ex.printStackTrace(System.err);
          System.exit(-1);
        }
      }
    }, "Thread(stdin/sockout)");
    thread.start();
  }

  //  static private void accept(final Socket sock) throws IOException {
  //    Thread thread;
  //    
  //    thread = new Thread(new Runnable() {
  //      Channel sockch;
  //      Consumer stdout;
  //      InputStream in;
  //      @Override
  //      public void run() {
  //        try {
  //          in = sock.getInputStream();
  //          sockch = new Channel(in);
  //          stdout = new Consumer(System.out, sockch);
  //          sockch.listen();
  //        } catch (IOException ex) {
  //          System.err.println("Server(accept) Exception: " + ex.getMessage());
  //          ex.printStackTrace(System.err);
  //          System.exit(-1);
  //        }
  //      }
  //    }, "Server(sockin/stdout)");
  //    thread.start();
  //
  //    thread = new Thread(new Runnable() {
  //      OutputStream out;
  //      PrintStream ps;
  //      Channel sockch;
  //      Producer stdin;
  //      @Override
  //      public void run() {
  //        try {
  //          out = sock.getOutputStream();
  //          ps = new PrintStream(out);
  //          sockch = new Channel(out);
  //          stdin = new Producer("stdin", System.in, sockch);
  //          stdin.produce();
  //
  //        } catch (IOException ex) {
  //          System.err.println("Client(connect) Exception: " + ex.getMessage());
  //          ex.printStackTrace(System.err);
  //          System.exit(-1);
  //        }
  //      }
  //    }, "Server(stdin/sockout)");
  //    thread.start();
  //
  //  }
  //
  //  static private void connect(final Socket sock) throws IOException {
  //    Thread thread;
  //    
  //    thread = new Thread(new Runnable() {
  //      Channel sockch;
  //      Consumer stdout;
  //      InputStream in;
  //      @Override
  //      public void run() {
  //        try {
  //          in = sock.getInputStream();
  //          sockch = new Channel(in);
  //          stdout = new Consumer(System.out, sockch);
  //          sockch.listen();
  //
  //        } catch (IOException ex) {
  //          System.err.println("Server(accept) Exception: " + ex.getMessage());
  //          ex.printStackTrace(System.err);
  //          System.exit(-1);
  //        }
  //      }
  //    }, "Client(sockin/stdout)");
  //    thread.start();
  //    
  //    thread = new Thread(new Runnable() {
  //      OutputStream out;
  //      PrintStream ps;
  //      Channel sockch;
  //      Producer stdin;
  //      @Override
  //      public void run() {
  //        try {
  //          out = sock.getOutputStream();
  //          ps = new PrintStream(out);
  //          sockch = new Channel(out);
  //          stdin = new Producer("stdin", System.in, sockch);
  //          stdin.produce();
  //
  //        } catch (IOException ex) {
  //          System.err.println("Client(connect) Exception: " + ex.getMessage());
  //          ex.printStackTrace(System.err);
  //          System.exit(-1);
  //        }
  //      }
  //    }, "Client(stdin/sockout)");
  //    thread.start();
  //    
  //  }
}
