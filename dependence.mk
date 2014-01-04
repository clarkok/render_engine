obj/dom.o: src/lib/dom.cpp src/include/def.h src/include/tree.h \
 src/include/def.h src/include/dom.h src/include/tree.h
	$(COMPILE_CMD)
obj/tree.o: src/lib/tree.cpp src/include/def.h src/include/tree.h \
 src/include/def.h
	$(COMPILE_CMD)
obj/css_lex.o: src/parser/css_lex.cpp src/include/def.h src/include/css_lex.h \
 src/include/def.h src/include/lex.h
	$(COMPILE_CMD)
obj/html_lex.o: src/parser/html_lex.cpp src/include/def.h \
 src/include/html_lex.h src/include/def.h src/include/lex.h
	$(COMPILE_CMD)
obj/html_parser.o: src/parser/html_parser.cpp src/include/def.h \
 src/include/tree.h src/include/def.h src/include/html_parser.h \
 src/include/lex.h src/include/tree.h src/include/dom.h \
 src/include/html_lex.h src/include/html_parser.h
	$(COMPILE_CMD)
obj/lex.o: src/parser/lex.cpp src/include/lex.h src/include/def.h
	$(COMPILE_CMD)
obj/def.o: src/def.cpp src/include/def.h
	$(COMPILE_CMD)
obj/main.o: src/main.cpp src/include/tree.h src/include/def.h \
 src/include/html_lex.h src/include/lex.h src/include/html_parser.h \
 src/include/tree.h src/include/dom.h src/include/html_lex.h \
 src/include/html_parser.h
	$(COMPILE_CMD)
