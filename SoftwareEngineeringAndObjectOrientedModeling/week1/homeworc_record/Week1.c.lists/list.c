#include <stddef.h>
#include "stdint.h"
#include <stdlib.h>
#include <string.h>
#include "list.h"






//------------------------------------
// List data structure. (Definition)
//------------------------------------
struct list_entry						// Single slot of the list
{
	void 				*elem;
	struct list_entry	*next;
};

struct list								// Hole list data structure
{
	struct list_entry * head;
	unsigned int size;
};

//------------------------------------
//Primitive related to the list structure
//------------------------------------
/**===================================
 * Allocates memory space for a one slot list, and returns its address.
 * The returned list is not initialized.
 =====================================*/
struct list* new_list()
{
	struct list* list = malloc(sizeof(struct list));
	memset(list, 0, sizeof(struct list));
	return list;
}

/**===================================
 * Returns the size of the input list.
 =====================================*/
int list_size(struct list* list)
{
	return list->size;
}

/**===================================
 * Add the given element at the end of the list.
 * The given list must have been allocated using the "new_list" function.
 =====================================*/
void list_append(struct list* list, void* element)
{
#ifndef BUGS
	if (list->size == 0)
	{
		struct list_entry *niu;
		niu = malloc(sizeof(struct list_entry));
		memset(niu, 0, sizeof(struct list_entry));
		niu->elem = element;
		list->head = niu;
		list->size = 1;
	} else
#endif
		list_insert_after_index(list, list->size - 1, element);
}

/**===================================
 * Add the given element at the beginning of the list.
 * The given list must have been allocated using the "new_list" function.
 =====================================*/
void list_prepend(struct list* list, void* element)
{
	if (list->size == 0)
	{
		struct list_entry *niu;
		niu = malloc(sizeof(struct list_entry));
		memset(niu, 0, sizeof(struct list_entry));
		niu->elem = element;
		list->head = niu;
		list->size = 1;
	}
	else
		list_insert_before_index(list, 0, element);
}

/**===================================
 * Returns the element at the given index in the given list.
 =====================================*/
void* list_element_at(struct list* list, int idx)
{
	struct list_entry *pos;
	pos = list->head;
	for (int i = 0; i < idx; i++)
		pos = pos->next;
	return pos->elem;
}

/**===================================
 * Add the given element before the given mark in the list.
 =====================================*/
int list_insert_before_element(struct list* list, void* mark, void* element)
{
	struct list_entry *prev = NULL;
	struct list_entry *pos;
	int idx = 0;
	pos = list->head;
	while (pos)
	{
		if (pos->elem == mark)
			break;
		idx++;
		prev = pos;
		pos = pos->next;
	}
	struct list_entry *niu;
	niu = malloc(sizeof(struct list_entry));
	memset(niu, 0, sizeof(struct list_entry));
	niu->elem = element;

	if (prev) {
		niu->next = prev->next;
		prev->next = niu;
	} else {
		niu->next = list->head->next;
		list->head = niu;
	}
	list->size++;
	return idx;
}

/**===================================
 * Add the given element after the given mark in the list.
 =====================================*/
int list_insert_after_element(struct list* list, void* mark, void* element)
{
	struct list_entry *pos;
	int idx = 0;
	pos = list->head;
	while (pos)
	{
		if (pos->elem == mark) break;
		idx++;
		pos = pos->next;
	}
	struct list_entry *niu;
	niu = malloc(sizeof(struct list_entry));
	memset(niu, 0, sizeof(struct list_entry));
	niu->elem = element;

	niu->next = pos->next;
	pos->next = niu;
	list->size++;
#ifndef BUGS
	return idx;
#endif
}

/**===================================
 * Puts the given element before the given index of the list.
 * The elements currently after the given index are shifted.
 =====================================*/
void list_insert_before_index(struct list* list, int idx, void* element)
{
	struct list_entry *prev = NULL;
	struct list_entry *pos;
	pos = list->head;
	for (int i = 0; i < idx; i++)
	{
		prev = pos;
		pos = pos->next;
	}
	struct list_entry *niu;
#ifdef BUGS
	niu = malloc(sizeof(struct list_entry*));
#else
	niu = malloc(sizeof(struct list_entry));
#endif
	memset(niu, 0, sizeof(struct list_entry));
	niu->elem = element;
	if (prev) {
		niu->next = prev->next;
		prev->next = niu;
	} else {
		niu->next = list->head;
		list->head = niu;
	}
	list->size++;
}

/**===================================
 * Puts the given element at the given index of the list.
 * The elements currently after the given index are shifted.
 =====================================*/
void list_insert_after_index(struct list* list, int idx, void* element)
{
	struct list_entry *pos;
	pos = list->head;
	for (int i = 0; i < idx; i++)
	{
		pos = pos->next;
	}
	struct list_entry *niu;
#ifdef BUGS
  niu = malloc(sizeof(struct list_entry*));
#else
  niu = malloc(sizeof(struct list_entry));
#endif
	memset(niu, 0, sizeof(struct list_entry));
	niu->elem = element;
	niu->next = pos->next;
	pos->next = niu;
	list->size++;
}

void* list_remove_at_index(struct list* list, int idx) {
	struct list_entry *prev = NULL;
	struct list_entry *pos;
	pos = list->head;
	for (int i = 0; i < idx; i++) {
		prev = pos;
		pos = pos->next;
	}
	if (prev)
		prev->next = pos->next;
	else
#ifdef BUGS
		list->head = pos;
#else
	    list->head = pos->next;
#endif
	list->size--;
	void* elem = pos->elem;
	free(pos);
	return elem;
}

int list_remove_(struct list* list, void* mark)
{
	struct list_entry *prev = NULL;
	struct list_entry *pos;
#ifdef BUGS
	int idx;
#else
	int idx=0;
#endif
	pos = list->head;
	while (pos) {
		if (pos->elem == mark)
			break;
		idx++;
		prev = pos;
		pos = pos->next;
	}
	if (prev)
		prev->next = pos->next;
	else
		list->head = pos->next;
	list->size--;
#ifndef BUGS
	free(pos);
#endif
	return idx;
}
// ---------------------------------------------
// Remove all the elements of the list.
// Remove the list
// ---------------------------------------------
void list_remove_all(struct list *list)
{
	struct list_entry *pos = list->head;
	struct list_entry *tmp;

	while (pos != NULL)
	{
		tmp = pos;
		pos = pos->next;
		free(tmp);
	}
	free(list);
}
