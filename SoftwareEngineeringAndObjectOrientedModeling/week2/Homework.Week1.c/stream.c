/*
 * stream.c
 *
 *  Created on: Sep 17, 2015
 *      Author: ogruber
 */

#include <stddef.h>
#include "stdint.h"
#include <stdlib.h>
#include "stdio.h"
#include <string.h>
#include "utils.h"
#include "list.h"
#include "stream.h"

/*
 * Turn this option on if you wish the list to be defensive
 * against poor programming.
 */
#define DEFENSIVE

struct stream {
  struct list*list;
  int available;
  int offset;
  int max_size;
};

struct entry {
  char* chars;
  int nchars;
  int offset;
};

struct stream* new_stream(int max_size) {
  struct stream* stream;
  stream = malloc(sizeof(struct stream));
  memset(stream, 0, sizeof(struct stream));
  stream->list = new_list();
  stream->max_size = max_size;
  return stream;
}

void free_stream(struct stream* stream) {
#ifdef DEFENSIVE
  assert(stream!=NULL,"Null stream");
#endif
  int size = list_size(stream->list);
  for (int i = 0; i < size; i++) {
    struct entry*e;
    e = (struct entry*) list_remove_at_index(stream->list, 0);
    free(e->chars);
    free(e);
  }
  free_list(stream->list);
  free(stream);
}

int stream_read(struct stream* stream, char *chars, size_t nchars) {
#ifdef DEFENSIVE
  assert(stream!=NULL,"Null stream");
  assert(chars!=NULL,"Null buffer");
#endif
  int offset = 0;
  if (nchars != 0) {
    if (stream->available < nchars)
      nchars = stream->available;
    while (offset < nchars) {
      int i;
      struct entry *e;
      e = list_element_at(stream->list, 0);
      for (i = e->offset; i < e->nchars && offset < nchars; i++, offset++)
        chars[offset] = e->chars[i];
      if (i >= e->nchars) {
        list_remove_at_index(stream->list, 0);
        free(e->chars);
        free(e);
      }
      else
        e->offset += i;
    }
  }
  return offset;
}

/*
 * Notice that we do copy the character array in order to preserve our
 * specification and keep the ownership of the given array to the caller.
 */
int stream_write(struct stream* stream, char *chars, size_t nchars) {
#ifdef DEFENSIVE
  assert(stream!=NULL,"Null stream");
  assert(chars!=NULL,"Null buffer");
#endif
  if (stream->available >= stream->max_size)
    return 0;
  if (stream->available + nchars >= stream->max_size)
    nchars = stream->max_size - stream->available;

  struct entry* e;
  e = malloc(sizeof(struct entry));
  memset(e, 0, sizeof(struct entry));
  e->chars = malloc(nchars * sizeof(char));
  memcpy(e->chars, chars, nchars);
  e->nchars = nchars;
  e->offset = 0;
  list_append(stream->list, e);
  stream->available += nchars;
  return nchars;
}

int stream_available(struct stream* stream) {
#ifdef DEFENSIVE
  assert(stream!=NULL,"Null stream");
#endif
  return stream->available;
}
