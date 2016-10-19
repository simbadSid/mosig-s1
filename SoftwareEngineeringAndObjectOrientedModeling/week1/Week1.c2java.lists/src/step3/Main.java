package step3;

public class Main {

  public static void main(String[] args) {

    List list = new LinkedList();
    
    list.append(new String("titi"));
    list.append(new String("tata"));
    list.prepend(new String("toto"));

    for (int i = 0; i < list.size(); i++) {
      String s = (String) list.elementAt(i);
      System.out.println("   list[" + i + "]=" + s);
    }
  }

}
