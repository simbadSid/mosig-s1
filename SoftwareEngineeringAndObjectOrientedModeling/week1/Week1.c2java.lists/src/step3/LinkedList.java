package step3;

public class LinkedList extends List {

  static class Entry {
    Entry next;
    Object elem;

    Entry(Object element) {
      this.elem = element;
    }
  }

  Entry head;
  int size;

  public int size() {
    return size;
  }

  public Object elementAt(int idx) {
    Entry pos;
    pos = head;
    for (int i = 0; i < idx; i++)
      pos = pos.next;
    return pos.elem;
  }


  public int indexOf(Object elem) {
    Entry pos;
    int idx;
    pos = head;
    for (idx = 0; idx < size; idx++) {
      if (pos.elem==elem)
        break;
      pos = pos.next;
    }
    return idx;
  }

  public void append(Object element) {
    if (size == 0) {
      Entry niu;
      niu = new Entry(element);
      head = niu;
      size = 1;
    } else
      insertAfter(size - 1, element);
  }

  public void prepend(Object element) {
    if (size == 0) {
      Entry niu;
      niu = new Entry(element);
      head = niu;
      size = 1;
    } else
      insertBefore(0, element);
  }

  public void insertBefore(int idx, Object element) {
    Entry prev = null;
    Entry pos;
    pos = head;
    for (int i = 0; i < idx; i++) {
      prev = pos;
      pos = pos.next;
    }
    Entry niu = new Entry(element);
    if (prev != null) {
      niu.next = prev.next;
      prev.next = niu;
    } else {
      niu.next = head;
      head = niu;
    }
    size++;
  }

  public void insertAfter(int idx, Object element) {
    Entry pos;
    pos = head;
    for (int i = 0; i < idx; i++) {
      pos = pos.next;
    }
    Entry niu = new Entry(element);
    niu.next = pos.next;
    pos.next = niu;
    size++;
  }

  public Object remove(int idx) {
    Entry prev = null;
    Entry pos;
    pos = head;
    for (int i = 0; i < idx; i++) {
      prev = pos;
      pos = pos.next;
    }
    if (prev!=null)
      prev.next = pos.next;
    else
          head = pos.next;
    size--;
    return pos.elem;
  }

  public int remove(Object element) {
    Entry prev = null;
    Entry pos;
    int idx=0;
    pos = head;
    while (pos!=null) {
      if (pos.elem == element)
        break;
      idx++;
      prev = pos;
      pos = pos.next;
    }
    if (prev!=null)
      prev.next = pos.next;
    else
      head = pos.next;
    size--;
    return idx;
  }

}
