(ns clojurein-source-code.homework.polynomial
  (:require [clojurein-source-code.common.util :refer [almost-equal]]
            [clojure.math :refer [sqrt]]
            [clojure.pprint :refer [cl-format]]))

(declare poly-roots)
  
;; In the following exercises, we represent a polynomial such as 4x^3 - 2x +4
;;    as a Map (hash table) which maps a exponents to coefficients.
;;    An exponent is always a non-negative integer, and a coeffient
;;    may be an integer or a double such as the following.
;;       {3 4.0,
;;        1 -1.0
;;        0 4.0}
;; Two polynomial-hash-maps are considered if the corresponding
;; polynomials are equal.  For example the following two should
;; be considered equal because they only differ by exponents
;; whose coeffients are 0.0
;;       {3 4.0,
;;        1 -1.0
;;        0 4.0
;;        4 0.0}
;; and
;;       {3 4.0,
;;        2 0.0
;;        1 -1.0
;;        0 4.0}

(defn canonicalize
  "remove exponent/coef pairs if coef is zeo"
  [kvs]
  (reduce-kv (fn [acc k v]
               (if (zero? v)
                 acc
                 (assoc acc k v)))
             {}
             kvs))

(def one
  "this is the polynomial which when multiplied by any polynomial p 
  the result is p.  I.e.  (poly-times one p) ==> (poly-times p one) ==> p"
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(def zero
  "this is the polynomial which when added to any polynomial p 
  the result is p.  I.e.  (poly-plus zero p) ==> (poly-plus p zero) ==> p"
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn make-polynomial
  "Given a sequence of coefficients [c0 c1 c2 ... cn] representing
  c0 x^0 + c1 x^1 + c2 x^2 + ... + cn x^n,
  return the hashmap representing the polygon"
  [coefs]
  (canonicalize (reduce (fn [acc [e c]]
                          (assoc acc e c)) {} (map-indexed vector coefs))))

(defn poly-coef 
  "Return the coeficient of the given exponent in the given polynomial.
  If the exponent is not explicitly given in the hash-map, then the
  coeficient should be understood to be 0"
  [poly exponent]
  (get poly exponent 0))

(defn poly-evaluate
  "given a polynomial and a numerical value  evaluate the polynomial.
  E.g.  (poly-evaluate {2 2.2, 0, -1.1) 4.3))
         ==> 2.2*pow(4.3 2) - 1.1*pow(4.3 0)"
  [a x]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn to-function
  "Return a function representing the polynomial which can
  be called with a given x to compute f(x) for polynomial f"
  [a]
  (fn [x]
    (poly-evaluate a x)))

(defn poly-degree
  "Return a positive integer indicating the largest power of
  x in the polynomial.  terms with a zero coefficient are
  not considered for computing the poly-degree.
  However, a constant polynomial has degree 0."
  [a]
  (reduce-kv (fn [acc  exponent coef]
               (if (zero? coef)
                 acc
                 (max exponent acc)))
             0 
             a))

(defn poly-plus
  "given two polynomials  add them to form a new polynomial
   e.g.  (poly-plus {2 2.2,  0 -1.1}
                    {3 3.3, 0 -1.1})
    ==> {3 3.3,  2 2.2,   0 -2.2}"
  [a b]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn poly-scale
  "create a new polynomial by multiplying a given polynomial by a given scalar (Double)"
  [s a]
  (update-vals (throw (ex-info "Missing single expression, not yet implemented" {}))
               (throw (ex-info "Missing single expression, not yet implemented" {}))
               ))


