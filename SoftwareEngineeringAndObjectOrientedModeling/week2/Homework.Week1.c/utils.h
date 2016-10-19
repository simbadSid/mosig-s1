/*
 * main.h
 *
 *  Created on: Sep 17, 2015
 *      Author: ogruber
 */

#ifndef UTILS_H_
#define UTILS_H_

#define assert(cond,msg) { \
  if (!(cond)) {\
    printf("FAILURE: %s",msg);\
    exit(-1);  \
  }\
}

#endif /* UTILS_H_ */
