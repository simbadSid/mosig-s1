#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dictionary.h"











char		*word;							// Input morse word
int			N;								// Size of the input word
dictionary	*dic;
int			**tab;							// Matrix M such as M[i][j]: nbr of words corresponding
											//		to the morse word starting form i and of size j+1


// ----------------------------------------
// Complexity: L * N   "Size of dictionary * size of Sentence"
// Parameter previousWord must be at least of size N+1
// Parameter indexPreviousWord start at 0
// ----------------------------------------
void buildTab(dictionary *dic, char *previousWord, int indexPreviousWord)
{
	if (dic == NULL)			return;							// Case the dictionary has been completely scanned
	if (indexPreviousWord > N)	return;							// Case the dictionary word don't fit in the phrase

	previousWord[indexPreviousWord] = '.';
	buildTab(dic->dotWord, previousWord, indexPreviousWord+1);
	previousWord[indexPreviousWord] = '-';
	buildTab(dic->dashWord, previousWord, indexPreviousWord+1);
	if (indexPreviousWord == 0) return;

	int i;
	char *tmpWord = word;

	previousWord[indexPreviousWord+1] = '\0';
	for (i=0; i<=N-indexPreviousWord; i++)
	{
		if (!strcmp(tmpWord, previousWord)) tab[i][indexPreviousWord-1] +=nbrWordInList(dic->wordList);
		tmpWord ++;
	}

}
void freeTab()
{
	int i;

	for (i=0; i<N; i++)	free(tab[i]);
	free(tab);
}

int compute(int begining)
{
	int i;
	int res = 0;

	if (begining >= N) return 1;

	for (i=0; i<N; i++)
	{
		if (tab[begining][i] == 0) continue;
		res += tab[begining][i] *compute(begining + i + 1);
	}

	return res;
}

int main(int argc, char **argv)
{
	int i, j, res = -1;
	char previousWor[N+1];

	word	= argv[1];
	N		= strlen(word);
	tab		= (int **)malloc(sizeof(int*) *N);
	dic		= malloc(sizeof(dictionary));

	for (i=0; i<N; i++)					// Initialize parameters
	{
		tab[i] = (int*)malloc(sizeof(int) * N);
		for (j=0; j<N; j++) tab[i][j] = 0;
	}
	buildDictionary(dic);


	buildTab(dic, previousWor, 0);
//for (i=0; i<N; i++)					// Initialize parameters
//{
//	for (j=0; j<N; j++) printf("Tab[%d][%d] = %d\n", i, j, tab[i][j] );
//}


//	res = compute(0);
	printf("\t The number of different phrases is: %d\n", res);

	freeTab();
	freeDictionary(dic);

	return 0;
}
