#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

#define ARRAY_SIZE 10


typedef struct{
	int calc_size;
	int begin;
	const int *arr;
} arr_chunk, *p_arr_chunk;


void* calc_arr(void *arg){
	p_arr_chunk chunk = (p_arr_chunk) arg;
	fprintf(stderr, "2222222222222222222222222222 %d %d\n", chunk->begin, chunk->calc_size) ;
	int i, result = 0;
	for (i = 0; i < chunk->calc_size; ++i) {
		result += chunk->arr[chunk->begin + i];
	}
	fprintf(stderr, "2222222222222222222222222222 %d\n", result) ;
	return (void*)result;
}


int main (int argc, char **argv){
	if (argc != 2){
		fprintf(stderr, "usage : %s thr_numb\n", argv[0]) ;
	    exit (-1) ;
	}
	int i, nb_threads = atoi(argv[1]);
	int * arr = malloc(ARRAY_SIZE * sizeof(int));
	for (i = 0; i < ARRAY_SIZE; ++i)
		arr[i] = i;
	pthread_t *tids = malloc(nb_threads * sizeof(pthread_t));

	int chunk_size = ARRAY_SIZE / nb_threads;
	for (i = 0; i < nb_threads; ++i) {
		p_arr_chunk chunk = malloc(sizeof(arr_chunk));
		chunk->arr = arr;
		chunk->begin = chunk_size * i;
		chunk->calc_size = chunk_size;
		if (i == nb_threads - 1 && ARRAY_SIZE % nb_threads != 0)
			chunk->calc_size += ARRAY_SIZE % nb_threads;
		pthread_create(&tids[i], NULL, calc_arr, chunk);
	}

	/* Wait until every thread ened */
	int sum = 0;
	int value;
	for (i = 0; i < nb_threads; i++){
		pthread_join(tids[i], (void **)&value);
		fprintf(stderr, "!!!!!!!!!!!!!!!!!!!!!!!!! %d\n", value) ;
		sum += value;
		printf("Final sum: %d\n", sum);
	}
	free(tids);
	return EXIT_SUCCESS;
}
