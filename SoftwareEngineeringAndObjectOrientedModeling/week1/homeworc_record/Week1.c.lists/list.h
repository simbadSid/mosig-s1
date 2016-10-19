#ifndef LIST_H_
#define LIST_H_




//------------------------------------
// Values returned by the list functions.
// These values indicates whether the function execution succeeded or not.
//------------------------------------
typedef int status_t;
#define STATUS_OK	0
#define STATUS_ERR	-1

//------------------------------------
// List data structure. (statement)
//------------------------------------
struct list_element;	// Single slot of the list
struct list;			// Hole list data structure

//------------------------------------
//Primitive related to the list structure
//------------------------------------
extern struct list* new_list();

extern int list_size(struct list* list);

extern void* list_element_at(struct list* list, int i);

extern void list_append(struct list* list, void* element);
extern void list_prepend(struct list* list, void* element);

extern int list_insert_before_element(struct list* list, void* mark, void* element);
extern int list_insert_after_element(struct list* list, void* mark, void* element);

extern void list_insert_before_index(struct list* list, int i, void* element);
extern void list_insert_after_index(struct list* list, int i, void* element);

extern void* list_remove_at_index(struct list* list, int i);
extern int   list_remove_(struct list* list, void* element);
extern void list_remove_all(struct list *list);


#endif /* LIST_H_ */
