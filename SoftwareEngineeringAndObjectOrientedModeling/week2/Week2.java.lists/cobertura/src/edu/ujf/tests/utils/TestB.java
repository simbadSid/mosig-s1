package edu.ujf.tests.utils;

import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ujf.samples.utils.LinkedList;
import edu.ujf.samples.utils.List;

/**
 * Simple tests, certainly not complete, 
 * to illustrate the use of the JUnit framework.
 *
 * @author Pr. Olivier Gruber <olivier dot gruber @ acm dot org>
 */
public class TestB {

  LinkedList empty;

  @Before
  public void setUp() {
    empty = new LinkedList();
  }

  @After
  public void tearDown() {
    empty = null;
  }

  @Test
  public void test1() {
    assert(empty.size() == 0);
  }

  @Test(expected = NoSuchElementException.class) 
  public void empty() {
    empty.elementAt(0);
  }

  @Test(expected = NoSuchElementException.class)
  public void test2() throws NoSuchElementException {
    empty.insertAfter(0, new Object());
  }

  public static int RANDOM_INSERT_SIZE = 1000;

  @Test
  public void test3() {
    LinkedList list = new LinkedList();
    randomInsert(list);
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
   * For example, let's suppose the random gives us the following sequence:
   * 
   *    3,1,2,0
   *      
   * So the list will be growing like this:
   *      { "3" }                 insertedValues = [ false, false, false, true]
   *      { "1","3" }             insertedValues = [ false, true, false, true ]
   *      { "1","2","3" }         insertedValues = [ false, true, true, true ]
   *      { "0,"1","2","3" }      insertedValues = [ true, true, true, true ]
   *      
   * @param list
   */
  private static void randomInsert(List list) {
    boolean[] insertedValues = new boolean[RANDOM_INSERT_SIZE];
    int count = 0;
    Random rand = new Random();
    while (count < RANDOM_INSERT_SIZE) {
      int idx = rand.nextInt(RANDOM_INSERT_SIZE);
      if (!insertedValues[idx]) {
        boolean inserted = false;
        for (int i = 0; i < count; i++) {
          String s = (String) list.elementAt(i);
          int mark = Integer.valueOf(s);
          if (idx > mark) {
            list.insertAfter(i, Integer.toString(idx));
            inserted = true;
            break;
          }
        }
        if (!inserted)
          list.append(Integer.toString(idx));
        insertedValues[idx] = true;
        count++;
      }
    }
    // check that the list is sorted, as it should.
    for (int i = 0; i < insertedValues.length; i++)
      assert(i == Integer.valueOf((String) list.elementAt(i)));
  }

}
