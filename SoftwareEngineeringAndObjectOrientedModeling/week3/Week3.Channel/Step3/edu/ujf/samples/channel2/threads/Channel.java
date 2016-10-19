package edu.ujf.samples.channel2.threads;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Channel {

  public interface Listener {
    void posted(char[] chars, int offset, int length);
  }

  Listener listener;
  InputStream in;
  OutputStream out;

  public Channel(InputStream in) throws IOException {
    this.in = in;
    this.out = null;
  }

  public Channel(OutputStream out) throws IOException {
    this.out = out;
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }
  
  public void listen() throws IOException {
    for (;;) {
      int nchars = readInt();
      char chars[] = new char[nchars];
      for (int i = 0; i < nchars; i++)
        chars[i] = readChar();
      if (listener != null) 
            listener.posted(chars, 0, nchars);
    }
  }

  private int readInt() throws IOException {
    int ch1 = in.read();
    int ch2 = in.read();
    int ch3 = in.read();
    int ch4 = in.read();
    if ((ch1 | ch2 | ch3 | ch4) < 0)
      throw new EOFException();
    return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
  }

  private void writeInt(int v) throws IOException {
    out.write((v >>> 24) & 0xFF);
    out.write((v >>> 16) & 0xFF);
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

  private char readChar() throws IOException {
    int ch1 = in.read();
    int ch2 = in.read();
    if ((ch1 | ch2) < 0)
      throw new EOFException();
    return (char) ((ch1 << 8) + (ch2 << 0));
  }

  private void writeChar(int v) throws IOException {
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

  public void post(char[] chars, int offset, int length) {
    try {
      writeInt(length);
      for (int i = 0; i < length; i++)
        writeChar(chars[offset + i]);
    } catch (IOException ex) {
      throw new Error("Channel failed.", ex);
    }
  }

}
