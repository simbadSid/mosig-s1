#include "my_stdio.h"

int main (int argc, char *argv[])
{
  MY_FILE *f1;
  MY_FILE *f2;
  char c;
  int result;

  // for the sake of simplicity we don't
  // print any error messages
	if (argc != 3)	printFatalError("test_buffered", "Missing parameters: input and output file", NULL);
	f1 = my_fopen(argv[1], "r");
	if (f1 == NULL)	printFatalError("test_buffered", "An error occured while opening the input file", NULL);

	f2 = my_fopen(argv[2], "w");
	if (f2 == NULL)	printFatalError("test_buffered", "An error occured while opening the output file", NULL);

	result = my_fread(&c, 1, 1, f1);
	while (result == 1)
	{
		result = my_fwrite(&c, 1, 1, f2);
		if (result == -1) printFatalError("test_buffered", "An error occured while writing in the output file", NULL);
		result = my_fread(&c, 1, 1, f1);
	}
	if (result == -1)	printFatalError("test_buffered", "An error occured while reading the input file. Result = ", NULL);
	my_fclose(f1);
	my_fclose(f2);
	return 0;
}
