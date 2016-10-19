package edu.ujf.samples.echo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Echo_v6 {

  public static void main(String args[]) {
    try {
      InputStreamReader in = new InputStreamReader(System.in);
      String encoding = in.getEncoding();
      System.out.println("Using encoding: " + encoding);
      echo(in);
    } catch (IOException ex) {
      System.err.println("Exception: " + ex.getMessage());
      ex.printStackTrace(System.err);
      System.exit(-1);
    }
    System.exit(0);
  }

  static class Element {
    char[] chars;
    Element(char[] chars, int offset, int length) {
      this.chars = new char[length];
      System.arraycopy(chars, offset, this.chars, 0, length);
    }
  }

  private static final int NCHARS = 6;

  private static void echo(InputStreamReader in) throws IOException {
    LinkedList<Element> list = new LinkedList<Element>();
    char[] chars = new char[NCHARS];
    for (;;) {
      int nchars = in.read(chars);
      if (nchars == -1) {
        if (list.size() != 0)
          echoLine(list);
        System.err.println("EOF detected.");
        return;
      }
      split(list,chars,nchars);
    }
  }

  private static void split(LinkedList<Element> list, char chars[], int nchars) {
    int offset = 0; 
    for (int i = 0; i < nchars; i++)
      if (chars[i] == '\n') {
        i+=1;
        Element elem = new Element(chars, offset, i-offset);
        list.add(elem);
        echoLine(list);
        offset = i;
      }
    if (offset<nchars) {
      Element elem = new Element(chars, offset, nchars-offset);
      list.add(elem);
    }
  }

  private static void echoLine(LinkedList<Element> list) {
    System.out.print("> ");
    while (!list.isEmpty()) {
      Element e = list.removeFirst();
      for (int k = 0; k < e.chars.length; k++)
        System.out.print(e.chars[k]);
    }
    return; 
  }

}
