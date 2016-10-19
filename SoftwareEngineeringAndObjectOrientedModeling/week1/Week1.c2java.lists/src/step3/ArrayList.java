package step3;

public class ArrayList extends List {
  int size;
  Object elems[];
  
  public ArrayList(int size) {
    elems = new Object[size];
  }
  
  @Override
  public int size() {
    return size;
  }

  @Override
  public Object elementAt(int i) {
    return elems[i];
  }

  @Override
  public int indexOf(Object elem) {
    int idx;
    for (idx = 0; idx < size; idx++) 
      if (elems[idx]==elem)
        break;
    return idx;
  }

  @Override
  public void append(Object element) {
    if (elems.length==size) {
      Object[] tmp = new Object[2*size];
      System.arraycopy(elems, 0, tmp, 0, size);
      elems = tmp;
    }
    elems[size++] = element;
  }

  @Override
  public void prepend(Object element) {
    for (int i=size-1; i>=0;i--)
      elems[i+1]=elems[i];
    elems[0]=element;
  }

  @Override
  public void insertBefore(int idx, Object element) {
    for (int i=size-1; i>=idx;i--)
      elems[i+1]=elems[i];
    elems[idx]=element;
  }

  @Override
  public void insertAfter(int idx, Object element) {
    for (int i=size-1; i>idx;i--)
      elems[i+1]=elems[i];
    elems[idx+1]=element;
  }

  @Override
  public Object remove(int idx) {
    Object removed = elems[idx];
    for (int i=idx+1; i<size;i++)
      elems[i-1]=elems[i];
    elems[size-1]=null;
    size--;
    return removed;
  }

  @Override
  public int remove(Object element) {
    for (int i=0; i<size;i++) {
      if (elems[i]==element) {
        int idx = i;
        for (i++; i<size;i++)
          elems[i-1]=elems[i];
        elems[size-1]=null;
        size--;
        return idx;
      }
    }
    return -1;
  }

}
