package edu.ujf.perfs.utils;

import edu.ujf.perfs.Harness;
import edu.ujf.samples.utils.DoubleLinkedList;
import edu.ujf.samples.utils.LinkedList;
import edu.ujf.samples.utils.List2;

/**
 * This test has been designed to compare the performance of 
 * linked lists, using either a single or double linking.
 *
 * @author Pr. Olivier Gruber <olivier dot gruber @ acm dot org>
 */
public class TestB extends Harness.Test {

  public static int RANDOM_INSERT_SIZE = TestA.RANDOM_INSERT_SIZE;
  static int[] valuesInInsertOrder = TestA.valuesInInsertOrder;

  public TestB() {
    // Do not recompute an order,
    // reuse the order from TestA,
    // so that results are comparable.
  }

  public void warmup() {
    test1();
    test2();
  }

  public void runAllTests() {
    test1();
    test2();
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
   * The random order has been determined in the constructor,
   * once for all tests, so that our different random insert tests
   * can be compared.
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
   * However, the algorithm has been modified from the TestA version.
   * Instead of scanning the list, each time we want to insert a new value,
   * we use our knowledge of already inserted values (insertedValues and 
   * elements) in order to find where the new value should be inserted. 
   * 
   * @param list
   */
  private void randomInsert(LinkedList list) {
    boolean[] insertedValues = new boolean[RANDOM_INSERT_SIZE];
    String[] elements = new String[RANDOM_INSERT_SIZE];
    int count = 0;
    while (count < insertedValues.length) {
      int value = this.valuesInInsertOrder[count];
      String e = Integer.toString(value);
      // find where to insert the new value,
      // we no longer scan the list, we search for the already inserted value
      // that is larger than the value we want to insert.
      boolean inserted = false;
      for (int i = value; i < insertedValues.length; i++) {
        if (insertedValues[i]) {
          int pos = list.indexOf(elements[i]);
          list.insertBefore(pos, e);
          inserted = true;
          break;
        }
      }
      if (!inserted)
        list.append(e);
      insertedValues[value] = true;
      elements[value] = e;
      count++;
    }
    if (TestA.VERIFY) {
      // verify that our list is in fact ordered.
      for (int i = 0; i < insertedValues.length; i++)
        assert(i == Integer.valueOf((String) list.elementAt(i)));
    }
  }

  public void test1() {
    String msg = "Random insert (optimized, linked list)";
    Runnable test = new Runnable() {
      public void run() {
        LinkedList list = new LinkedList();
        randomInsert(list);
      }
    };
    run(msg, test);
  }

  /**
   * This random insert test is the same as explained above
   * for the random insert in a LinkedList. 
   * @param list
   */
  private void randomInsert(DoubleLinkedList list) {
    int[] insertedValues = new int[RANDOM_INSERT_SIZE];
    List2.Element[] elements = new List2.Element[RANDOM_INSERT_SIZE];
    int count = 0;
    while (count < insertedValues.length) {
      int value = this.valuesInInsertOrder[count];
      // Find where to insert the new value,
      // we no longer scan the list, we search for the already inserted value
      // that is larger than the value we want to insert.
      // Notice how efficient it is with double-linked list to insert
      // before a known element...
      List2.Element e = null;
      boolean inserted = false;
      for (int i = value; i < insertedValues.length; i++) {
        if (insertedValues[i] != 0) {
          e = list.insertBefore(elements[i], Integer.toString(value));
          inserted = true;
          break;
        }
      }
      if (!inserted)
        e = list.append(Integer.toString(value));
      insertedValues[value] = value;
      elements[value] = e;
      count++;
    }
    if (TestA.VERIFY) {
      // verify that our list is in fact ordered.
      for (int i = 0; i < insertedValues.length; i++)
        assert(i == Integer.valueOf((String) list.elementAt(i).get()));
    }
  }

  public void test2() {
    String msg = "Random insert (optimized, double linked list)";
    Runnable test = new Runnable() {
      public void run() {
        DoubleLinkedList list = new DoubleLinkedList();
        randomInsert(list);
      }
    };
    run(msg, test);
  }

}
