INCLUDE_PATH = src/include/

COMPILER = g++
COMPILE_OPTIONS = -I $(INCLUDE_PATH) -Wall -g
COMPILE_CMD = $(COMPILER) $(COMPILE_OPTIONS) -c -o $@ $<

TARGET = bin/render

HEADERS = src/include/def.h src/include/lex.h src/include/html_lex.h \
		  src/include/css_lex.h src/include/praser.h src/include/dom.h \
		  src/include/tree.h

OBJECTS = obj/main.o obj/def.o obj/lex.o obj/html_lex.o obj/css_lex.o \
		  obj/tree.o

$(TARGET): $(OBJECTS)
	$(COMPILER) $(COMPILE_OPTIONS) -o $@ $(OBJECTS)

obj/main.o: src/main.cpp
	$(COMPILE_CMD)

obj/def.o: src/def.cpp src/include/def.h
	$(COMPILE_CMD)

obj/lex.o: src/praser/lex.cpp src/include/lex.h src/include/def.h
	$(COMPILE_CMD)

obj/html_lex.o: src/praser/html_lex.cpp src/include/html_lex.h src/include/lex.h src/include/def.h
	$(COMPILE_CMD)

obj/css_lex.o: src/praser/css_lex.cpp src/include/css_lex.h src/include/lex.h src/include/def.h
	$(COMPILE_CMD)

obj/tree.o: src/lib/tree.cpp src/include/def.h src/include/tree.h
	$(COMPILE_CMD)

.PHONY: clean

clean:
	rm -f $(TARGET) $(OBJECTS)
