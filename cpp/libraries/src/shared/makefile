
CC=g++
CFLAGS=-c -Wall
TARGET_NAME=shared_main

all: main
	LD_LIBRARY_PATH=. | export LD_LIBRARY_PATH

main: libcalc.so
	$(CC) main.cpp -L. -lcalc -o $(TARGET_NAME)

libcalc.so: calc.o
	$(CC) -shared -fPIC calc.cpp -o libcalc.so

.PHONY: clean

clean:
	rm *.o
