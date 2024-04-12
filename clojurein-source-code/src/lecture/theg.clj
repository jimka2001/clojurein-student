(ns lecture.theg
  (:require [common.util :refer [member]]
            [common.show :refer [show]]))

(defn check-edge
  "assert that src and dst are valid verticies of an n-vetex graph"
  [n [src dst]]
  (assert (<= 0 src))
  (assert (<= 0 dst))
  (assert (< src n))
  (assert (< dst n)))

(defn make-adj-1
  "Compute adjacency list as a vector whose k'th element is a set of neighbors
  of vetex k"
  [n edges]
  (doseq [edge edges]
    (check-edge n edge))
  (loop [edges edges
         adj (vec (repeat n #{}))]
    (if (empty? edges)
      adj
      (let [[[src dst] & tail] edges]
        (recur tail
               (assoc adj src (conj (adj src) dst)))))))

(defn make-adj-2
  "Compute adjacency list as a map, which assoicates k with the set of neighbors
  of vetex k"
  [n edges]
  (doseq [edge edges]
    (check-edge n edge))
  (loop [edges edges
         adj {}]
    (if (empty? edges)
      adj
      (let [[[src dst] & tail] edges]
        (recur tail
               (assoc adj src (conj (get adj src #{})
                                    dst)))))))
  
(defn make-adj-3
  [n edges]
  (doseq [edge edges]
    (check-edge n edge))
  (reduce (fn [adj [src dst]]
            (assoc adj src (conj (get adj src #{})
                                 dst)))
          {}
          edges))

(defn make-adj-4
  [edges]
  (into {} (for [[src pairs] (group-by first edges)]
             [src (into #{} (map second pairs))])))

;;(make-adj-1 3 [[0 1] [0 2] [1 2]])
;;(make-adj-2 3 [[0 1] [0 2] [1 2]])
;;(make-adj-3 3 [[0 1] [0 2] [1 2]])
;;(make-adj-4 3 [[0 1] [0 2] [1 2]])

(defn list-paths
  [edges v-start target-length]
  (let [adj (make-adj-4 edges)
        initial-paths [(list v-start)]]
    (letfn [(extend-by-1 [path]
              (assert (not-empty path))
              (let [p-start (first path)]
                (for [neigh (get adj p-start [])
                      :when (not (member neigh path))]
                  (conj path neigh))))]
      (reduce (fn [path _]
                (mapcat extend-by-1 path))
              initial-paths
              (range target-length)))))
    
(def *graph*
  [[0 1] [0 2] 
   [1 2] [1 3] 
   [2 4] [2 5] [2 6] [2 7] 
   [3 0] [3 5] 
   [4 0] [4 6] 
   [5 0] 
   [6 8] 
   [7 8]])

(show 9 *graph* :directed true)

(list-paths *graph* 1 5)
