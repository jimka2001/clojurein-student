(ns clojurein-source-code.homework.binarysearch
  (:require [clojurein-source-code.common.util :refer [???]]))

  
;; This function accepts a left which is guaranteed <= right,
;; and a Boolean function which can be called on any double in the specified range.
;; bin-search-by-boolean returns a value, x, such that either f(x) is false and
;;   f(x+delta) is true, or f(x) is true and f(x+delta) is false.
;; In the case that max-depth != false, then terminate the recursive search
;;   when the specified max-depth is reached.
;; false is returned if no such x can be found.
;;
;; If you have the range i.e. values of left and right you can
;; use the 5-argument version of bin-search-by-boolean above
;; to find an x-value arbitrarily close to the value where
;; f switches from true to false (or false to true).
;; Now suppose you don't know the range.  You find find a range
;; sufficient to call the 5-argument version of bin-search-by-boolean.
;; This function DOES NOT support max-depth  if an attempt is made to
;;   search on a function which is not a step function it is OK
;;   to loop forever or throw an exception as you like.
;;   This case is NOT tested in the test cases.

(defn bin-search-by-boolean
  ([f delta]
   ;; the 2-argument clause computes left and right and calls the
   ;; 5-argument entry point with max-depth=false
   (loop [left -1.0
          right 1.0]
     ;; CHALLENGE: student must complete the implementation.
     (throw (ex-info "Not yet implemented"))
     ))
  ([left right f delta max-depth]
   (assert (<= left right))
   ;; takes a function, f, for which f(left) = false, and f(right) = true,
   ;; finds an x such that f(x)=false, and f(x+delta)= true

   (case (list (boolean (f left))
               (boolean (f right)))
     ((true true) (false false))
     false

     ((true false))
     ;; complement the function and make recursive call
     (bin-search-by-boolean left right
                            (???)
                            delta max-depth)

     ((false true))
     (loop [left  left
            right right
            depth 0]
       (let [mid (???)
             ]
         (cond (< (- right left) delta)
               (???)

               (and max-depth  ;; check max-depth as necessary
                    (>= depth max-depth))
               (???)

               (f mid)
               (???)

               :else
               (???)
               )))

     (throw (ex-info "line never reached")))))
