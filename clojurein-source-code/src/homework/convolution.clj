(ns homework.convolution
  (:require [clojure.test :refer [function?]]))

(defn integral
  "use trapezoid rule to sum the area under the curve.
  The first iteration is to divide the area trapezoids, then
  the second iteration divides the area into 4 trapezoids,
  the third iteration, into 8---
  until the difference in area between two iterations is
  less then epsilon.
  "
  [f lower upper epsilon]
  (letfn [(trap [dx left]
            ;; compute area of trapezoid with base length=dx
            ;; and whose left edge is x=left, and whose
            ;; right edge is x=left + dx
            ;; and whose height on left and right are f(left)
            ;; and f(left + dx)

            ;; CHALLENGE: student must complete the implementation.
            (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
            )]
    (let [total-x (- upper lower)]
      (loop [n 2
             prev-area (trap (- upper lower) lower)]
        ;; CHALLENGE: student must complete the implementation.
        (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
        ))))

(defn clip-port
  "Given a unary function, f, and a boundary defined by lower and upper,
   returns a unary function derived from f, which returns the value of f(x)
   if x is strictly between upper and lower.  However if x is on the boundary
   our outside the retion (lower, upper) returns 0"
  [f lower upper]
  (assert (< lower upper))
  (assert (function? f))
  (fn [x]
    (cond (<= x lower)
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          
          (>= x upper)
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          
          :else
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          ))
  )

(defn convolution
  "Compute and return the convolution,
  which is a binary function, of x and epsilon.  Evaluating this function
  at x computes the integral from lower to upper of f(t)f(x-t).
  This function enforces that the function, f,
  has value 0 outside the interval [lower upper]"
  [f g lower upper]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )
  
