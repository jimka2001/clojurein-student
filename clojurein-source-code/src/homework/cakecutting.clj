(ns homework.cakecutting
  )


;; The numerical (cheating) solution of Mind Your Decisions
;; Can you Solve The Pythagoras Pie Puzzle?
;; https:;;www.youtube.com/watch?v=9PVcTdSlGBA&t=30s
;; In the video, the narrator solves the problem by finding
;; the closed form formula for the size of the n'th slice.
;; Your assignment is to do the calculation iteratively.

(defn piece-sizes-rec
  "This function, piece-sizes-rec, uses loop/recur to perform the calculation.

  returns a list of [k value] pairs where value is the size of the kth piece of pie.
  0.0 < value < 100.0 which indicates the percentage of the entire pie.  e.g. 0.5
  indicates half the pie.
  The list runs from n ... 1.
  The returned list is sorted into order of decreasing slice size."
  []
  (loop [n 1
         remaining-size 1.0
         acc ()]
    (let [piece-size (throw (ex-info "Missing single expression, not yet implemented" {}))
          ]
      (if (= n 100)
        (throw (ex-info "Missing single expression, not yet implemented" {}))
        (recur (throw (ex-info "Missing single expression, not yet implemented" {}))
               (throw (ex-info "Missing single expression, not yet implemented" {}))
               (throw (ex-info "Missing single expression, not yet implemented" {}))
               )))))

(defn piece-sizes-reduce
  "This function, piece-sizes-reduce, uses reduce to compute the same value
  as computed by piece-sizes-rec.

   returns a list of [k value] pairs where value is the size of the kth piece of pie
   the list runs from n ... 1
   The returned list is sorted into order of decreasing slice size."
  []
  (let [[_ data] (reduce (fn [[remaining-size history] k]
                           (let [piece-size (throw (ex-info "Missing single expression, not yet implemented" {}))
                                 ]
                             [(- remaining-size piece-size)
                              (throw (ex-info "Missing single expression, not yet implemented" {}))
                              ]
                             ))
                         [1.0 ()]
                         (throw (ex-info "Missing single expression, not yet implemented" {}))
                         )]
    (reverse (sort-by second data))))

