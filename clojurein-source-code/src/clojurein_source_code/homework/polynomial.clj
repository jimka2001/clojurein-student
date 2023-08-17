(ns clojurein-source-code.homework.polynomial
  (:require [clojurein-source-code.common.util :refer [almost-equal]]
            [clojure.pprint :refer [cl-format]]))

  
;; We represent a polynomial such as 4x^3 - 2x +4
;;    as {3 4.0,
;;        1 -1.0
;;        0 4.0}

(defn evaluate
  "given a polynomial and a numerical value  evaluate the polynomial.
  E.g.  evaluate(Map(2->2.2  0-> -1.1) 4.3))
         ==> 2.2*pow(4.3 2) - 1.1*pow(4.3 0)"
  [a x]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn plus
  "given two polynomials  add them to form a new polynomial
   e.g.  plus(Map(2->2.2  0-> -1.1) Map(3->3.3  0-> -1.1))
    ==> Map(3->3.3  2->2.2  0-> -2.2)"
  [a b]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn scale
  "create a new polynomial by multiplying a given polynomial by a given scalar (Double)"
  [s a]
  (update-vals (throw (ex-info "Missing single expression, not yet implemented" {}))
               (throw (ex-info "Missing single expression, not yet implemented" {}))
               ))


(defn subtract
  "subtract two polynomials forming a new polynomial"
  [a b]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn times
  "multiply two polynomials by multiplying two given polynomials.
  You may find this function challenging to write.  It is not
  trivial.   Remember that every term in a must be multiplied by every
  term in b.  You might find it useful to use foldLeft twice 
  but you may write the code any way you like as long as it
  obey functional programming principles   i.e.  don't modify
  variables  and don't use mutable data structures.
  It may help to use a pencil and paper to multiply polynomials
  together to notice the pattern.   Good luck!"
  [a b]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(def one
  "this is the polynomial which when multiplied by any polynomial p 
  the result is p.  I.e.  times(one p) ==> times(p one) ==> p"
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(def zero
  "this is the polynomial which when added to any polynomial p 
  the result is p.  I.e.  plus(zero p) ==> plus(p zero) ==> p"
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn power
  "raise a polynomial to a positive integer (or 0) power."
  [a p]
  (assert (>= p 0) (cl-format false "power is not and should not be implemented for p < 0: p=~A" p))
  ;; warning if pow = 100  don't try to do 99 calls to times 
  ;;   Hint x^(2n) = (x^n)^2  and x^(2n+1) = x * x^(2n)  what is x^0 ?  what is x^1 ?
  (cond
    (= p 1)
    (throw (ex-info "Missing single expression, not yet implemented" {}))

    (= p 0)
    (throw (ex-info "Missing single expression, not yet implemented" {}))

    (even? p)
    (throw (ex-info "Missing single expression, not yet implemented" {}))

    :else
    (throw (ex-info "Missing single expression, not yet implemented" {}))
    ))

(defn poly-almost-equal
  "detect whether two polynomials are close enough to qualify
  as equal   i.e.  their maximum distance of the cooef of a power is less than epsilon"
  [epsilon a b]
  (assert (float? epsilon))
  (assert (map? a))
  (assert (map? b))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

