SRC = ${PWD}/../clojurein/.
DST = ${PWD}/../clojurein-student/.

all:
	cp ${SRC}/doc/*.pdf ./doc/.
	cp -r ${SRC}/clojurein-source-code/test/* ./clojurein-source-code/test/.
	python3 ${SRC}/bin/filter-challenges.py ${SRC}/clojurein-source-code/src/clojurein_source_code/homework ${DST}/clojurein-source-code/src/clojurein_source_code/homework

