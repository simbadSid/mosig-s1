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

	if ((indexPreviousWord > 0) && (dic->nbrWord > 0))
	{
		int i;
		char *tmpWord = word;

		previousWord[indexPreviousWord] = '\0';
		for (i=0; i<=N-indexPreviousWord; i++)
		{
			if (isSubString(tmpWord, previousWord)) tab[i][indexPreviousWord-1] = dic->nbrWord;
			tmpWord = tmpWord + sizeof(char);
		}
	}

	previousWord[indexPreviousWord] = '.';
	buildTab(dic->dotWord, previousWord, indexPreviousWord+1);
	previousWord[indexPreviousWord] = '-';
	buildTab(dic->dashWord, previousWord, indexPreviousWord+1);

}

// ----------------------------------------
// Complexity: wait for it N^2
// Parameter begining starts at 0
// ----------------------------------------
int compute(int begining)
{
	int size;
	int res = 0;
	if (begining >= N-1) return 1;

	for (size=0; size<N; size++)
	{
		if (tab[begining][size] == 0) continue;
		res += tab[begining][size] * compute(begining + size+1);
	}

	return res;
}

int main(int argc, char **argv)
{
	int res = -1;
	char previousWor[N+1];

	if (argc <= 1)
	{
		printf("\n*** Please put the morse code to process ***\n\n");
		exit(0);
	}
	word	= argv[1];
	N		= strlen(word);
	dic		= allocateDictionary();

	buildDictionary(dic);						// Create the tree based dictionary
	tab = initTab(N);							// Create the fit table
	buildTab(dic, previousWor, 0);
	printTab(tab, N, word);

	res = compute(0);							// Compute the result
	printf("\t The number of different phrases is: %d\n", res);

	freeTab(tab, N);
	freeDictionary(dic);

	return 0;
}