(defn poly-subtract
  "subtract two polynomials forming a new polynomial"
  [a b]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn poly-times
  "multiply two polynomials.
  You may find this function challenging to write.  It is not
  trivial.   Remember that every term in a must be multiplied by every
  term in b."
  [a b]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn poly-from-roots
  "given a sequence of roots [r0 r1 r2 ...], return the
  polynomial equal to (x - r0)(x - r1)(x - r2) ..."
  [roots]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn poly-power
  "raise a polynomial to a positive integer (or 0) power."
  [a p]
  (assert (>= p 0) (cl-format false "poly-power is not and should not be implemented for p < 0: p=~A" p))
  ;; warning if pow = 100  don't try to do 99 calls to poly-times 
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

(defn poly-equal
  "detect whether to polynomials are exactly numerically equal.
  A polynomial with an integer coeficient such as 3 should be
  considered equal to a polynomial with a floating point
  coeficient such as 3.0"
  [a b]
  (assert (map? a))
  (assert (map? b))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )


(defn poly-divide
  "Divide two polynomials returning a quotient and remainder"
  [numerator denominator]
  ;; (print [:numerator numerator          :denominator denominator])
  (assert (map? numerator))
  (assert (map? denominator))
  (assert (not (poly-equal zero denominator))
          (cl-format nil "cannot divide by zero polynomial, numerator=~A"
                     numerator))
  (let [dd (poly-degree denominator)]; denominator degree
    (loop [acc zero
           numerator numerator]

      (let [nd (poly-degree numerator)] ; numerator degree
        (cond
          ;; e.g    2x^2 - x + 4
          ;;    --------------------
          ;;     3x^9 - x^3 + x + 1 
          (< nd dd)
          [(canonicalize acc)
           (canonicalize numerator)]
          
          ;; e.g 2
          ;;    ---
          ;;     3
          (= 0 nd dd)
          [(canonicalize (poly-plus acc
                               {0 (/ (poly-coef numerator 0)
                                     (poly-coef denominator 0))}))
           zero]

          ;; e.g 3x^9 - x^3 + x + 1
          ;;    --------------------
          ;;        2x^2 - x + 4
          :else
          (let [qd (- nd dd)  ; degree of quotient
                leading-term {qd (/ (poly-coef numerator nd)
                                    (poly-coef denominator dd))}]
            (recur (poly-plus acc leading-term)
                   ;; we force the qd term to zero in
                   ;; case there was round-off error.
                   (dissoc (poly-subtract numerator
                                     (poly-times leading-term denominator))
                           nd))))))))

(defn poly-derivative [poly]
  ;; ax^3 + bx^2 + cx + d = 0 --> 3 a x^2 + 2 b x + c
  (reduce-kv (fn [acc e c]
               (if (zero? e)
                 acc
                 (assoc acc (dec e) (* e c))))
             {}
             poly))

(defn find-root-by-binary-search
  ([poly left right epsilon]
   (let [mid (/ (+ right left) 2)]
     (if (< (abs (- right left)) epsilon)
       mid
       (let [f-left (poly-evaluate poly left)
             f-right (poly-evaluate poly right)
             f-mid (poly-evaluate poly mid)]
         (cond (<= (* f-left f-mid) 0)
               (recur poly left mid epsilon)

               :else
               (recur poly right left epsilon))))))

  ([poly epsilon]
   (loop [left -1.0
          right 1.0]
     (let [f-left (poly-evaluate poly left)
           f-right (poly-evaluate poly right)]
       (cond (<= (* f-left f-right) 0)
             (find-root-by-binary-search poly left right epsilon)
             :else
             (recur (* 2 left) (* 2 right)))))))


(defn poly-roots-by-inflection-points
  "Try to find the roots of the derivative polynomial.
  If more than one root is found, then between every two
  consecutive such inflection points, test the value of the
  original polynomial.  If the polynomial changes sign between
  these two inflection points, then find a root using binary
  search.  Return a list of such roots, in increasing order."

  [poly epsilon]
  (loop [inflection-points (poly-roots (poly-derivative poly) (/ epsilon 10))
         n (count inflection-points)
         rs []]
    ;; if there is more than one inflection point, and the polynomial
    ;; has opposite sign at two adjacent inflection points, then
    ;; use find-root-by-binary-search to find a root.
    (cond (> n 1)
          (let [if1 (first inflection-points)
                if2 (second inflection-points)
                f-left (poly-evaluate poly if1)
                f-right (poly-evaluate poly if2)]
            (if (<= (* f-left f-right) 0)
              (recur (rest inflection-points)
                     (dec n)
                     (conj rs (find-root-by-binary-search poly if1 if2 epsilon)))
              (recur (rest inflection-points)
                     (dec n)
                     rs)))

          :else
          (sort rs))))

(defn quadratic-formula [a b c epsilon]
  ;; The discriminant is b^2 - 4ac
  ;; If the discriminant is close to zero (within epsilon)
  ;;    then a repeated root should be returned.
  ;; If the descriminant is negative (and not within epsilon of zero)
  ;;    no roots (empty vector []) is returned.
  ;; If the descriminant is positive (and not within epsilon of zero)
  ;;    then use the quadratic formula to compute the two roots
  ;;    and return [r1 r2] 
  (let [descr (- (* b b) (* 4 a c))]
    (cond (< (abs descr) epsilon)
          ;; -b
          ;; ---
          ;;  2a
          (into [] (repeat 2 (/ (- b)
                                (* 2 a))))

          (< descr 0)
          []

          :else
          ;; -b +/- sqrt(descr)
          ;; -----------------
          ;;      2a
          (sort [(/ (+ (- b) (sqrt descr))
                    (* 2 a))
                 (/ (- (- b) (sqrt descr))
                    (* 2 a))]))))

(defn poly-roots [poly epsilon]
  (sort 
   (cond (== 0 (poly-degree poly))
         []
         
         ;; ax^3 + bx^2 + cx + 0, has 0 root, so divide by x and find remaining roots
         (and (>= (poly-degree poly) 1)
              (== 0 (poly-coef poly 0)))
         (let [[q r] (poly-divide poly {1 1})]
           (conj (poly-roots q epsilon)
                 0))

         ;; ax + b = 0 --> x = -b/a
         (== 1 (poly-degree poly))
         [(/ (- (poly-coef poly 0))
             (poly-coef poly 1))]

         ;; ax^2 + bx + c = 0 --> quadratic formula
         (== 2 (poly-degree poly))
         (quadratic-formula (poly-coef poly 2) (poly-coef poly 1) (poly-coef poly 0) epsilon)

         ;; ax^3 + bx^2 + cx + d = 0 --> binary search
         ;; Such a polynomial is guaranteed to have a root.
         ;; Use the function find-root-by-binary-search with epsilon/10
         ;; to find it (call it r1), then use poly-divide to divide out the root
         ;; leaving a new polynomial whose degree is decreased by one.
         ;; Find the new polynomial's roots with a recursive call.
         ;; return a vector of r1 with the roots found recursively
         (odd? (poly-degree poly))
         (let [root (find-root-by-binary-search poly (/ epsilon 10))
               [order-2 rem] (poly-divide poly (poly-from-roots [root]))]
           (conj (poly-roots order-2 epsilon)
                 root))

         :else
         ;; Now we know the degree is even, so use
         ;; poly-roots-by-inflection-points to find 0 or more roots.
         ;; If some roots are found, then divide them out using
         ;; poly-divide and poly-from-roots, which gives a new
         ;; polynomial of smaller degree.  Use poly-roots to find its
         ;; roots and then append the two vectors of roots together.
         (let [rs (poly-roots-by-inflection-points poly epsilon)]
           (if (empty? rs)
             [] ;; unable to find any roots of even degree polynomial
             (let [[less-order rem] (poly-divide poly (poly-from-roots rs))]
               (concat rs
                       (poly-roots less-order epsilon))))))))

;; (poly-roots (poly-from-roots [2 -3 1 5 1 0 0 0 0 0 0])  0.0000001)
