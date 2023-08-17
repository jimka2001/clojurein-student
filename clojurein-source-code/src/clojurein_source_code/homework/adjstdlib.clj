(ns clojurein-source-code.homework.adjstdlib
  (:require [clojure.set :refer [union difference]]))


;; In a previous lecture and exercise we implemented the makeAdj_*
;;   functions to calculate the so-called adjacency list for a graph.
;;   Remember that the adjacency list is not a list, rather it is
;;   just called that for historical reasons.
;;   In this assignment you will again implement makeAdj but this time
;;   using the groupBy function.
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
;;   fill in the body of a call to .groupBy{} and a call to map.{}
;;   In each case, one line of code suffices.
;;
;; reversed-edges, to implement this function, you need to substitute
;;   variable names for the ???
;;
;; make-adv, to implement this function you must fill in the condition
;;   within the if(???) which arbitrates between the two variants
;;   of the algorithm.  If in doubt, look at the test cases, and try
;;   to run the tests with your proposed implementation.
;;
;; reachable-vertices, you should be able to use
;;   the exact implementation you wrote in a previous exercise.
;;   Simply copy/paste that solution in place here for the
;;   reachableVertices function.   I estimate that you need
;;   10 to 15 lines of code including braces { }.


;; this function computes the Adj list in the form of a Map[V,Set[V]]
;; assuming the given edges are directed.
(defn make-adj-directed [edges]
  (update-vals (throw (ex-info "Missing single expression, not yet implemented" {}))
               (fn [edges] (throw (ex-info "Missing single expression, not yet implemented" {}))
                 )))



;; (make-adj-directed '((1 2) (1 3) (2 3)))




;; Given a sequence of pairs of the form (src,dst), this
;; function returns a sequence where each pair is reversed
;; to (dst,src).   I.e.,
;; Seq((1,2),(3,4),(5,6)) --> Seq((2,1),(4,3),(6,5))
(defn reversed-edges
  [edges]
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn make-adj [edges directed]
  (if directed
    (make-adj-directed edges)
    (make-adj-directed (concat (throw (ex-info "Missing single expression, not yet implemented" {}))
                               (throw (ex-info "Missing single expression, not yet implemented" {}))
                               ))))

(defn reachable-vertices
  [edges v-start directed]
  (let [adj (make-adj edges directed)]
    (loop [done #{}
           to-do #{v-start}]
      ;; CHALLENGE: student must complete the implementation.
      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
      )))
