/*
 * main.c
 *
 *  Created on: Sep 2, 2015
 *      Author: ogruber
 */

#include <stddef.h>
#include "stdint.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "list.h"

#ifndef CFLAGS
#define CFLAGS ""
#endif

void print_list(char* prefix, struct list* list) {
  int size = list_size(list);
  if (size == 0) {
    printf("%s: empty list.\n", prefix);
    return;
  }
  printf("%s\n", prefix);
  for (int i = 0; i < list_size(list); i++) {
    char* s = (char*) list_element_at(list, i);
    printf(" list[%d]=%s \n", i, s);
  }
}


int main(int argc, char** argv) {

  struct list* list;
  list = new_list();

  list_append(list, "zero");
  list_append(list, "un");
  list_append(list, "deux");
  list_append(list, "trois");

  print_list("Initial:", list);

  list_remove_at_index(list, 3);
  print_list("Removed[3]", list);
  list_remove_at_index(list, 1);
  print_list("Removed[1]", list);
  list_remove_at_index(list, 0);
  print_list("Removed[0]", list);

  list_remove_at_index(list, 0);
  print_list("Removed[1]", list);


  extern void run_all_tests();
  for (int i=0;i<argc;i++)
    if (0==strcmp(argv[i],"-tests")) {
      printf("\nRunning tests: cflags are %s\n",CFLAGS);
      run_all_tests();
      printf("All done with tests.\n\n");
    }
  return 0;
}

