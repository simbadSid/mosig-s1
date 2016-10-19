package step5;

import java.util.NoSuchElementException;

public class Main {

  static void print(List list) {
    System.out.println("List:");
    for (int i = 0; i < list.size(); i++) {
      String s = (String) list.elementAt(i);
      System.out.println("   list[" + i + "]=" + s);
    }
  }
  
  static void test(List list) {

    list.append(new String("titi"));
    list.insertAfter(0,new String("tata"));
    list.prepend(new String("toto"));
    list.insertBefore(1,"foo");
    list.insertBefore(3,"bar");

    print(list);
  }
  
  static void demoExceptions() {
    List list = new LinkedList();
    try {
      // you will see this one, if you set a breakpoint on 
      // the NoSuchElementException
      Object obj = list.elementAt(1);
    } catch (NoSuchElementException ex) {
    }
    Object obj = list.elementAt(1);
  }
  
  public static void main(String[] args) {

    List list = new LinkedList();
    test(list);
    
    list = new ArrayList(2);
    test(list);
    
    demoExceptions();
  }

}
