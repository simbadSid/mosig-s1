#include "dictionary.h"





dictionary * allocateDictionary()
{
	dictionary *res = malloc(sizeof(dictionary));
	res->nbrWord = 0;
	return res;
}
void buildDictionary(dictionary	*dic)
{
	FILE *fic = fopen(DICTIONARY_FILE, "r");
	dictionary	*tmpDic;
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
				case '.':	if (tmpDic->dotWord == NULL)	tmpDic->dotWord = allocateDictionary();
							tmpDic = tmpDic->dotWord;
							break;
				case '-':	if (tmpDic->dashWord== NULL)	tmpDic->dashWord= allocateDictionary();
							tmpDic = tmpDic->dashWord;
							break;
				default:	printf("\t Error in the dictionary:\n");
							printf("\t\t- English word: %s\n", eng);
							printf("\t\t- Morse word  : %s\n", mor);
							exit(0);
			}
		}
		tmpDic->nbrWord ++;
	}
	fclose(fic);
}

void freeDictionary(dictionary *dic)
{
	if (dic == NULL) return;

	freeDictionary(dic->dashWord);
	freeDictionary(dic->dotWord);
	free(dic);
}

// ---------------------------------------------
// Auxiliary functions
// ---------------------------------------------
char isSubString(char *str, char *subStr)
{
	int i = 0;

	while(subStr[i] != '\0')
	{
		if (str[i] == '\0')			return 0;
		if (str[i] != subStr[i])	return 0;
		i++;
	}
	return 1;
}
int **initTab(int N)
{
	int i, j;
	int **tab = (int **)malloc(sizeof(int*) *N);

	for (i=0; i<N; i++)
	{
		tab[i] = (int*)malloc(sizeof(int) * N);
		for (j=0; j<N; j++) tab[i][j] = 0;
	}
	return tab;
}
void freeTab(int **tab, int N)
{
	int i;

	for (i=0; i<N; i++)	free(tab[i]);
	free(tab);
}
void printTab(int **tab, int N, char *word)
{
	int i, j;
	printf("\n\n");

//	for (j=0; j<N; j++) printf("%4s", "+----");
//	printf("+\n");
//	for (j=0; j<N; j++) printf("|%-4d", j);
//	printf("|\n");
//	for (j=0; j<N; j++) printf("|%-4s", "----");
//	printf("+\n");
	for (j=0; j<N; j++) printf("|%-4c", word[j]);
	printf("|\n");
//	for (j=0; j<N; j++) printf("%4s", "+----");
//	printf("+\n\n\n");
printf("\n");

	for (j=0; j<N; j++)
	{
		for (i=0; i<N; i++)	printf("|%-4d", tab[i][j] );
		printf("|\n");
//		for (z=0; z<N; z++) printf("|%-4s", "----");
//		printf("+\n");
	}

}
