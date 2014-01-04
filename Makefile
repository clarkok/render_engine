INCLUDE_PATH = src/include/

COMPILER = g++
COMPILE_OPTIONS = -I $(INCLUDE_PATH) -Wall -g
COMPILE_CMD = $(COMPILER) $(COMPILE_OPTIONS) -c -o $@ $<

DEPENDENCE = dependence.mk

TARGET = bin/render

HEADERS = src/include/def.h src/include/lex.h src/include/html_lex.h \
		  src/include/css_lex.h src/include/html_parser.h src/include/dom.h \
		  src/include/tree.h

OBJECTS = obj/main.o obj/def.o obj/lex.o obj/html_lex.o obj/css_lex.o \
		  obj/tree.o obj/html_parser.o obj/dom.o

SOURCES = src/lib/dom.cpp src/lib/tree.cpp src/parser/css_lex.cpp \
		  src/parser/html_lex.cpp src/parser/html_parser.cpp src/parser/lex.cpp \
		  src/def.cpp src/main.cpp

$(TARGET): $(OBJECTS)
	mkdir -p obj bin
	$(COMPILER) $(COMPILE_OPTIONS) -o $@ $(OBJECTS)

include $(DEPENDENCE)

.PHONY: clean dependence

clean:
	rm -f $(TARGET) $(OBJECTS)

dependence: $(SOURCES)
	$(COMPILER) -I $(INCLUDE_PATH) -MM $(SOURCES) \
		| sed "/^[^\ ]/s/^/obj\//" \
		| sed "/[^\/]$$/a \	\$$(COMPILE_CMD)" \
		> $(DEPENDENCE)
