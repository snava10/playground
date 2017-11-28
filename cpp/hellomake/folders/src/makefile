IDIR =../include
CC=g++
CFLAGS= -c -Wall -I$(IDIR)

ODIR=obj
LDIR =../lib
BIN=bin

SOURCES = $(wildcard *.cpp)
$(warning sources is $(sources))
#OBJS = $(SOURCES:.cpp=.o)
OBJS = $(SOURCES:%.cpp=%.o)

$(warning objs is $(OBJS))

all: hello

hello: $(OBJS) 
	$(CC) $(addprefix $(ODIR)/, $(OBJS)) -o $(addprefix $(BIN)/, hello)

#.cpp.o:
#	$(CC) $< $(CFLAGS) -o $(addprefix $(ODIR)/, $@)
%.o : %.cpp
	$(CC) $< $(CFLAGS) -o $(addprefix $(ODIR)/, $@)

.PHONY: clean

clean:
	rm -f $(ODIR)/*.o *~ core $(INCDIR)/*~
	

