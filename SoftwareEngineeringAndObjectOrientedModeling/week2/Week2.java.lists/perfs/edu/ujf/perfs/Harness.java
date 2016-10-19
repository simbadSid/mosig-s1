package edu.ujf.perfs;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Vector;

import edu.ujf.samples.utils.DoubleLinkedList;

/**
 * Simple Test Harness, inspired by JUnit... 
 * but adapted for performance testing as opposed to unit testing.
 * 
 * A test extends the Test class.
 * A test may require to be warmed up, in which case, you override the method warmup.
 * A test is run multiple times (see TEST_RUN_NUMBER) and the average elapsed time is computed.
 * Between each run, a garbage collection is requested, reducing the chances of measuring a
 * garbage collection interruption during a test run.
 * 
 * Note: standard deviation is missing.
 * 
 * Usage:
 * 
 *    $ java -cp <your-classpath> edu.ujf.perfs.utils.Harness [class[:method[,method...]]]
 *    
 * Examples:
 *    To run all tests in the SimpleTests class:
 *    
 *       $ java -cp ./bin edu.ujf.perfs.utils.Harness <your_package>.<your_class>
 *    
 *    To only run some tests (like test2() and test3())
 *    
 *       $ java -cp ./bin edu.ujf.perfs.utils.Harness <your_package>.<your_class>:<method1>,<method2>
 *    
 *    
 * With no argument, the harness use a default set of tests, see defaultTests variable.
 *
 * @author Pr. Olivier Gruber <olivier dot gruber @ acm dot org>
 */
public class Harness {

  static final int TEST_RUN_NUMBER = 10;

  static boolean warmup;

  public static abstract class Test {
    protected boolean warmup;

    public void echoElapsed(String prefix, long start, long end) {
      long elapsed = end - start;
      Harness.echoElapsed("  " + prefix, elapsed);
    }

    public void echoElapsed(String prefix, long elapsed) {
      Harness.echoElapsed("  " + prefix, elapsed);
    }

    public void run(String msg, Runnable test) {
      long start, end, elapsed;
      elapsed = 0;
      for (int i = 0; i < Harness.TEST_RUN_NUMBER; i++) {
        System.gc();
        start = System.currentTimeMillis();
        test.run();
        end = System.currentTimeMillis();
        elapsed += (end - start);
      }
      // elapsed = ((double) elapsed) / (double) Harness.TEST_RUN_NUMBER;
      elapsed = elapsed / Harness.TEST_RUN_NUMBER;
      if (!warmup)
        echoElapsed(msg, elapsed);
    }

    public abstract void warmup();

    public abstract void runAllTests();
  }

  private static void echoElapsed(String prefix, long elapsed) {
    if (elapsed > 1000L) {
      double rounded = ((double) elapsed) / 1000.0;
      System.out.printf("  " + prefix + ": %.3fs \n", rounded);
    } else {
      System.out.println("  " + prefix + ": " + elapsed + "ms");
    }
  }

  static void run(ClassLoader cl, String test) {

    System.out.println("-------------------------------------------------");
    System.out.println("Test: " + test);
    System.out.println("-------------------------------------------------");

    String classname;
    String tests[] = null;
    Method methods[] = null;
    Vector list = new Vector();
    int idx = test.indexOf(":");
    if (idx == -1) {
      classname = test;
    } else {
      classname = test.substring(0, idx);
      String options = test.substring(idx + 1);
      idx = options.indexOf(",");
      while (idx != -1) {
        list.addElement(options.substring(0, idx));
        options = options.substring(idx + 1);
        idx = options.indexOf(",");
      }
      list.addElement(options);
      if (list.size() != 0) {
        tests = new String[list.size()];
        list.toArray(tests);
      }
    }

    Class c;
    Test t;
    try {
      c = cl.loadClass(classname);
      t = (Test) c.newInstance();
    } catch (Throwable th) {
      th.printStackTrace(System.err);
      System.err.println("   --> failed loading test. exception: " + th.getMessage());
      return;
    }
    if (tests != null) {
      methods = new Method[tests.length];
      for (int j = 0; j < tests.length; j++) {
        try {
          Method m = c.getMethod(tests[j]);
          methods[j] = m;
        } catch (Throwable th) {
          th.printStackTrace(System.err);
          System.err.println("   --> failed loading test: " + tests[j] + " exception: " + th.getMessage());
          continue;
        }
      }
    }

    long start, end, elapsed;
    elapsed = 0L;
    if (tests == null) {
      if (Harness.warmup) {
        System.out.println("  Warming up...");
        start = System.currentTimeMillis();
        t.warmup = true;
        t.warmup();
        end = System.currentTimeMillis();
        elapsed = end - start;
        echoElapsed("-> warmup", elapsed);
      }
      System.out.println("  Running all tests...");
      t.warmup = false;
      start = System.currentTimeMillis();
      t.runAllTests();
      end = System.currentTimeMillis();
      elapsed = (end - start);
      echoElapsed("Elapsed", elapsed);
    } else {
      System.out.println("  no warming up...");
      t.warmup = false;
      for (int j = 0; j < tests.length; j++) {
        try {
          Method m = methods[j];
          if (m == null)
            continue;
          System.out.println("  Running " + tests[j] + "()...");
          start = System.currentTimeMillis();
          m.invoke(t, null);
          end = System.currentTimeMillis();
          elapsed = (end - start);
          echoElapsed("   elapsed " + tests[j], elapsed);
        } catch (Throwable th) {
          th.printStackTrace(System.err);
          System.err.println("   --> failed executing test: " + tests[j] + " exception: " + th.getMessage());
          continue;
        }
      }
    }
  }

  static String[] defaultTests = new String[] { "edu.ujf.perfs.utils.TestA", "edu.ujf.perfs.utils.TestB" };

  public static void main(String args[]) throws IOException {
    boolean jvisualvm = false;
    warmup = true;
    /*
     * Parse arguments, removing those arguments that we interpret as options:
     *  -warmup
     *  -nowarmup
     *  -jvisualvm
     */
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-nowarmup")) {
        warmup = false;
        args[i] = null;
      } else if (args[i].equals("-warmup")) {
        warmup = true;
        args[i] = null;
      } else if (args[i].equals("-jvisualvm")) {
        jvisualvm = true;
        args[i] = null;
      }
    }
    /*
     * Pack arguments, suppressing any null entry we 
     * created above, parsing option arguments.
     */
    int nargs = 0;
    for (int i = 0; i < args.length; i++)
      if (args[i] != null)
        args[nargs++] = args[i];

    if (nargs == 0) {
      args = defaultTests;
      nargs = args.length;
    }
    System.out.println("\n\nRunning "+nargs+" performance tests...");
    if (jvisualvm) {
      System.out.println("  press enter to start");
      System.in.read();      
    }
    ClassLoader cl = Harness.class.getClassLoader();
    for (int i = 0; i < nargs; i++) {
      String test = args[i];
      run(cl, test);
    }
    System.out.println("Tests are over. Running a gc...");
    System.gc();
    if (jvisualvm) {
      System.out.println("Hit Ctrl-C whenever.");
      for (;;)
        try {
          Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
    }
  }
}
