#include "dictionary.h"






void buildDictionary(dictionary	*dic)
{
	FILE *fic = fopen(DICTIONARY_FILE, "r");
	dictionary	*tmpDic;
	wordList	*tmpWordList;
	char eng[MAX_WORD_SIZE], mor[MAX_WORD_SIZE];
	int i;

	while (!feof(fic))
	{
		tmpDic = dic;
		fscanf(fic, "%s", eng);
		fscanf(fic, "%s", mor);
		for (i = 0; i<strlen(mor); i++)
		{
			switch(mor[i])
			{
				case '.':	if (tmpDic->dotWord == NULL)	tmpDic->dotWord = malloc(sizeof(dictionary));
							tmpDic = tmpDic->dotWord;
							break;
				case '-':	if (tmpDic->dashWord== NULL)	tmpDic->dashWord= malloc(sizeof(dictionary));
							tmpDic = tmpDic->dashWord;
							break;
				default:	printf("\t Error in the dictionary:\n");
							printf("\t\t- English word: %s\n", eng);
							printf("\t\t- Morse word  : %s\n", mor);
							exit(0);
			}
		}
		if (tmpDic->wordList == NULL)
		{
			tmpDic->wordList	= malloc(sizeof(wordList));
			tmpWordList			= tmpDic->wordList;
		}
		else
		{
			tmpWordList			= tmpDic->wordList;
			while(tmpWordList->next != NULL) tmpWordList = tmpWordList->next;
			tmpWordList->next	= malloc(sizeof(wordList));
			tmpWordList			= tmpWordList->next;
		}
		tmpWordList->word = malloc(sizeof(char)*strlen(eng));
		strcpy(tmpWordList->word, eng);
	}
	fclose(fic);
}

void freeDictionary(dictionary *dic)
{
	if (dic == NULL) return;

	freeWordList(dic->wordList);
	freeDictionary(dic->dashWord);
	freeDictionary(dic->dotWord);
	free(dic);
}
void freeWordList (wordList *wordList)
{
	if (wordList == NULL) return;

	free(wordList->word);
	freeWordList(wordList->next);
	free(wordList);
}
int nbrWordInList(wordList *wordList)
{
	if (wordList == NULL)	return 0;
	else					return 1 + nbrWordInList(wordList->next);
}
