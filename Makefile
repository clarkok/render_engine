render:
	javac -d ./obj `find -name "*.java"`

.PHONY: clean

clean:
	rm `find -iname *.class`
