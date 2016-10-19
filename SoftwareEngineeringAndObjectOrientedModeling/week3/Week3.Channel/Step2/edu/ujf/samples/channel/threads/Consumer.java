package edu.ujf.samples.channel.threads;

import java.io.PrintStream;

public class Consumer extends Thread {
  PrintStream ps;
  Channel ch;
  Consumer(String name,PrintStream ps, Channel ch) {
    super(name);
    this.ps = ps;
    this.ch = ch;
    
    start();
  }
  
  public void run() {
    for (;;) {
      Channel.Element e = ch.pull();
      for (int k = 0; k < e.length; k++)
        ps.print(e.chars[e.offset+k]);
    }
  }

}
