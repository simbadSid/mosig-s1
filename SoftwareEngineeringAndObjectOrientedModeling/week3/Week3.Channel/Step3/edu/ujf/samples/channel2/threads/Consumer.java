package edu.ujf.samples.channel2.threads;

import java.io.IOException;
import java.io.PrintStream;

public class Consumer implements Channel.Listener {
  PrintStream ps;
  Channel ch;

  Consumer(PrintStream ps, Channel ch) {
    this.ps = ps;
    this.ch = ch;
    ch.setListener(this);
  }

  public void run() {
    try {
      ch.listen();
    } catch (IOException ex) {
      System.err.println("Consumer Exception: " + ex.getMessage());
      ex.printStackTrace(System.err);
      System.exit(-1);
    }
    return;
  }

  @Override
  public void posted(char[] chars, int offset, int length) {
    ps.print("> ");
    for (int k = 0; k < length; k++)
      ps.print(chars[offset + k]);
  }

}
