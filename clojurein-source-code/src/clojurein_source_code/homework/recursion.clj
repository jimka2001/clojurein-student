(ns clojurein-source-code.homework.recursion
  )


(defn sum-by-simple-recursion
  ""
  [nums]
  (if (empty? nums)
    0
    (+ (throw (ex-info "Missing single expression, not yet implemented" {}))
       (sum-by-simple-recursion (throw (ex-info "Missing single expression, not yet implemented" {}))
                                ))))

(defn sum-by-loop-recur
  ""
  [nums]
  (loop [acc 0
         nums nums]
    (if (empty? nums)
      (throw (ex-info "Missing single expression, not yet implemented" {}))
      (recur (throw (ex-info "Missing single expression, not yet implemented" {}))
             (throw (ex-info "Missing single expression, not yet implemented" {}))
             ))))


(defn sum-by-reduce
  ""
  [nums]
  (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          ))

(defn product-by-simple-recursion
  ""
  [nums]
  (if (empty? nums)
    (throw (ex-info "Missing single expression, not yet implemented" {}))
    ((throw (ex-info "Missing single expression, not yet implemented" {}))
                 (throw (ex-info "Missing single expression, not yet implemented" {}))
                 (throw (ex-info "Missing single expression, not yet implemented" {}))
                 )))

(defn product-by-loop-recur
  ""
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
  ""
  [nums]
  (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          ))
