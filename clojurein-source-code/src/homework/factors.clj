(ns homework.factors
  (:require [clojure.math :refer [sqrt]]
            [common.util :refer [re-chunk]]))

(defn prime-factors
  "Return a vector of positive integers, each >= 2,
  representing the prime factorization of the given whole number.
  The order of the prime factors is not important.
  The product of the factors must be n, and each of the
  factors must be prime."
  [n]
  (assert (> n 0))
  (assert (integer? n))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  ;; HINT 25 line(s)
  )
