/*
 * list.h
 *
 *  Created on: Sep 2, 2015
 *      Author: ogruber
 */

#ifndef LIST_H_
#define LIST_H_


struct list;

/*
 * Allocates a new list.
 */
extern struct list* new_list();

/*
 * Frees a list, it is expected to be empty.
 * The reason is that this generic list would not know how to
 * free the application elements of the list.
 * This means that a list must be empty before it is freed.
 */
extern void free_list(struct list* list);

/*
 * Returns the list size as the number of elements in the list.
 */
extern int list_size(struct list* list);

/*
 * Returns the element at the given position, starting at zero.
 * Warning: the position is expected to be within the list,
 * no boundary checks are done.
 */
extern void* list_element_at(struct list* list, int i);

/*
 * Append the given element at the end of the list.
 * The append is always successful, unless the system runs out of memory.
 * The element is aliased by the list, the list does not acquire the ownership
 * of the element.
 */
extern void list_append(struct list* list, void* element);

/*
 * Prepend the given element at the beginning of the list.
 * The append is always successful, unless the system runs out of memory.
 * The element is aliased by the list, the list does not acquire the ownership
 * of the element.
 */
extern void list_prepend(struct list* list, void* element);

/*
 * Inserts the given element before the given index.
 * The element is aliased by the list, the list does not acquire the ownership
 * of the element.
 * Warning:
 *   the given element is expected to be within the list,
 *   the behavior is unspecified if it is not.
 */
int list_insert_after_element(struct list* list, void* mark, void* element);

/*
 * Inserts the given element before the given index.
 * The element is aliased by the list, the list does not acquire the ownership
 * of the element.
 * Warning:
 *   the given element is expected to be within the list,
 *   the behavior is unspecified if it is not.
 */
int list_insert_before_element(struct list* list, void* mark, void* element);

/*
 * Inserts the given element before the given index, starting at zero.
 * The element is aliased by the list, the list does not acquire the ownership
 * of the element.
 * Warning:
 *   the position is expected to be within the list,
 *   no boundary checks are done.
 *   the behavior is unspecified if it is not.
 */
extern void list_insert_before_index(struct list* list, int i, void* element);


/*
 * Inserts the given element after the given index, starting at zero.
 * The element is aliased by the list, the list does not acquire the ownership
 * of the element.
 * Warning:
 *   the position is expected to be within the list,
 *   no boundary checks are done.
 *   the behavior is unspecified if it is not.
 */
extern void list_insert_after_index(struct list* list, int i, void* element);

/*
 * Remove the element at the given index, starting at zero.
 * The element being only aliased by the list, the element is not freed.
 * Warning:
 *   the position is expected to be within the list,
 *   no boundary checks are done.
 *   the behavior is unspecified if it is not.
 */
extern void* list_remove_at_index(struct list* list, int i);

/*
 * Remove the given element.
 * The element being only aliased by the list, the element is not freed.
 * Warning:
 *   the given element is expected to be within the list,
 *   the behavior is unspecified if it is not.
 */
extern int   list_remove_(struct list* list, void* element);


#endif /* LIST_H_ */
