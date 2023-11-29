(ns clojurein-source-code.homework.reduce
  )

  
(defn harmonic-sum
  "Compute the sum of reciprocals of the numbers in the given list;
    e.g. harmonic-sum([1  2  4  7]) =   1.0/1 + 1.0/2 + 1.0/4 + 1.0/7
    Recall that the sum of an empty list is 0 .
   The harmonic sum of an empty list is meaningless.
   Therefore we will only consider non-empty lists.
   Also it is guaranteed that no element of the input list is 0
   so you can always compute 1.0/x
   Hint, since data is not empty,  you can always take its first element  (first data)
      but (rest data) might be empty.
      Thus the 2nd argument of 3-arg reduce may be some expression of (first data)"
  [data]
  (assert (not-empty data))
  (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          (throw (ex-info "Missing single expression, not yet implemented" {}))
          ))

(defn running-sum
  "running-sum takes a sequence  of integers such as [2 4 6 8]
  or '(2 4 6 8)
  and returns a list of pairs [x y] where x comes directly from data
  and y is the sum of x and everything to the left of x in data."
  [data]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

;; We wish to define an addition and multiplication on pairs of integers
;;   according to the following rules
;;   (a b) plus (c d) = (a+c b+d)
;;   (a b) times (c d) = (a*c-b*d  b*c+a*d)"

(def plus-identity
  "the value which we can add (plus) to any (Double Double)
      and get the same value back.
       plus(plus_identity  x) == x
   and plus(x  plus_identity) == x
  If it is not clear what this value should be 
  you may look closely at the test cases;
  you should be able to reverse engineer the value of
   plus_identity by looking at the test case."
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn plus
  "e.g.  (plus [1.0 10.0] [100.0 1000.0]) ==> [101.0 1010.0]"
  [[a b] [c d]]
  [(+ a c) (+ b d)])

  
(defn times
  "e.g.  (times [3.0 5.0] [-7.0 2.0]) ==> [-31.0 -29.0]"
  [[a b] [c d]]

  [(- (* a c) (* b d))
   (+ (* b c) (* a d))])

(def times-identity
  "The value which we can multiply (times) to any (Double Double)
      and get the same value back.
      (times times-identity  x) == x
  and (times x  times-identity) == x
  If it is not clear what this value should be 
  you may look closely at the test cases;
  you should be able to reverse engineer the value of
  times_identity by looking at the test case."
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn plus-list
  "You may use your functions  plus and times  to compute the sum and product
  of exactly two pairs at a time.
  Use reduce to compute to generalize to a List of pairs of Double
  You'll need to provide a so-called zero argument for fold in both
  cases. In the first case the argument must be the identity of the
  plus function  and in the second case  you'll need the identity
  for the times function.   You need to figure out what these
  elements are?  E.g.  which pair [e1 e2] has the property that
  for all [a b]  (plus [e1 e2] [a b]) == [a b] == (plus [a b] [e1 e2]) ?"
  [data]
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

(defn times-list
  "You may use your functions  plus and times  to compute the sum and product
  of exactly two pairs at a time.
  Use reduce to compute to generalize to a List of pairs of Double
  You'll need to provide a so-called zero argument for fold in both
  cases. In the first case the argument must be the identity of the
  plus function  and in the second case  you'll need the identity
  for the times function.   You need to figure out what these
  elements are?  E.g.  which pair [e1 e2] has the property that
  for all [a b]  (times [e1 e2] [a b]) == [a b] == (times [a b] [e1 e2]) ?"
  [data]
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

