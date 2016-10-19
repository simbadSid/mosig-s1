#ifndef DICTIONARY_H_
#define DICTIONARY_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>




#define DICTIONARY_FILE	"../resource/dict-english-morse.txt"
#define MAX_WORD_SIZE	300


typedef struct WORDLIST						// List of world corresponding to a morse code
{
	char				*word;
	struct WORDLIST		*next;
}wordList;
typedef struct DICTIONARY					// Tree based morse dictionary
{
	wordList			*wordList;
	struct DICTIONARY	*dotWord;
	struct DICTIONARY	*dashWord;
} dictionary;







void buildDictionary(dictionary	*dic);
void freeDictionary	(dictionary	*dic);
void freeWordList	(wordList	*wordList);
int nbrWordInList	(wordList	*wordList);


#endif /* DICTIONARY_H_ */
