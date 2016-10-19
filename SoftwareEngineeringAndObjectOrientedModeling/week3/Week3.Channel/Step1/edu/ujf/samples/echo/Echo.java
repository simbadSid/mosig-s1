package edu.ujf.samples.echo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Echo {

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

    /* Note for students:
     * Constructors are a big help... use them when possible
     * they ensure a proper initialization of your objects.
     * Moreover, the use of constructors avoids repeating 
     * the initialization code everywhere.
     */
    /**
     * Construct an element, from a range of characters 
     * in the given character array. 
     * Notice that the characters are copied and no reference
     * to the character array is kept.
     * @param chars
     * @param offset in the character array
     * @param length in the character array
     */
    Element(char[] chars, int offset, int length) {
      this.chars = new char[length];
      System.arraycopy(chars, offset, this.chars, 0, length);
    }
  }

  /*
   * Note for students:
   * Always better to use constants whenever possible,
   * than repeat numbers in different places in your code.
   */
  private static final int NCHARS = 6;

  /*
   * Note for students:
   * (1) Better to pass the stream, more general coding practice.
   * (2) Factorize code into methods whenever appropriate (echoLine),
   *     it helps the readability of your code and its maintenance.
   * (3) Removed the print of the "> " prompt, it does not work well anyway.
   *     We cannot relate the input and the output of the prompt, can we?
   *     What if the input stream buffers multiple lines at a time?
   * (4) Proper splitting into lines    
   *     It is possible that the read characters do not constitute a full line,
   *     in which case, accumulate in the list.
   *     It is also possible that the read characters contains multiple lines
   *     and some remaining characters belonging to an unfinished line.
   *     Echo each line and preserve the extra characters.
   *     
   */ 
  private static void echo(InputStreamReader in) throws IOException {
    LinkedList<Element> list = new LinkedList<Element>();
    // it is ok to use a single array since read characters are copied
    // into the list elements.
    char[] chars = new char[NCHARS];
    for (;;) {
      int nchars = in.read(chars);
      if (nchars == -1) {
        if (list.size() != 0)
          echoLine(list);
        System.err.println("EOF detected.");
        return;
      }
      // Now split the read characters into line, delimited by a '\n'.
      split(list,chars,nchars);
      // The list is empty here, or it contains one element with 
      // the first characters of a new line.
    }
  }

  /**
   * Proper splitting into lines    
   * It is possible that the read characters do not constitute a full line,
   * in which case, we accumulate them in the list.
   * It is also possible that the read characters contains multiple lines
   * and some remaining characters belonging to an unfinished line.
   * Echo each line and preserve the extra characters.
   *
   * Note: 
   *    upon returning, the list has at most one element, 
   *    that element containing the beginning of a new line.
   *              
   * @param list
   * @param chars
   * @param nchars
   */
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

  /**
   * Print all characters in the list as one single line.
   * When returning, the list is empty.  
   * @param list
   */
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
