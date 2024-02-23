(ns homework.recursion
  )


(defn sum-by-simple-recursion
  "Use simple recursion to sum the elements in a given list"
  [nums]
  (if (empty? nums)
    0
    (+ (throw (ex-info "Missing single expression, not yet implemented" {}))
       (sum-by-simple-recursion (throw (ex-info "Missing single expression, not yet implemented" {}))
                                ))))

(defn sum-by-loop-recur
  "Use loop/recur to sume the elements in a given list"
  [nums]
  (loop [acc 0
         nums nums]
    (if (empty? nums)
      (throw (ex-info "Missing single expression, not yet implemented" {}))
      (recur (throw (ex-info "Missing single expression, not yet implemented" {}))
             (throw (ex-info "Missing single expression, not yet implemented" {}))
             ))))


(defn sum-by-reduce
  "Use reduce to sum the elements in a given list"
  [nums]
  (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          ))

(defn product-by-simple-recursion
  "Use simple recursion to compute the productuct (multiplicative) of the elements in a given list"
  [nums]
  (if (empty? nums)
    (throw (ex-info "Missing single expression, not yet implemented" {}))
    ((throw (ex-info "Missing single expression, not yet implemented" {}))
                 (throw (ex-info "Missing single expression, not yet implemented" {}))
                 (throw (ex-info "Missing single expression, not yet implemented" {}))
                 )))

(defn product-by-loop-recur
  "Use loop/recur to compute the productuct (multiplicative) of the elements in a given list"
  [nums]
  (loop [acc (throw (ex-info "Missing single expression, not yet implemented" {}))
         nums (throw (ex-info "Missing single expression, not yet implemented" {}))
         ]
    (if (empty? (throw (ex-info "Missing single expression, not yet implemented" {}))
                )
      (throw (ex-info "Missing single expression, not yet implemented" {}))
      (recur (throw (ex-info "Missing single expression, not yet implemented" {}))
             (throw (ex-info "Missing single expression, not yet implemented" {}))
             ))))


(defn product-by-reduce
  "Use reduce recursion to compute the productuct (multiplicative) of the elements in a given list"
  [nums]
  (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          ))
