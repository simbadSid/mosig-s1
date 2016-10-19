#include <stdio.h>
#include <stdlib.h>




void print_vect(int *v, int size)
{
	int i;
	printf("[ "); 
	for(i = 0; i < size; i++){
		printf("%d ", v[i]); 
	}
	printf("]\n"); 
}

void vect_sum(int *v1, int *v2, int *r, int size)
{
	int i;
	for(i = 0; i < size; i++) r[i] = v1[i] + v2[i];
}

int main()
{
	int v1[] = {1, 2, 4, 7};
	int v2[] = {3, 4, 9, 2};
	int p_result[4];
	vect_sum(v1, v2, p_result, 4);
	print_vect(p_result, 4);
	exit(0);
}
