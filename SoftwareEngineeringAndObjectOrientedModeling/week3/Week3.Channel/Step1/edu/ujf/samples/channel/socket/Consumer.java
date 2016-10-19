package edu.ujf.samples.channel.socket;

import java.io.PrintStream;

public class Consumer implements Channel.Listener {
  PrintStream ps;
  String prompt;
  Consumer(PrintStream ps, String prompt) {
    this.ps = ps;
    this.prompt = prompt;
  }
  @Override
  public void posted(char[] chars, int offset, int length) {
    if (prompt!=null)
      ps.print(prompt);
    for (int k = 0; k < length; k++)
      ps.print(chars[offset+k]);
  }

}
