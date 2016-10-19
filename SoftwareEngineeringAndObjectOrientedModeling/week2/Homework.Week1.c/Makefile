CONFIG_DEBUG=y
CONFIG_GPROF=n
CONFIG_COVERAGE=y

CFLAGS= -Wall -std=gnu99
LDFLAGS=
LIBS=

# Generate debug information
ifeq ($(CONFIG_DEBUG),y)
CFLAGS+= -g3  
LDFLAGS+= -g3 
LIBS+=
else
CFLAGS+= -O3 
LDFLAGS+= -O3 
LIBS+= 
endif

# Generate coverage (gcov)
# better without optimization, hence use -g3
ifeq ($(CONFIG_COVERAGE),y)
CFLAGS+= -g3 -fprofile-arcs -ftest-coverage 
LDFLAGS+= -g3 
LIBS+= -lgcov
endif

# Generate performance information (gprof)
ifeq ($(CONFIG_GPROF),y)
CFLAGS+= -O3 -pg 
LDFLAGS+= -O3 -pg 
LIBS+=
endif

GCC=gcc
EXEC=stream

all: list.o main.o tests.o stream.o
	$(GCC) $(LDFLAGS) -o $(EXEC) list.o main.o tests.o stream.o $(LIBS)	

clean: 
	rm -f *.o $(EXEC)
	rm -rf *.gcda *.gcno $(EXEC).info html
	rm -rf gmon.out $(EXEC).gprof

tests: clean all
	time ./$(EXEC) -tests

gprof: clean all
	./$(EXEC) -tests
	gprof $(EXEC) gmon.out > $(EXEC).gprof

gcov: clean all
	lcov --base-directory . --directory . --zerocounters -q
	./$(EXEC) -tests
	lcov --base-directory . --directory . -c -o $(EXEC).info
	lcov --remove $(EXEC).info "/usr*" -o $(EXEC).info # remove output for external libraries
	rm -rf html
	genhtml -o html -t "Test Coverage" --num-spaces 4 $(EXEC).info
	google-chrome html/index.html &

leaks: clean all
	reset ; valgrind --leak-check=full --db-attach=yes --db-command="/usr/bin/gdb -nw %f %p" ./$(EXEC) -tests

main.o: main.c
	$(GCC) -c $(CFLAGS) -DCFLAGS="\"$(CFLAGS)\"" -o main.o main.c

list.o: list.c list.h
	$(GCC) -c $(CFLAGS) -o list.o list.c	

tests.o: tests.c
	$(GCC) -c $(CFLAGS) -o tests.o tests.c

stream.o: stream.c
	$(GCC) -c $(CFLAGS) -o stream.o stream.c
