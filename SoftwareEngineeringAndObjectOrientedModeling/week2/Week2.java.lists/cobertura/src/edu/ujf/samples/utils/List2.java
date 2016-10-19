package edu.ujf.samples.utils;

public interface List2 {

  public interface Element {
    public Object get();
  }
  
  public int size();

  public Element elementAt(Object element);

  public Element elementAt(int i);

  public Element append(Object element);

  public Element prepend(Object element);

  public Element insertBefore(Element mark, Object element);

  public Element insertAfter(Element mark, Object element);

  public void remove(Element element);

}
