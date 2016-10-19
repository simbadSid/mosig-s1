


typedef struct WORDLIST
{
	char			*word;
	struct WORDLIST		*next;
}wordList;
typedef struct DICTIONARY
{
	wordList		*wordList;
	struct DICTIONARY	dotWords;
	struct DICTIONARY	dashWords;
} dictionary;


char	*word;
int	N;
int	**tab;



// ----------------------------------------
// Complexity: L * N   "Size of dictionary * size of Sentence"
// ----------------------------------------
void buildTab(dictionary *dic)
{
	if (dic == NULL) return;

	wordList wl = dic->wordList;
	int size;
	char subWord[N+1], tmp;
	while(wl != NULL)
	{
		size = strlen(wl->word);
		for (i=0; i<N-size; i++)
		{
			tmp = word + i*(sizeof(char));
			strcpy(subWord, tmp, size);
			if (!strcmp(wl->word, subWord)) Tab[i][size] ++;
		}
		wl = wl->next;
	}
	buildTad(dic->dotWords);
	buildTad(dic->hashWords);
}

int compte(int begining)
{
	int i;
	int res = 0;

	if (begining >= N) return 1;

	for (i=0; i<N; i++) res += tab[beg][i] *compte(begining + i + 1);

	return res;
}

int main(int argc, char **argv)
{
	int i, j;

	word	= argv[1];
	N	= strlen(word);
	tab	= (int **)malloc(sizeof(int) * N*N);
	
	for (i=0; i<N; i++)					// Initialize parameters
		for (j=0; j<N; j++) tab[i][j] = 0;
	buildTab(dic);

	res = compte();
}
