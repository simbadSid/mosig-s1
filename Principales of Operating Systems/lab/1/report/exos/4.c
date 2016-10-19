#include <string.h>
#include <stdio.h>
#include <stdlib.h>

char *concat(char *str1, char *str2)
{
	char *buf = malloc(strlen(str1) + strlen(str2)); 
	return buf;
}

int main()
{
	char *str = concat("hello", "world");
	printf("RESULT : %s\n", str);

	return 0;
}
