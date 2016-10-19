package edu.ujf.samples.channel.threads;

import java.util.LinkedList;

public class Channel {

  static public class Element {
    char[] chars;
    int offset;
    int length;
    Element(char[] chars, int offset, int length) { 
      this.chars = chars;
      this.offset = offset;
      this.length = length;
    }
  }

  LinkedList<Element> elems;

  public Channel() {
    elems = new LinkedList<Element>();
  }

  public synchronized void post(char[] chars, int offset, int length) {
    Element e = new Element(chars,offset,length);
    elems.addLast(e);
    this.notifyAll();
  }

  public synchronized Element pull() {
    while (elems.size() == 0) {
      try {
        wait();
      } catch (InterruptedException ex) {
      }
    }
    return elems.removeFirst();
  }

}
