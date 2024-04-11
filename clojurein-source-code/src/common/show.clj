(ns common.show
  (:require [clojure.java.shell :refer [sh]]
            [clojure.pprint :refer [cl-format]]
            [common.view :refer [view-image]]
  ))

(def ^:dynamic *dot-path* "dot")

(defn str-to-png
  "return the name of a file (created as a temp file)
     e.g., in /tmp (depends on OS)
  The content of the file is a PNG image corresponding to
     the output of the dot program with the given dot_string
     as input."
  ([dot-string]
   (str-to-png dot-string "graph"))
  ([dot-string title]
   (let [png-name (.getPath (java.io.File/createTempFile (str title "-") ".png"))
         dot-name (.getPath (java.io.File/createTempFile (str title "-") ".dot"))]
     (spit dot-name dot-string)
     (sh *dot-path* "-Tpng" dot-name "-o" png-name)
     png-name)))

(defn show
  "Given a list of edges, display the graph.
  The display is achieved in an OS dependent way"
  [n edges & {:keys [directed title colors default-color]
               :or {directed false
                    title "graph"
                    default-color "#ffffaa"
                    colors {}}}]
  (assert (> n 0))
  (doseq [[a b] edges]
    (assert (< a n))
    (assert (< b n)))
  (let [text (with-out-str
               (if directed
                 (print "digraph")
                 (print "graph"))
               (println " {" )
               (println "rankdir=LR" )
               (println "node[shape=circle,style=filled]" )
               (when directed
                 (println "edge[arrowhead=vee, arrowsize=.7]"))
               (doseq [s (range n)]
                 (cl-format true "~D [fillcolor=~S]~%" s (get colors s default-color)))
               (doseq [[src dst] edges]
                 (print src)
                 (print (if directed " -> " " -- "))
                 (println dst))
               (print "}\n" ))]
    (view-image (str-to-png text title))))

;; (show 5 [[0 1]
;;          [1 2]
;;          [0 2]
;;          [1 3]
;;          [3 4]] :directed true)
