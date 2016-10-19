package edu.ujf.samples.channel2.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;

public class Producer {
  private static final int NCHARS = 6;

  static class Element {
    char[] chars;

    Element(char[] chars, int offset, int length) {
      this.chars = new char[length];
      System.arraycopy(chars, offset, this.chars, 0, length);
    }
  }

  InputStreamReader in;
  Channel out;
  String name;

  Producer(String name, InputStream in, Channel out) {
    this.name = name;
    this.out = out;
    this.in = new InputStreamReader(in);
    String encoding = this.in.getEncoding();
    System.out.println("Using encoding: " + encoding);
  }

  LinkedList<Element> list = new LinkedList<Element>();

  void produce(final java.util.concurrent.Executor executor) {
    executor.execute(new Runnable() {
      public void run() {
        try {
          poll();
        } catch (IOException ex) {
          System.err.println("Producer Exception: " + ex.getMessage());
          ex.printStackTrace(System.err);
        }
        executor.execute(this);
      }
    });
  }

  void produce(final Executor executor) {
    executor.execute(new Runnable() {
      public void run() {
        try {
          poll();
        } catch (IOException ex) {
          System.err.println("Producer Exception: " + ex.getMessage());
          ex.printStackTrace(System.err);
        }
        executor.execute(this, Main.POLL_DELAY);
      }
    });
  }

  void poll() throws IOException {
    if (!in.ready())
      return;
    boolean complete = false;
    char[] chars = new char[NCHARS];
    int nchars = in.read(chars);
    if (nchars == -1) {
      if (list.size() != 0)
        echoLine(list);
      System.err.println("EOF");
      return; // End of stream detected...
    }
    split(list, chars, nchars);
  }

  private void split(LinkedList<Element> list, char chars[], int nchars) {
    int offset = 0;
    for (int i = 0; i < nchars; i++)
      if (chars[i] == '\n') {
        i += 1;
        Element elem = new Element(chars, offset, i - offset);
        list.add(elem);
        echoLine(list);
        offset = i;
      }
    if (offset < nchars) {
      Element elem = new Element(chars, offset, nchars - offset);
      list.add(elem);
    }
  }

  private void echoLine(LinkedList<Element> list) {

    int nchars = 0;
    Iterator<Element> iter = list.iterator();
    while (iter.hasNext()) {
      Element e = iter.next();
      nchars += e.chars.length;
    }
    int offset = 0;
    char[] chars = new char[nchars];
    while (!list.isEmpty()) {
      Element e = list.removeFirst();
      System.arraycopy(e.chars, 0, chars, offset, e.chars.length);
      offset += e.chars.length;
    }
    out.post(chars, 0, nchars);
  }

}
