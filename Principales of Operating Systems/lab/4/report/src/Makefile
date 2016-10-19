CC     	= gcc
EXEC   	= match match_parameters sumArray_multiThread sumArray_sequential sumArray_mixt sumArray_sharedVariable
CFLAGS 	= -Wall -Werror -g -pthread
all   	: $(EXEC)










#-----------------------------------------------------------------------------------------------------------
# --------------------------------------- Executables a creer -----------------------------------------------
#-----------------------------------------------------------------------------------------------------------
match	: match.o
	$(CC) $(CFLAGS) -o $@ $^
match_parameters : match_parameters.o
	$(CC) $(CFLAGS) -o $@ $^

sumArray_sequential: sumArray_sequential.o
	$(CC) $(CFLAGS) -o $@ $^ -DSEQUENTIAL

sumArray_multiThread: sumArray_multiThread.o
	$(CC) $(CFLAGS) -o $@ $^ -DMULTITHREAD

sumArray_mixt: sumArray_mixt.o
	$(CC) $(SUM_ARRAY_OPTIONS) $(CFLAGS) -o $@ $^

sumArray_sharedVariable: sumArray_sharedVariable.o
	$(CC) $(SUM_ARRAY_OPTIONS) $(CFLAGS) -o $@ $^

#-----------------------------------------------------------------------------------------------------------
# ------------------------------------------ Modules geres -------------------------------------------------
#-----------------------------------------------------------------------------------------------------------
sumArray_multiThread.o:	sumArray.c
		$(CC) -DMULTITHREAD $(CFLAGS) -c -o $@ $^

sumArray_sequential.o:	sumArray.c
		$(CC) -DSEQUENTIAL $(CFLAGS) -c -o $@ $^

sumArray_mixt.o:	sumArray.c
		$(CC) -DMULTITHREAD -DSEQUENTIAL $(CFLAGS) -c -o $@ $^

sumArray_sharedVariable.o:	sumArray_sharedVariable.c
		$(CC) -DMULTITHREAD -DSEQUENTIAL $(CFLAGS) -c -o $@ $^

%.o 		: %.c
		$(CC) $(CFLAGS) -c -o $@ $^

#-----------------------------------------------------------------------------------------------------------
# ----------------------------------------- Methodes generales -----------------------------------------------
#-----------------------------------------------------------------------------------------------------------
.PHONY	: clean mrproper
clean:
	rm -f *.o $(EXEC)
mrproper: clean
	rm -rf $(EXEC)
