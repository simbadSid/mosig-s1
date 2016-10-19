package edu.ujf.samples.channel2.events;

import java.util.LinkedList;

public class Executor implements java.util.concurrent.Executor {

  LinkedList<Runnable> ready;
  Delayed first, last;

  static class Delayed {
    Runnable event;
    long eta;
    Delayed next;

    Delayed(Runnable event, long delay) {
      this.eta = System.currentTimeMillis() + delay;
      this.event = event;
    }
  }

  public Executor() {
    ready = new LinkedList<Runnable>();
    first = last = null;
  }

  public void start() {
    long last_now=0;
    for (;;) {
      Runnable event;
      long now = System.currentTimeMillis();
      while (first != null) {
        Delayed d = first;
        if (d.eta > now)
          break;
        ready.add(d.event);
        if (first == last)
          first = last = null;
        else
          first = first.next;
      }
      if (ready.isEmpty()) {
        if (first == null) {
          System.out.println("\nScheduler has nothing more to schedule,");
          System.out.println("so that's it folks, exiting...");
          System.exit(0);
        }
        if (now - last_now > 1000) {
          last_now = now;
          System.out.println("Zzz...");
        }
        long delay = first.eta - now;
        if (delay > 0)
          try {
            Thread.sleep(delay);
          } catch (InterruptedException ex) {
          }
      } else {
        last_now = now;
        event = ready.removeFirst();
        event.run();
      }
    }
  }

  /**
   * Submit an event for later execution.
   * @param event
   */
  @Override
  public void execute(Runnable event) {
    ready.add(event);
  }

  /**
   * Submit an event for delayed execution.
   * @param event
   * @param delay (in milliseconds).
   */
  public void execute(Runnable event, long delay) {
    Delayed niu = new Delayed(event, delay);
    if (first == null) {
      first = last = niu;
    } else {
      Delayed pos = first;
      Delayed prev = null;
      boolean inserted = false;
      do {
        if (niu.eta < pos.eta) {
          if (prev == null) {
            niu.next = first;
            first = niu;
          } else {
            niu.next = prev.next;
            prev.next = niu;
            if (prev==last)
              last = niu;
          }
          inserted = true;
          break;
        }
        prev = pos;
        pos = pos.next;
      } while (prev != last);
      if (!inserted) {
        last.next = niu;
        last = niu;
      }
    }
  }

}
