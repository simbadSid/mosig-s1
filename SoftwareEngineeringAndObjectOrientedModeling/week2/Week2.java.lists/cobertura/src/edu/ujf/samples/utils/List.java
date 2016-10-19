package edu.ujf.samples.utils;

public interface List {

  public int size();

  public Object elementAt(int i);

  public int indexOf(Object element);

  public void append(Object element);

  public void prepend(Object element);

  public void insertBefore(int idx, Object element);

  public void insertAfter(int idx, Object element);

  public Object remove(int idx);

  public boolean remove(Object element);

}
