(ns homework.theg
  (:require [clojure.set :refer [union difference]]))


;; In a previous lecture and exercise we implemented the makeAdj_*
;;   functions to calculate the so-called adjacency list for a graph.
;;   Remember that the adjacency list is not a list, rather it is
;;   just called that for historical reasons.
;;   In this assignment you will again implement makeAdj but this time
;;   using the group-by function.
;;
;; You must complete the implementation of several functions.
;;    make-adj-directed
;;    reversed-edges
;;    make-adv
;;    reachable-vertices
;;
;; In each case you must replace the ??? with the correct code.
;;
;; make-adj-directed, to implement this function you need to
;;   fill in the body of a call to group-by and a call to map.
;;   In each case, one line of code suffices.
;;
;; reversed-edges, to implement this function, you need to substitute
;;   variable names for the ???
;;
;; make-adv, to implement this function you must fill in the condition
;;   within the (if ...) which arbitrates between the two variants
;;   of the algorithm.  If in doubt, look at the test cases, and try
;;   to run the tests with your proposed implementation.
;;
;; reachable-vertices, you should be able to use
;;   the exact implementation you wrote in a previous exercise.
;;   Simply copy/paste that solution in place here for the
;;   reachable-vertices function.   I estimate that you need
;;   10 to 15 lines of code.


(defn make-adj-directed 
  "This function computes the adjacency list in the form of a which is a hashmap
   which maps a vertex to the set of its neighbors
   assuming the given edges are directed."
  [edges]
  (update-vals (group-by (throw (ex-info "Missing single expression, not yet implemented" {}))
                         )
               (fn [edges] (throw (ex-info "Missing single expression, not yet implemented" {}))
                 )))


(defn reversed-edges
  "Given a sequence of pairs of the form (src dst) or [src dst], this
   function returns a sequence where each pair is reversed
   to (dst src).   I.e.,
   [(1 2) (3 4) (5 6)] --> [(2 1) (4 3) (6 5)]"
  [edges]
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn make-adj
  "Returns a hashmap from vertex to set of verticies which are neighbors."
  [edges directed]
  (if directed
    (make-adj-directed edges)
    (make-adj-directed (concat edges
                               (throw (ex-info "Missing single expression, not yet implemented" {}))
                               ))))

(defn reachable-vertices
  "Return a set of all verticies which are reachable starting at vertex `v-start`.
  The graph can be directed or undirected depending on the input parameter `directed`"
  [edges v-start directed]
  (let [adj (make-adj edges directed)]
    (loop [done #{}
           to-do #{v-start}]
      ;; CHALLENGE: student must complete the implementation.
      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
      ;; HINT 7 line(s)
      )))

  
(defn partition-vertices-by-distance
  "Returns a hash-map which assoicates a distance to the set
  of verticies that distance from the given v-start"
  [edges v-start directed]
  (let [adj (make-adj edges directed)]
    (loop [f 1
           m {0 #{v-start}}
           seen #{v-start}]
      ;; CHALLENGE: student must complete the implementation.
      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
      ;; HINT 7 line(s)
      )))
