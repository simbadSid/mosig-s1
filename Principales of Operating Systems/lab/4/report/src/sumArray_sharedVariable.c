#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
# include <time.h>






#define ARRAY_SIZE		1000
#define NBR_THREAD		8

int array[ARRAY_SIZE];
int result[NBR_THREAD];
int availableIndex = -1;
pthread_mutex_t lock;



void *threadFunction (void *arg)
{
	int subArraySize= ARRAY_SIZE/NBR_THREAD;
	int threadIndex = (int)arg;
	int beginIndex	= threadIndex * subArraySize;
	int endIndex	= beginIndex+subArraySize;
	int res = 0, i;

	for (i=beginIndex; i<endIndex; i++) res += array[i];
	if (threadIndex == NBR_THREAD-1)for (i=endIndex; i<ARRAY_SIZE; i++) res += array[i];
	pthread_mutex_lock(&lock);
	availableIndex ++;
	result[availableIndex] = res;
	pthread_mutex_unlock(&lock);
	return (void*)res;
}


int main (int argc, char **argv)
{
	int i, res;
	clock_t t;

	for (i=0; i<ARRAY_SIZE; i++) array[i] = i;

#ifdef SEQUENTIAL
	t = clock();
	res = 0;
	for (i=0; i<ARRAY_SIZE; i++) res += array[i];
	printf("The result of the sequential program is: %d\n", res);
	t = clock() - t;
	printf("The computation time is: %d\n\n\n", (int)t);
#endif
	printf("----------------------\n\n");
#ifdef MULTITHREAD
	t = clock();
	pthread_mutex_init(&lock, NULL);
	pthread_t tids[NBR_THREAD];
	for (i=0; i<NBR_THREAD; i++)
	{
		pthread_create (&tids[i], NULL, threadFunction, (void*)i);
	}
	res = 0;
	for (i=0; i<NBR_THREAD; i++)
	{
		pthread_join (tids[i], NULL);
		printf("\tThe result of the %d th thread to finish is: %d\n", i, result[i]);
		res += result[i];
	}
	printf("The result of the multi-thread program is: %d\n", res);
	t = clock() - t;
	printf("The computation time is: %d\n", (int)t);
	pthread_mutex_destroy(&lock);
#endif


	return 0;
}
