
CC=g++
CFLAGS=-c -Wall
TARGET_NAME=static_main

all: main

main:libcalc.a
	$(CC) main.cpp -L. -lcalc -o $(TARGET_NAME)

libcalc.a:calc.o
	ar rcs $@ $<

calc.o: calc.cpp

.PHONY: clean

clean:
	rm *.o
	
