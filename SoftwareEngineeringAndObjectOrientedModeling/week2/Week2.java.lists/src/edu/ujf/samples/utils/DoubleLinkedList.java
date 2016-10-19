package edu.ujf.samples.utils;

public class DoubleLinkedList implements List2 {

  static class Entry implements Element {
    Entry next,prev;
    Object elem;

    Entry(Object element) {
      elem = element;
      next = prev = this;
    }
    
    private void extract() {
      prev.next = next;
      next.prev = prev;
      next = prev = this;
    }
    
    private void insertBefore(Entry pos) {
      next = pos;
      prev = pos.prev;
      prev.next = this;
      pos.prev = this;
    }

    private void insertAfter(Entry pos) {
      next = pos.next;
      next.prev = this;
      prev = pos;
      pos.next = this;
    }

    @Override
    public Object get() {
      return elem;
    }
  }

  Entry head,last;
  int size;

  public DoubleLinkedList() {

  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Element elementAt(int idx) {
    Entry pos;
    pos = head;
    for (int i = 0; i < idx; i++)
      pos = pos.next;
    return pos;
  }

  @Override
  public Element elementAt(Object element) {
    Entry pos;
    pos = head;
    for (int i = 0; i < size; i++) {
      if (pos.elem == element)
        return pos;
      pos = pos.next;
    }
    return null;
  }

  @Override
  public Element append(Object element) {
    Entry niu;
    niu = new Entry(element);
    if (size == 0) {
      head = last = niu;
      size = 1;
    } else {
      niu.insertAfter(last);
      last = niu;
      size++;
    }
    return niu;
  }

  @Override
  public Element prepend(Object element) {
    Entry niu;
    niu = new Entry(element);
    if (size == 0) {
      head = niu;
      size = 1;
    } else {
      niu.insertBefore(head);
      size++;
    }
    return niu;
  }

  @Override
  public Element insertBefore(Element mark, Object element) {
    Entry niu = new Entry(element);
    niu.insertBefore((Entry)mark);
    size++;
    return niu;
  }

  @Override
  public Element insertAfter(Element mark, Object element) {
    Entry niu = new Entry(element);
    niu.insertAfter((Entry)mark);
    size++;
    return niu;
  }

  @Override
  public void remove(Element elem) {
    Entry pos = (Entry)elem;
    if (size==1) {
      head = last = null;
      size = 0;
    } else {
      size--;
      if (last==pos)
        last = pos.prev;
      if (head==pos)
        head = head.next;
    }
    pos.extract();
  }
  

}
