/*
 *  ex1.c
 *
 */

#include <stdlib.h>
#include <stdio.h>




int min(int a, int b, int c)
{
	int tmp_min;					// This variable is allocated in the stack
	tmp_min = a <= b ? a : b;
	tmp_min = tmp_min <= c ? tmp_min : c;
	return tmp_min;
}
int main()
{
	int min_val = min(3, 7, 5);			// This variable is allocated in the stack
	printf("The min is: %d\n", min_val);
	exit(0);
}


// Question I.1
// The a, b and c variables are allocated in the stack.  Them corresponding memory is allocated before the call of the "min" function and released after the function returns.

