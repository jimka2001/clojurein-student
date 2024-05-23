(ns homework.binarysearch
  )

  
;; This multi-arity function accepts either [f-step delta] or
;; [left right f-step delta max-depth].
;; In either case f-step is a unary function.
;; step accepts a double and returns a Boolean.
;; 
;; If called with [f-step delta], then bin-search-by-boolean attempts
;; to find doubles left and right to call and return
;;    (bin-search-by-boolean left right f-step delta false).
;; It does so by testing at left-=1.0, right=-1.0.  If f-step
;; returns false at left and right or returns true at left and right
;; then left and right are doubled, and step is retested.
;; left and right will continue to be doubled until values are
;; discovered for which f-step(left) != f-step(right),
;; in which case (bin-search-by-boolean left right f-step delta false)
;; is called and returned.
;; 
;; In the case of [left right f-step delta max-depth],
;; we are guaranteed right <= left,
;; bin-search-by-boolean returns a value, x, such that either f-step(x) is false and
;;   f-step(x+delta) is true, or f-step(x) is true and f-step(x+delta) is false.
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
  ([f-step delta]
   ;; the 2-argument clause computes left and right and calls the
   ;; 5-argument entry point with max-depth=false
   (loop [left -1.0
          right 1.0]
     ;; CHALLENGE: student must complete the implementation.
     (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
     ))
  ([left right f-step delta max-depth]
   (assert (<= left right))
   ;; takes a function, f, for which f(left) = false, and f(right) = true,
   ;; finds an x such that f(x)=false, and f(x+delta)= true

   (case (list (boolean (f-step left))
               (boolean (f-step right)))
     ((true true) (false false))
     false

     ((true false))
     ;; complement the function and make recursive call
     (bin-search-by-boolean left right
                            (throw (ex-info "Missing single expression, not yet implemented" {}))
                            delta max-depth)

     ((false true))
     (loop [left  left
            right right
            depth 0]
       (let [mid (throw (ex-info "Missing single expression, not yet implemented" {}))
             ]
         (cond (< (- right left) delta)
               (throw (ex-info "Missing single expression, not yet implemented" {}))

               (and max-depth  ;; check max-depth as necessary
                    (>= depth max-depth))
               (throw (ex-info "Missing single expression, not yet implemented" {}))

               (f-step mid)
               (throw (ex-info "Missing single expression, not yet implemented" {}))

               :else
               (throw (ex-info "Missing single expression, not yet implemented" {}))
               )))

     (throw (ex-info "line never reached" {})))))
