package edu.ujf.tests.utils;

import java.io.IOException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class is a simple harness to launch JUnit tests
 * from the command line or a script.
 * 
 * @author Pr. Olivier Gruber <olivier dot gruber @ acm dot org >
 *
 */
public class Harness {

  public static void main(String args[]) throws IOException {
    System.out.println("Staring tests: press enter");
    System.in.read();

    Result result = JUnitCore.runClasses(TestA.class, TestB.class);

    if (result.wasSuccessful())
      System.out.println("All tests were successful.");
    else {
      System.out.println("Tests: "+ result.getRunCount()+" were run.");
      System.out.println("       "+ result.getFailureCount() +" failed.");
      for (Failure failure : result.getFailures())
        System.out.println("       "+ failure.toString());
    }
    System.out.println("\nHit Ctrl-C whenever ready.");
    for (;;)
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }
  }

}
