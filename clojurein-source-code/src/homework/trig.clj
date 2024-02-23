(ns homework.trig
  )

  
(def n-terms
  "The number of terms of each series which are added.
   You may need to increase this number to make the tests pass
   the larger nTerms, the slower the computation, but the more
   accurate the results. Suggestion, start with 5 and increase it until the
   tests pass."
  20)


(defn exp
  "
              1    x^1   x^2   x^3
    exp(x) = --- + --- + --- + --- + ...
              0!    1!    2!    3!

   To add this series we take advantage of the fact that term T(n+1) = T(n) * (x/n).
    Thus we avoid explicitly calculating x^n and n!, because we always start with x^(n-1)/(n-1)!.
    The body of the foldLeft, does not simply calculate the sum, but rather calculates
    a pair (sum, nextTerm),  with nextTerm = term * x/n

  This implementation contains an optimization.  If |x| > 1, then we divide by 2.0
  to compute exp(x/2.0) then square the result.
  "
  [x]
  (if (> (abs x) 1)
    (let [ex (exp (/ x 2.0))]
      (throw (ex-info "Missing single expression, not yet implemented" {}))
      )
    ;; we know |x| <= 1
    (let [t0 1.0]
      (first
       (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
               (throw (ex-info "Missing single expression, not yet implemented" {}))
               (range 1 n-terms))))))

(defn cos
  "
             1    x^2   x^4   x^6
   cos(x) = --- - --- + --- - --- + ...
             0!    2!    4!    6!
   Follow the pattern of 'def exp' above to express the cosine computation
   in terms of foldLeft.  The difference is that you need to
    1) make sure the calculated term alternates in sign from one iteration
         to the next
    2) the iterator feeding into foldLeft is not (1 to nTerms) but rather
         a different start and end point, and a step by 2
    3) given one term in the sum, what must you multiply it by
         to obtain the next?  It is no longer x/n as before.
    4) the iteration variable should traverse the even integers, (0, 2, 4 ...)

  This implementation contains an optimization.  If x < 0 then we compute cos(-x),
  and if x > 2pi, then we we compute cos(x - (2 n pi)) for some n which forces
  the argument of cos between 0 and 2pi.
  "
  [x]
  (let [pi Math/PI
        periods (Math/floor (/ x (* 2 pi)))]
    (cond (< x 0.0)
          (throw (ex-info "Missing single expression, not yet implemented" {}))

          (> periods 0)
          (cos (throw (ex-info "Missing single expression, not yet implemented" {}))
               )

          :else
          ;; we know 0 <= x <= 2pi
          (let [t0 1.0
                x2 (* x x)]
            (first
             (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
                     (throw (ex-info "Missing single expression, not yet implemented" {}))
                     (range 2
                            (throw (ex-info "Missing single expression, not yet implemented" {}))
                            2)))))))


(defn sin
  "
              x^1   x^3   x^5   x^7
     sin(x) = --- - --- + --- - --- + ...
               1!    3!    5!    7!
   Follow the pattern of exp and cos above.   This can be done with the
   same code inside the 2nd argument of foldLeft, but different first argument,
   indicating the initial value of the sum.  Notice that the cos series
   starts at 1, whereas the sin series starts at x.
   Also be careful that the iteration variable must traverse the odd integers
   rather than the even integers as before.

  This implementation contains an optimization.  If x < 0 then we compute -sin(-x),
  and if x > 2pi, then we we compute sin(x - (2 n pi)) for some n which forces
  the argument of sin between 0 and 2pi."
  [x]
  (let [pi Math/PI
        periods (Math/floor (/ x (* 2 pi)))]
    (cond (< x 0)
          (throw (ex-info "Missing single expression, not yet implemented" {}))

          (> periods 0)
          (throw (ex-info "Missing single expression, not yet implemented" {}))

          :else
          ;; we know 0 <= x <= 2pi
          (let [t0 x
                x2 (* x x)]
            (first (reduce (throw (ex-info "Missing single expression, not yet implemented" {}))
                           (throw (ex-info "Missing single expression, not yet implemented" {}))
                           (range (throw (ex-info "Missing single expression, not yet implemented" {}))
                                  (throw (ex-info "Missing single expression, not yet implemented" {}))
                                  (throw (ex-info "Missing single expression, not yet implemented" {}))
                                  )
                           ))))))

