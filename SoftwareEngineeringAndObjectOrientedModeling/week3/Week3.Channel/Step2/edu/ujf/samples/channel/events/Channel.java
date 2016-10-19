package edu.ujf.samples.channel.events;

public class Channel {

  public interface Listener {
    void posted(char[] chars, int offset, int length);
  }

  Listener listener;

  public Channel(Listener l) {
    listener = l;
  }

  public void post(char[] chars, int offset, int length) {
    listener.posted(chars, offset, length);
  }

}
