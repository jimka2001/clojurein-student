SRC = ${PWD}/../clojurein/.
DST = ${PWD}/../clojurein-student/.

all:
	cp ${SRC}/doc/*.pdf ./doc/.
	cp ${SRC}/clojurein-source-code/project.clj ./clojurein-source-code/project.clj
	cp -r ${SRC}/clojurein-source-code/test/* ./clojurein-source-code/test/.
	python3 ${SRC}/bin/filter-challenges.py ${SRC}/clojurein-source-code/src/clojurein_source_code/homework ${DST}/clojurein-source-code/src/clojurein_source_code/homework
	cd ${DST}/clojurein-source-code ; ls -l ; lein check


