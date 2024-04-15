SRC = ${PWD}/../clojurein/.
DST = ${PWD}/../clojurein-student/.

all:
	cp ${SRC}/*.md .
	mkdir -p .vscode
	cp ${SRC}/.vscode/settings.json ./.vscode/.
	cp ${SRC}/clojurein-source-code/project.clj ./clojurein-source-code/project.clj
	cp -r ${SRC}/clojurein-source-code/resources/France-baby-names ./clojurein-source-code/resources/.
	cp -r ${SRC}/clojurein-source-code/resources/US-baby-names ./clojurein-source-code/resources/.
	cp -r ${SRC}/clojurein-source-code/test/* ./clojurein-source-code/test/.
	cp -r ${SRC}/clojurein-source-code/README.md ./clojurein-source-code/.
	cp -r ${SRC}/clojurein-source-code/Makefile ./clojurein-source-code/.
	mkdir -p ./clojurein-source-code/src/{common,homework,lecture}/.
	mkdir -p ./clojurein-source-code/test/{common,homework,lecture}/.
	cp -r ${SRC}/clojurein-source-code/test/lecture/*.clj ./clojurein-source-code/test/lecture/.
	cp -r ${SRC}/clojurein-source-code/src/common/*.clj ./clojurein-source-code/src/common/.
	cp -r ${SRC}/clojurein-source-code/src/lecture/*.clj ./clojurein-source-code/src/lecture/.
	cp ${SRC}/clojurein-source-code/src/homework/*.md ./clojurein-source-code/src/homework/.
	python3 ${SRC}/bin/filter-challenges.py ${SRC}/clojurein-source-code/src/homework ${DST}/clojurein-source-code/src/homework
	#cd ${DST}/clojurein-source-code ; ls -l ; lein check
	cd ${DST}/clojurein-source-code/src/homework ; ls -l ; lein exec ${SRC}/bin/match_parens.clj ${DST}/clojurein-source-code/src/homework/*.clj


