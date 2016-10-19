package step5;

/**
 * This interface abstracts a list of elements.
 * @author Olivier Gruber
 *
 */
public interface List {

  /**
   * @return the size of list, in number of elements.
   */
  public int size();

  /**
   * Searches for the element at the given index.
   * @param index
   * @return the element at the given index in the list.
   * @throws NoSuchElementException if index is out of bounds.
   */
  public Object elementAt(int index);

  /**
   * Searches the index of the given element.
   * @param index
   * @return the element at the given index in the list.
   * @throws NoSuchElementException if the given element is not found.
   */
  public int indexOf(Object elem);

  /**
   * Append at the end of the list the given element.
   * @param element
   */
  public void append(Object element);

  /**
   * Prepend the given element at the front of the list.
   * @param element
   */
  public void prepend(Object element);

  /**
   * Insert the given element before the given index.
   * @param idx
   * @param element
   * @throws NoSuchElementException if index is out of bounds.
   */
  public void insertBefore(int idx, Object element);

  /**
   * Insert the given element after the given index.
   * @param idx
   * @param element
   * @throws NoSuchElementException if index is out of bounds.
   */
  public void insertAfter(int idx, Object element);

  /**
   * Remove the element at the given index from the list.
   * @param idx
   * @param element
   * @throws NoSuchElementException if index is out of bounds.
   */
  public Object remove(int idx);

  /**
   * Remove the element at the given index from the list.
   * @param element
   * @return the index at which the element was.
   * @throws NoSuchElementException if index is out of bounds.
   */
  public int remove(Object element);

}
