package edu.ujf.samples.utils;

public class Main {

  static void print(String prefix,List list) {
    int size = list.size();
    if (size==0) {
      System.out.println(" " + prefix+": empty list.");
      return;
    }
    System.out.println(" " + prefix+": ");
    for (int i=0;i<list.size();i++) {
      String s = (String)list.elementAt(i);
      System.out.println("   list["+i+"]="+s);
    }
  }

  static void print(String prefix,List2 list) {
    int size = list.size();
    if (size==0) {
      System.out.println(" " + prefix+": empty list.");
      return;
    }
    System.out.println(" " + prefix+": ");
    for (int i=0;i<list.size();i++) {
      String s = (String)list.elementAt(i).get();
      System.out.println("   list["+i+"]="+s);
    }
  }

  static void test(List list) {
    System.out.println("\nTesting List: ");
    list.append("zero");
    list.append("un");
    list.append("deux");
    list.append("trois");

    print("Initial:",list);

    list.remove(3);
    print("Removed[3]",list);
    list.remove(1);
    print("Removed[1]",list);
    list.remove(0);
    print("Removed[0]",list);

    list.remove(0);
    print("Removed[1]",list);
  }

  static void test(List2 list) {
    System.out.println("\nTesting List2: ");
    List2.Element zero= list.append("zero");
    List2.Element one= list.append("un");
    List2.Element two= list.append("deux");
    List2.Element three= list.append("trois");

    print("Initial:",list);

    list.remove(three);
    print("Removed[3]",list);
    list.remove(one);
    print("Removed[1]",list);
    list.remove(zero);
    print("Removed[0]",list);

    list.remove(two);
    print("Removed[1]",list);
  }

  
  public static void main(String args[]) {
    LinkedList list;
    list = new LinkedList();
    
    test(list);

    DoubleLinkedList list2 = new DoubleLinkedList();
    test(list2);
  }
}
