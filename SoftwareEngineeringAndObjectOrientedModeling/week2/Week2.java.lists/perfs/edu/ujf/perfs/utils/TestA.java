package edu.ujf.perfs.utils;

import java.util.Random;

import edu.ujf.perfs.Harness;
import edu.ujf.samples.utils.ArrayList;
import edu.ujf.samples.utils.DoubleLinkedList;
import edu.ujf.samples.utils.LinkedList;
import edu.ujf.samples.utils.List;
import edu.ujf.samples.utils.List2;

/**
 * This is a first simple series of tests based on a random insert in a list.
 * We use different implementations for the list:
 *    - linked list
 *    - array list 
 *    - double-linked list
 *    
 * We also use different elements:
 *    - The string representation of numbers.
 *    - An object containing a integer field.
 *    
 * @author Pr. Olivier Gruber <olivier dot gruber @ acm dot org>
 */
public class TestA extends Harness.Test {

  public static boolean VERIFY = false;

  public static int RANDOM_INSERT_SIZE = 1000;

  /*
   * Contains the computed random order in which the 
   * elements, from 0 to RANDOM_INSERT_SIZE, will be 
   * inserted in the list.
   */
  static int[] valuesInInsertOrder;

  public TestA() {
    /*
     * Compute a random order in which to insert values in 
     * the linked list... We pre-compute the order so that 
     * all tests use the same order, so that we can compare
     * their relative performance.
     */
    Random rand = new Random();
    valuesInInsertOrder = new int[RANDOM_INSERT_SIZE];
    boolean[] flags = new boolean[RANDOM_INSERT_SIZE];
    int count = 0;
    while (count < RANDOM_INSERT_SIZE) {
      int idx = rand.nextInt(RANDOM_INSERT_SIZE);
      while (flags[idx])
        idx = (idx + 1) % RANDOM_INSERT_SIZE;
      flags[idx] = true;
      valuesInInsertOrder[count++] = idx;
    }
  }

  /**
   * We definitly want to warmup the Java Virtual Machine here...
   */
  public void warmup() {
    test1();
    test1Bis();
    test2();
    test3();
  }

  public void runAllTests() {
    test1();
    test1Bis();
    test2();
    test3();
  }

  /**
   * The basic idea of this random insert test is the following.
   * We will want an list with RANDOM_INSERT_SIZE elements in the end.
   * Each element is the string representation of a number, 
   * between 0 and RANDOM_INSERT_SIZE.
   * Furthermore, we want that list to be ordered, 
   * from 0 to RANDOM_INSERT_SIZE.
   * However, we want to insert the elements in a random order,
   * while keeping the list sorted at all times.
   * So every time we insert a new value, we scan the list in order
   * to find where it should be inserted in order for the list to 
   * remain sorted.
   * 
   * For example:
   *      valuesInInsertOrder[0]=3
   *      valuesInInsertOrder[1]=1
   *      valuesInInsertOrder[2]=2
   *      valuesInInsertOrder[3]=0
   *      
   * So the list will be growing like this:
   *      { "3" }                 insertedValues = [ false, false, false, true]
   *      { "1","3" }             insertedValues = [ false, true, false, true ]
   *      { "1","2","3" }         insertedValues = [ false, true, true, true ]
   *      { "0,"1","2","3" }      insertedValues = [ true, true, true, true ]
   *      
   * @param list
   */
  private void randomInsert(List list) {
    boolean[] insertedValues = new boolean[RANDOM_INSERT_SIZE];
    int count = 0;
    while (count < insertedValues.length) {
      int value = this.valuesInInsertOrder[count];
      // now scan the list to find where we should insert the new 
      // element (value).
      for (int i = 0; i < count; i++) {
        String s = (String) list.elementAt(i);
        int mark = Integer.valueOf(s);
        if (mark > value) {
          list.insertBefore(i, Integer.toString(value));
          insertedValues[value] = true;
          break;
        }
      }
      if (!insertedValues[value]) {
        list.append(Integer.toString(value));
        insertedValues[value] = true;
      }
      count++;
    }
    if (TestA.VERIFY) {
      // verify that our list is in fact ordered.
      for (int i = 0; i < insertedValues.length; i++)
        assert(i == Integer.valueOf((String) list.elementAt(i)));
    }
  }

