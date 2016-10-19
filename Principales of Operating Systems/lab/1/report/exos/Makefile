
# Standard command line for compilation
# gcc = GNU Compiler Collection compiler
# Simple command: gcc main.c (the output is in a.out)
# Name the executable:  gcc main.c -o main (which can be executed ./main)

# Or you can use a Makefile which automizes the compilation process
# The actions in the Makefile are invoked with the command make

CC=gcc
CFLAGS=-W -Wall -pedantic -g -std=c99 
LDFLAGS=
EXEC=ex1 ex2 ex3 rec 0 1 2 3 4
SRC= $(wildcard *.c)
OBJ= $(SRC:.c=.o)

all: $(EXEC)

# The command uses the automatic variables `$@' and `$<' to substitute the names 
# of the target file and the source file in each case where the rule applies
# 
#$@
#The file name of the target of the rule. 
#If the target is an archive member, then `$@' is the name of the archive file. 
#In a pattern rule that has multiple targets (see section Introduction to Pattern Rules), 
#`$@' is the name of whichever target caused the rule's commands to be run.

#$%
#The target member name, when the target is an archive member. 
#See section Using make to Update Archive Files. 
#For example, if the target is `foo.a(bar.o)' then `$%' is `bar.o' and `$@' is `foo.a'. 
#`$%' is empty when the target is not an archive member.

#$<
#The name of the first dependency. If the target got its commands from an implicit rule, 
#this will be the first dependency added by the implicit rule (see section Using Implicit Rules).

#$?
#The names of all the dependencies that are newer than the target, with spaces between them.
# For dependencies which are archive members, only the member named is used (see section Using make to Update Archive Files).

#$^
#The names of all the dependencies, with spaces between them. For dependencies which are archive members, 
#only the member named is used (see section Using make to Update Archive Files). 
#A target has only one dependency on each other file it depends on, 
#no matter how many times each file is listed as a dependency. 
#So if you list a dependency more than once for a target, the value of $^ contains just one copy of the name.


%.o: %.c
	$(CC) -o $@ -c $< $(CFLAGS)


.PHONY: clean mrproper

clean:
	rm -rf *.o

rmproper: clean
	rm -rf $(EXEC)
