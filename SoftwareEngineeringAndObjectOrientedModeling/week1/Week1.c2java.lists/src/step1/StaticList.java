package step1;

class Entry {
  Entry next;
  Object elem;

  Entry(Object element) {
    this.elem = element;
  }
}

class List {
  Entry head;
  int size;
}

class StaticList {
  
  public void main(String args[]) {
    List list = new List();
    StaticList.append(list, new String("titi"));
    StaticList.append(list, new String("tata"));
    StaticList.prepend(list, new String("toto"));
    
    for (int i=0;i<StaticList.size(list);i++) {
      String s = (String)StaticList.elementAt(list,i);
      System.out.println("   list["+i+"]="+s);
    }
  }

  public static int size(List list) {
    return list.size;
  }

  public static Object elementAt(List list, int idx) {
    Entry pos;
    pos = list.head;
    for (int i = 0; i < idx; i++)
      pos = pos.next;
    return pos.elem;
  }

  public static int indexOf(List list, Object elem) {
    Entry pos;
    int idx;
    pos = list.head;
    for (idx = 0; idx < list.size; idx++) {
      if (pos.elem==elem)
        break;
      pos = pos.next;
    }
    return idx;
  }

  public static void append(List list, Object element) {
    if (list.size == 0) {
      Entry niu;
      niu = new Entry(element);
      list.head = niu;
      list.size = 1;
    } else
      insertAfter(list, list.size - 1, element);
  }

  public static void prepend(List list, Object element) {
    if (list.size == 0) {
      Entry niu;
      niu = new Entry(element);
      list.head = niu;
      list.size = 1;
    } else
      insertAfter(list, 0, element);
  }

  public static void insertBefore(List list, int idx, Object element) {
    Entry prev = null;
    Entry pos;
    pos = list.head;
    for (int i = 0; i < idx; i++) {
      prev = pos;
      pos = pos.next;
    }
    Entry niu = new Entry(element);
    if (prev != null) {
      niu.next = prev.next;
      prev.next = niu;
    } else {
      niu.next = list.head.next;
      list.head = niu;
    }
    list.size++;
  }

  public static void insertAfter(List list, int idx, Object element) {
    Entry pos;
    pos = list.head;
    for (int i = 0; i < idx; i++) {
      pos = pos.next;
    }
    Entry niu = new Entry(element);
    niu.next = pos.next;
    pos.next = niu;
    list.size++;
  }

  public static Object remove(List list, int idx) {
    Entry prev = null;
    Entry pos;
    pos = list.head;
    for (int i = 0; i < idx; i++) {
      prev = pos;
      pos = pos.next;
    }
    if (prev != null)
      prev.next = pos.next;
    else
      list.head = pos.next;
    list.size--;
    return pos.elem;
  }

  public static int remove(List list, Object element) {
    Entry prev = null;
    Entry pos;
    int idx = 0;
    pos = list.head;
    while (pos != null) {
      if (pos.elem == element)
        break;
      idx++;
      prev = pos;
      pos = pos.next;
    }
    if (prev != null)
      prev.next = pos.next;
    else
      list.head = pos.next;
    list.size--;
    return idx;
  }

}