  public void test1() {
    String msg = "Random insert (linked list)";
    Runnable test = new Runnable() {
      public void run() {
        LinkedList list = new LinkedList();
        randomInsert(list);
      }
    };
    run(msg, test);
  }

  static class TestElement {
    int val;

    TestElement(int v) {
      val = v;
    }
  }

  /**
  * The basic idea of this random insert test is the following.
  * We will want an list with RANDOM_INSERT_SIZE elements in the end.
  * Each element is an object, containing an integer value, 
  * between 0 and RANDOM_INSERT_SIZE.
  * Furthermore, we want that list to be ordered, 
  * from 0 to RANDOM_INSERT_SIZE.
  * However, we want to insert the elements in a random order,
  * while keeping the list sorted at all times.
  * So every time we insert a new value, we scan the list in order
  * to find where it should be inserted in order for the list to 
  * remain sorted.
  */
  private void randomStructInsert(List list) {
    boolean[] insertedValues = new boolean[RANDOM_INSERT_SIZE];
    int count = 0;
    while (count < insertedValues.length) {
      int idx = this.valuesInInsertOrder[count];
      // now scan the list to find where we should insert the new 
      // element (value).
      TestElement elem = new TestElement(idx);
      for (int i = 0; i < count; i++) {
        TestElement e = (TestElement) list.elementAt(i);
        int mark = e.val;
        if (mark > idx) {
          list.insertBefore(i, elem);
          insertedValues[idx] = true;
          break;
        }
      }
      if (!insertedValues[idx]) {
        list.append(elem);
        insertedValues[idx] = true;
      }
      count++;
    }
    if (TestA.VERIFY) {
      // verify that our list is in fact ordered.
      for (int i = 0; i < insertedValues.length; i++)
        assert(i == ((TestElement) list.elementAt(i)).val);
    }
  }

  public void test1Bis() {
    String msg = "Random struct insert (linked list)";
    Runnable test = new Runnable() {
      public void run() {
        LinkedList list = new LinkedList();
        randomStructInsert(list);
      }
    };
    run(msg, test);
  }

  public void test2() {
    String msg = "Random insert (array list)";
    Runnable test = new Runnable() {
      public void run() {
        ArrayList list = new ArrayList(10);
        randomInsert(list);
      }
    };
    run(msg, test);
  }

  /**
  * The basic idea of this random insert test is the same as the randomInsert(List list)
  * described earlier. The code is duplicated because the DoubleLinkedList has a different 
  * specification than regular list, the rationale was to specify a double-linked list
  * in order to be able to exploit their special insert/remove capabilities.
  */
  private void randomInsert(DoubleLinkedList list) {
    boolean[] insertedValues = new boolean[RANDOM_INSERT_SIZE];
    int count = 0;
    while (count < insertedValues.length) {
      int idx = this.valuesInInsertOrder[count];
      // now scan the list to find where we should insert the new 
      // element (value).
      for (int i = 0; i < count; i++) {
        List2.Element e = list.elementAt(i);
        String s = (String) e.get();
        int mark = Integer.valueOf(s);
        if (mark > idx) {
          list.insertBefore(e, Integer.toString(idx));
          insertedValues[idx] = true;
          break;
        }
      }
      if (!insertedValues[idx]) {
        list.append(Integer.toString(idx));
        insertedValues[idx] = true;
      }
      count++;
    }
    if (TestA.VERIFY) {
      // verify that our list is in fact ordered.
      for (int i = 0; i < insertedValues.length; i++)
        assert(i == Integer.valueOf((String) list.elementAt(i).get()));
    }
  }

  public void test3() {
    String msg = "Random insert (double linked list)";
    Runnable test = new Runnable() {
      public void run() {
        DoubleLinkedList list = new DoubleLinkedList();
        randomInsert(list);
      }
    };
    run(msg, test);
  }
}
