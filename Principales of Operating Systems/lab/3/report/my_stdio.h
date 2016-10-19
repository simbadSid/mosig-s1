#ifndef MY_STDIO_H
#define MY_STDIO_H

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>



#define BUFFER_SIZE		10
#define FILE_ACCESS_RIGHT	{"r", "w"}
#define NBR_FILE_ACCESS_RIGHT	2






typedef struct
{
	int		fd;
	char	buf[BUFFER_SIZE];
	int		bufSize;
}MY_FILE;



MY_FILE *my_fopen(char *name, char *mode);
int my_fclose(MY_FILE *f);
int my_fread(void *p, size_t size, size_t nbelem, MY_FILE *f);
int my_fwrite(void *p, size_t size, size_t nbelem, MY_FILE *f);
//int my_feof(MY _ILE *f);


// Auxiliaire functions
char checkFileAccessRight(char *mode);
void printFatalError(char *functionName, char *error, char *detail);
int min(int a, int b);
size_t remainingFileSize(int fd);





#endif
