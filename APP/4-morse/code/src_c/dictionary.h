#ifndef DICTIONARY_H_
#define DICTIONARY_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>




#define DICTIONARY_FILE	"../resource/dict-english-morse.txt"
#define MAX_WORD_SIZE	300


typedef struct DICTIONARY					// Tree based morse dictionary
{
	int					nbrWord;
	struct DICTIONARY	*dotWord;
	struct DICTIONARY	*dashWord;
} dictionary;







dictionary * allocateDictionary();
void buildDictionary(dictionary	*dic);
void freeDictionary	(dictionary	*dic);
char isSubString	(char *str, char *subStr);

// ---------------------------------------------
// Auxiliary functions
// ---------------------------------------------
char isSubString	(char *str, char *subStr);
int **initTab		(int N);
void freeTab		(int **tab, int N);
void printTab		(int **tab, int N, char *word);





#endif /* DICTIONARY_H_ */
