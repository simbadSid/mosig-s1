package step2;

public class Main {

  static void print(LinkedList list) {
    System.out.println("List:");
    for (int i = 0; i < list.size(); i++) {
      String s = (String) list.elementAt(i);
      System.out.println("   list[" + i + "]=" + s);
    }
  }

  static void test1() {
    LinkedList list = new LinkedList();
    list.append(new String("titi"));
    list.append(new String("tata"));
    list.prepend(new String("toto"));
    System.out.println("Expected: toto, titi, tata");
    print(list);
  }
  
  static void test2() {
    LinkedList list = new LinkedList();

    list.append(new String("titi"));
    list.insertAfter(0,new String("tata"));
    list.prepend(new String("toto"));
    list.insertBefore(1,"foo");
    list.insertBefore(3,"bar");

    System.out.println("Expected: toto, foo, titi, bar, tata");
    print(list);
    
    list.remove(4);
    System.out.println("Expected: toto, foo, titi, tata");
    print(list);
    
    list.remove(1);
    System.out.println("Expected: toto, titi, tata");
    print(list);
    
    list.remove(0);
    System.out.println("Expected: titi, tata");
    print(list);
    if (LinkedList.BUGS)
      System.err.println("ERROR!");


  }

  public static void main(String[] args) {
    
    test1();
    test2();
    
  }

}
