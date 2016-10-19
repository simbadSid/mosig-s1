package edu.ujf.samples.channel.events;

import java.util.LinkedList;

public class Executor implements java.util.concurrent.Executor {

  LinkedList<Runnable> ready;
  
  /*
   * This is a list of delayed events (see Delayed class).
   * The list is ordered based on the ETA of delayed events,
   * the ETA being the estimated time of arrival, meaning
   * when the event reaction (Runnable) will be enqueued 
   * on the ready queue.
   */
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

  /**
   * The start of this executor, the method "captures" the current thread,
   * never returning...
   * Nota Bene: the executor does not expect to run out of events, 
   * if it does, it is considered a fatal error and the executor 
   * exits the Java Virtual Machine (System.exit).
   */
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
   * Events submitted this way are executed in FIFO order. 
   * @param event
   */
  @Override
  public void execute(Runnable event) {
    ready.add(event);
  }

  /**
   * Submit an event for delayed execution.
   * The event is preserved until the delay expires.
   * When the delay will have expired, the event will be 
   * submitted for execution. 
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
      /*
       * insert the new element in the delayed list,
       * but keep the list ordered by Estimated Times of Arrival (ETA)
       */
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
