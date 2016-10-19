#include "my_stdio.h"







MY_FILE *my_fopen(char *name, char *mode)
{
	char test;
	MY_FILE *res;
	int fd;

	test = checkFileAccessRight(mode);
	if (!test)	printFatalError("my_fopen", "Unhandeled file access mode", mode);
//TODO
//	fd = open(name, mode);
fd = open(name, O_CREAT, S_IRWXU);
	if (fd <= 0)	printFatalError("my_fopen", "The file open failed", NULL);
	res = malloc(sizeof(MY_FILE));
	if (res == NULL)printFatalError("my_fopen", "The memory allocation failed", NULL);
	res->fd			= fd;
	res->bufSize	= 0;

	return res;
}
int my_fclose(MY_FILE *f)
{
//TODO write the buffer in case ...
	close(f->fd);
	return 1;
}
int my_fread(void *p, size_t size, size_t nbelem, MY_FILE *f)
{
	int i, resSize=0;
	char *res = p;
	char *ptr = f->buf;
	size_t remainingSize = min(nbelem*size, remainingFileSize(f->fd));

//TODO check read access right
	for (i=0; i<remainingSize; i++)
	{
		if (f->bufSize == 0)
		{
			f->bufSize = read(f->fd, f->buf, BUFFER_SIZE);
			if (f->bufSize == 0) break;
			if (f->bufSize <  0) printFatalError("my_fread", "An error occured while reading in the file", NULL);
			ptr = f->buf;
		}
		*res = *ptr;
		res ++;
		ptr ++;
		f->bufSize--;
		resSize ++;
	}

	return resSize/size;
}
int my_fwrite(void *p, size_t size, size_t nbelem, MY_FILE *f)
{
	size_t test, resSize=0;
	char *input	= p;
	char *output	= f->buf;
	int i;

//TODO check write access right
	for (i=0; i<nbelem*size; i++)
	{
		if (f->bufSize == BUFFER_SIZE)
		{
			test = write(f->fd, f->buf, BUFFER_SIZE);
			resSize += test;
			if (test < 0) printFatalError("my_fwrite", "An error occurred while writing in the file", NULL);
			if (test != BUFFER_SIZE) break;
			output = f->buf;
			f->bufSize = 0;
		}
		*output = *input;
		output	++;
		input	++;
		f->bufSize ++;
		resSize ++;
	}

	return resSize;
}
// --------------------------------------------------------------------
// Print a formated error message to the user.
// Call the function exit.
// --------------------------------------------------------------------
void printFatalError(char *functionName, char *error, char *detail)
{
	fprintf(stdout, "*************************\n");
	fprintf(stdout, "Error in the function \"%s\" :\n", functionName);
	fprintf(stdout, "%s", error);
	if (detail != NULL)	fprintf(stdout, "%s\n", detail);
	else			fprintf(stdout, "\n");
	fprintf(stdout, "*************************\n");
	exit(0);
}
// --------------------------------------------------------------------
// Check if the given file access mode is handeled by our lybrary
// --------------------------------------------------------------------
char checkFileAccessRight(char *mode)
{
	char *existingAccesRight[] = FILE_ACCESS_RIGHT;
	int i;

	for (i=0; i<NBR_FILE_ACCESS_RIGHT; i++)
	{
		if (strcmp(mode, existingAccesRight[i])) return 1;
	}
	return 0;
}
int min(int a, int b)
{
	if (a <= b)	return a;
	else		return b;
}
size_t remainingFileSize(int fd)
{
	size_t curOffset	= lseek(fd, 0, SEEK_CUR);
	size_t lastOffset	= lseek(fd, 0, SEEK_END);
	size_t res		= lastOffset - curOffset;

	lseek(fd, -res, SEEK_END);
	return (res);
}
