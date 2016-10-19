package step3;

public abstract class List {

  public abstract int size();

  public abstract Object elementAt(int i);

  public abstract int indexOf(Object elem);

  public abstract void append(Object element);

  public abstract void prepend(Object element);

  public abstract void insertBefore(int idx, Object element);

  public abstract void insertAfter(int idx, Object element);

  public abstract Object remove(int idx);

  public abstract int remove(Object element);

}
