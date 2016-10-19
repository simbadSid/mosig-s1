package edu.ujf.samples.channel;

import java.io.PrintStream;

public class Consumer implements Channel.Listener {
  PrintStream ps;
  Consumer(PrintStream ps) {
    this.ps = ps;
  }
  @Override
  public void posted(char[] chars, int offset, int length) {
    ps.print("> ");
    for (int k = 0; k < length; k++)
      ps.print(chars[offset+k]);
  }

}
