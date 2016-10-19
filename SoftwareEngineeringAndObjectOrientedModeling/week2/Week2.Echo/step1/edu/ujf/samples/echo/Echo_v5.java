package edu.ujf.samples.echo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Echo_v5 {

  public static void main(String args[]) {
    try {
      echo();
    } catch (IOException ex) {
      System.err.println("Exception: " + ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }

  static class Element {
    int nchars;
    char[] chars;
  }

  /**
   * Buggy or not, can you tell?
   */
  private static void echo() throws IOException {
    char[] chars = new char[6]; 
    LinkedList<Element> list = new LinkedList<Element>();

    InputStreamReader in = new InputStreamReader(System.in);
    
    String encoding = in.getEncoding();
    System.out.println("Using encoding: " + encoding);

    System.out.print("> ");
    for (;;) {
      boolean complete = false;
      int nchars = in.read(chars);
      for (int i = 0; i < nchars; i++)
        if (chars[i] == '\n') {
          complete = true;
          break;
        }
      Element elem = new Element();
      elem.chars = chars;
      elem.nchars = nchars;
      list.add(elem);

      if (!complete)
        continue;
      
      for (int i = 0; i < list.size(); i++) {
        Element e = (Element) list.get(i);
        for (int k = 0; k < e.nchars; k++)
          System.out.print(e.chars[k]);
      }
      System.out.print("> ");
    }
  }

}
