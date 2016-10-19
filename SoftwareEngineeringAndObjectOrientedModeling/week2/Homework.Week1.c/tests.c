/*
 * tests.c
 *
 *  Created on: Sep 5, 2015
 *      Author: ogruber
 */

#include <stddef.h>
#include "stdint.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <sys/time.h>
#include "utils.h"
#include "list.h"
#include "stream.h"

unsigned long long nano_time() {
  unsigned long long result;
  struct timeval tp;
  if (gettimeofday(&tp, NULL) == -1) {
    printf("PANIC: gettimeofday call failed.");
    exit(-1);
  }
  result = (((unsigned long long) tp.tv_sec) * 1000000L);
  result += (((unsigned long long) tp.tv_usec));
  return result * 1000L;
}

void test1(void) {
  struct stream * stream;
  stream = new_stream(20);
  stream_write(stream,"abc",3);
  stream_write(stream,"de",2);
  stream_write(stream,"fghij",5);

  char chars[11];
  int n;
  n = stream_read(stream,chars,10);
  chars[10]=0;
  assert(n==10,"");
  assert(0==strcmp(chars,"abcdefghij"),"");
  printf(" read: %s \n",chars);
  free_stream(stream);
}

void test2(void) {
  struct stream * stream;
  char chars[11];

  stream = new_stream(20);
  stream_write(stream,"abc",3);
  stream_write(stream,"de",2);
  stream_write(stream,"fghij",5);

  assert(2==stream_read(stream,chars,2),"");
  chars[2]=0;
  assert(0==strcmp(chars,"ab"),"");

  assert(4==stream_read(stream,chars,4),"");
  chars[4]=0;
  assert(0==strcmp(chars,"cdef"),"");

  assert(4==stream_read(stream,chars,4),"");
  chars[4]=0;
  assert(0==strcmp(chars,"ghij"),"");

  free_stream(stream);
}

void test3(void) {
  struct stream * stream;
  char chars[20];
  int n;
  stream = new_stream(20);
  n = stream_write(stream,"toto",4);
  assert(n==4,"");
  n = stream_write(stream,",titi",5);
  assert(n==5,"");

  n = stream_read(stream,chars,20);
  assert(n==9,"");
  chars[n]=0;
  assert(0==strcmp(chars,"toto,titi"),"");

  free_stream(stream);
}

void test4(void) {
  struct stream * stream;
  int n;
  stream = new_stream(7);
  n = stream_write(stream,"toto",4);
  assert(n==4,"");
  n = stream_write(stream,",titi",5);
  assert(n==3,"");
  n = stream_write(stream,",tata",5);
  assert(n==0,"");

  n = stream_available(stream);
  assert(n==7,"")
  free_stream(stream);
}

void run_all_tests() {
  unsigned long long start, end;
  start = nano_time();
  test1();
  end = nano_time();
  printf("test1: %llu ms \n", (end - start) / 1000000L);

  start = nano_time();
  test2();
  end = nano_time();
  printf("test2: %llu ms \n", (end - start) / 1000000L);

  start = nano_time();
  test3();
  end = nano_time();
  printf("test3: %llu ms \n", (end - start) / 1000000L);

  start = nano_time();
  test4();
  end = nano_time();
  printf("test4: %llu ms \n", (end - start) / 1000000L);

}
