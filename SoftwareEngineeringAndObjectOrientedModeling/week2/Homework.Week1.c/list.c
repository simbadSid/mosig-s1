/*
 * list.c
 *
 * Earlier version was a non-defensive implementation...
 * this implementation assumed that the calling code has no bugs.
 *
 * Modified version, added reasonnable runtime checks, as an option
 *  Created on: Sep 2, 2015
 *      Author: Pr. Olivier Gruber
 */

#include <stddef.h>
#include "stdint.h"
#include <stdlib.h>
#include "stdio.h"
#include <string.h>
#include "utils.h"
#include "list.h"

/*
 * Turn this option on if you wish the list to be defensive
 * against poor programming.
 */
#define DEFENSIVE

struct list_entry {
	struct list_entry *next;
	void *elem;
};

struct list {
	struct list_entry * head;
	unsigned int size;
};

struct list* new_list() {
	struct list* list = malloc(sizeof(struct list));
	memset(list, 0, sizeof(struct list));
	return list;
}

void free_list(struct list* list) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
#endif
  free(list);
}

int list_size(struct list* list) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
#endif
	return list->size;
}

/*
 * You may wish to optimize this implementation,
 * bookkeeping the tail... so that we avoid a scan
 * for each append, a quite frequent operation on lists.
 */
void list_append(struct list* list, void* element) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(element!=NULL,"Null element");
#endif
	if (list->size == 0) {
		struct list_entry *niu;
		niu = malloc(sizeof(struct list_entry));
		memset(niu, 0, sizeof(struct list_entry));
		niu->elem = element;
		list->head = niu;
		list->size = 1;
	} else
		list_insert_after_index(list, list->size - 1, element);
}

/*
 * This function is clean, but to the point of calling
 * a generic insert function although we know we are
 * inserting at zero and therefore we can have a
 * much more efficient insert.
 */
void list_prepend(struct list* list, void* element) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(element!=NULL,"Null element");
#endif
	if (list->size == 0) {
		struct list_entry *niu;
		niu = malloc(sizeof(struct list_entry));
		memset(niu, 0, sizeof(struct list_entry));
		niu->elem = element;
		list->head = niu;
		list->size = 1;
	} else
		list_insert_before_index(list, 0, element);
}

/*
 * Defensive programming:
 *  we could easily test for negative index or index larger
 *  that the list size.
 */
void* list_element_at(struct list* list, int idx) {
	struct list_entry *pos;

#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
	if (idx<0 || idx>=list->size)
	  return NULL;
#endif
	pos = list->head;
	for (int i = 0; i < idx; i++)
		pos = pos->next;
	return pos->elem;
}

int list_insert_before_element(struct list* list, void* mark, void* element) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(mark!=NULL,"Null element");
  assert(element!=NULL,"Null element");
#endif
	struct list_entry *prev = NULL;
	struct list_entry *pos;
	int idx = 0;
	pos = list->head;
	while (pos) {
		if (pos->elem == mark)
			break;
		idx++;
		prev = pos;
		pos = pos->next;
	}
#ifdef DEFENSIVE
  assert(pos!=NULL,"mark not found");
#endif
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

int list_insert_after_element(struct list* list, void* mark, void* element) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(mark!=NULL,"Null element");
  assert(element!=NULL,"Null element");
#endif
	struct list_entry *pos;
	int idx = 0;
	pos = list->head;
	while (pos) {
		if (pos->elem == mark)
			break;
		idx++;
		pos = pos->next;
	}
#ifdef DEFENSIVE
  assert(pos!=NULL,"mark not found");
#endif
	struct list_entry *niu;
	niu = malloc(sizeof(struct list_entry));
	memset(niu, 0, sizeof(struct list_entry));
	niu->elem = element;

	niu->next = pos->next;
	pos->next = niu;
	list->size++;
	return idx;
}

void list_insert_before_index(struct list* list, int idx, void* element) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(element!=NULL,"Null element");
#endif
	struct list_entry *prev = NULL;
	struct list_entry *pos;
#ifdef DEFENSIVE
  assert(idx>=0 && idx<list->size,"index out of bounds");
#endif
	pos = list->head;
	for (int i = 0; i < idx; i++) {
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
		niu->next = list->head;
		list->head = niu;
	}
	list->size++;
}

void list_insert_after_index(struct list* list, int idx, void* element) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(element!=NULL,"Null element");
#endif
	struct list_entry *pos;
#ifdef DEFENSIVE
  assert(idx>=0 && idx<list->size,"index out of bounds");
#endif
	pos = list->head;
	for (int i = 0; i < idx; i++) {
		pos = pos->next;
	}
	struct list_entry *niu;
  niu = malloc(sizeof(struct list_entry));
	memset(niu, 0, sizeof(struct list_entry));
	niu->elem = element;
	niu->next = pos->next;
	pos->next = niu;
	list->size++;
}

void* list_remove_at_index(struct list* list, int idx) {
	struct list_entry *prev = NULL;
	struct list_entry *pos;
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(idx>=0 && idx<list->size,"index out of bounds");
#endif
	pos = list->head;
	for (int i = 0; i < idx; i++) {
		prev = pos;
		pos = pos->next;
	}
	if (prev)
		prev->next = pos->next;
	else
	  list->head = pos->next;
	list->size--;
	void* elem = pos->elem;
	free(pos);
	return elem;
}

int list_remove_(struct list* list, void* mark) {
#ifdef DEFENSIVE
  assert(list!=NULL,"Null list");
  assert(mark!=NULL,"Null element");
#endif
	struct list_entry *prev = NULL;
	struct list_entry *pos;
	int idx=0;
	pos = list->head;
	while (pos) {
		if (pos->elem == mark)
			break;
		idx++;
		prev = pos;
		pos = pos->next;
	}
#ifdef DEFENSIVE
  assert(pos!=NULL,"element not found");
#endif
	if (prev)
		prev->next = pos->next;
	else
		list->head = pos->next;
	list->size--;
	free(pos);
	return idx;
}

