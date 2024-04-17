(ns homework.calculus
  (:require [clojure.test :refer [function?]]
            [clojure.pprint :refer [cl-format]]))

(defn integral
  "use trapezoid rule to sum the area under the curve.
  The first iteration is to divide the area into n trapezoids, then
  the second iteration divides the area into 2*n trapezoids,
  the third iteration, into 4*n---
  until the difference in area between two iterations is
  less then epsilon.
  "
  [f lower upper n epsilon]
  (assert (function? f))
  (assert (number? lower))
  (assert (number? upper))
  (assert (int? n))
  (assert (float? epsilon))
  (assert (< lower upper))
  (assert (< 0 epsilon))
  (assert (< 0 n))
  (letfn [(trap [dx left]
            ;; compute area of trapezoid with base length=dx
            ;; and whose left edge is x=left, and whose
            ;; right edge is x=left + dx
            ;; and whose height on left and right are f(left)
            ;; and f(left + dx)

            ;; CHALLENGE: student must complete the implementation.
            (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
            )
          (sum-traps [n]
            ;; divide the interval into n equal subintervals,
            ;; and sum the trapezoids on each.  I.e., sum the
            ;; areas of n-many trapezoids.
            ;; CHALLENGE: student must complete the implementation.
            (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
            )]
    (loop [prev-area (sum-traps n)
           n (* 2 n)]
      ;; CHALLENGE: student must complete the implementation.
      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
      )))

(defn derivative+
  "Compute an approximation for the slope of the given function
  as x approaches a from the right."
  [f a dx epsilon]
  (assert (function? f))
  (assert (float? a))
  (assert (float? dx))
  (assert (float? epsilon))
  (assert (< 0 epsilon))
  (assert (< 0 dx))
  
  (let [fa (f a)
        slope (fn [dx] (/ (- (f (+ a dx)) fa)
                          dx))]
    ;; CHALLENGE: student must complete the implementation.
    (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
    ))

(defn derivative-
  [f a dx epsilon]
  (assert (function? f))
  (assert (float? a))
  (assert (float? dx))
  (assert (float? epsilon))
  (assert (< 0 epsilon))
  (assert (< 0 dx))
  (- (derivative+ (fn [x]
                    (throw (ex-info "Missing single expression, not yet implemented" {}))
                    )
                  (throw (ex-info "Missing single expression, not yet implemented" {}))
                  dx epsilon)))

(defn derivative
  "returns the derivate (or nil)
  by comparing the derivative from the right (derivative+ ...)
  vs the derivative from the left (derivative- ...)
  This function calls derivative- and derivative+
  with half of epsilon as 4th argument.
  If they are within epsilon of each other, then
  their average is returned.  Otherwise nil is returned."
  [f a dx epsilon]
  (assert (function? f))
  (assert (float? a))
  (assert (float? dx))
  (assert (float? epsilon))
  (assert (< 0 epsilon))
  (assert (< 0 dx))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )
