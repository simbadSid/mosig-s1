package edu.ujf.samples.channel2.events;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Channel {

  public interface Listener {
    void posted(char[] chars, int offset, int length);
  }

  Executor executor;
  Listener listener;
  InputStream in;
  OutputStream out;

  public Channel(Executor executor, InputStream in, Listener listener) throws IOException {
    this.executor = executor;
    this.in = in;
    this.out = null;
    this.state = READ_LENGTH;
    this.listener = listener;
    
    executor.execute(new Runnable() {
      public void run() {
        try {
          poll();
        } catch (IOException ex) {
          System.err.println("Producer Exception: " + ex.getMessage());
          ex.printStackTrace(System.err);
        }
        executor.execute(this, Main.POLL_DELAY);
      }
    });
  }

  public Channel(Executor executor, OutputStream out) throws IOException {
    this.executor = executor;
    this.out = out;
  }

  private static final int READ_LENGTH = 1;
  private static final int READ_CHARS = 2;

  int state;
  int nchars;

  private void poll() throws IOException {
    int nbytes;
    switch (state) {
    case READ_LENGTH:
      nbytes = in.available();
      if (nbytes < 4)
        return;
      nchars = readInt();
      System.out.println(" nchars="+nchars);
      state = READ_CHARS;
    case READ_CHARS:
      nbytes = in.available();
      System.out.println("  nbytes="+nbytes);
      if (nbytes < 2 * nchars) {
        System.out.println("    waiting for nchars="+nchars);
        return;
      }
      char chars[] = new char[nchars];
      for (int i = 0; i < nchars; i++)
        chars[i] = readChar();
      if (listener != null) {
        executor.execute(new Runnable() {
          public void run() {
            listener.posted(chars, 0, nchars);
          }
        });
      }
      state = READ_LENGTH;
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
      System.out.println("Writing nchars="+length);
      writeInt(length);
      for (int i = 0; i < length; i++)
        writeChar(chars[offset + i]);
    } catch (IOException ex) {
      throw new Error("Channel failed.", ex);
    }
  }

}
