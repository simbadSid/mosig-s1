#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>


typedef struct
{
	char *lyrics;
	int			nbrSing;
}suporterParameter;





void *supporter (void *arg)
{
	char		*str= ((suporterParameter *) arg)->lyrics;
	int			nbr	= ((suporterParameter *) arg)->nbrSing;
	int			pid	= getpid (), i;
	pthread_t	tid	= pthread_self ();
	srand ((int) tid) ;

	for (i = 0; i < nbr; i++)
	{
		printf ("Processus %d Thread %x : %s \n", pid, (unsigned int) tid, str) ;
		usleep (rand() / RAND_MAX * 1000000.) ;
	}
	return (void *) tid ;
}

int main (int argc, char **argv)
{
	int team1 ;
	int team2 ;
	int i ;
	int nb_threads = 0 ;
	pthread_t *tids ;
	suporterParameter *arg1, *arg2;

	if (argc != 5)
	{
		fprintf(stderr, "usage : %s team1 team2 nbrSongTeam1 nbrSongTeam2\n", argv[0]) ;
		exit (-1) ;
	}

	team1	= atoi (argv[1]);
	team2	= atoi (argv[2]);
	nb_threads = team1 + team2;
	tids	= malloc (nb_threads*sizeof(pthread_t));
	arg1	= malloc(sizeof(suporterParameter));
	arg2	= malloc(sizeof(suporterParameter));
	arg1->lyrics	= "Allons enfants de la patrie";
	arg2->lyrics	= "Swing low, sweet chariot";
	arg1->nbrSing	= atoi (argv[3]);
	arg2->nbrSing	= atoi (argv[4]);

	/* Create the threads for team1 */
	for (i = 0 ; i < team1; i++)
	{
		pthread_create (&tids[i], NULL, supporter, arg1);
	}
	/* Create the other threads (ie. team2) */
	for ( ; i < nb_threads; i++)
	{
		pthread_create (&tids[i], NULL, supporter, arg2);
	}

	/* Wait until every thread ened */
	for (i = 0; i < nb_threads; i++)
	{
		pthread_join (tids[i], NULL) ;
	}

	free (tids) ;
	return EXIT_SUCCESS;
}
