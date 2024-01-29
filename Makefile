SRC = ${PWD}/../clojurein/.
DST = ${PWD}/../clojurein-student/.

all:
	cp ${SRC}/clojurein-source-code/project.clj ./clojurein-source-code/project.clj
	cp -r ${SRC}/clojurein-source-code/resources/France-baby-names ./clojurein-source-code/resources/.
	cp -r ${SRC}/clojurein-source-code/test/* ./clojurein-source-code/test/.
	cp -r ${SRC}/clojurein-source-code/src/clojurein_source_code/common/*.clj ./clojurein-source-code/src/clojurein_source_code/common/.
	python3 ${SRC}/bin/filter-challenges.py ${SRC}/clojurein-source-code/src/clojurein_source_code/homework ${DST}/clojurein-source-code/src/clojurein_source_code/homework
	#cd ${DST}/clojurein-source-code ; ls -l ; lein check
	cd ${DST}/clojurein-source-code/src/clojurein_source_code/homework ; ls -l ; lein exec ${SRC}/bin/match_parens.clj ${DST}/clojurein-source-code/src/clojurein_source_code/homework/*.clj


