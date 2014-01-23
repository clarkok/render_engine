SOURCES = Render.java

render: $(SOURCES)
	javac -d ./obj `find -name "*.java"`

.PHONY: clean

clean:
	rm `find -iname *.class`
