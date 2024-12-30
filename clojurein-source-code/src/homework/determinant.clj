(ns homework.determinant)

;; This exercise is incomplete.


(defn supress_rc 
  "Given a function, f (as returned from mat-to-function or from supress_rc).
  Assuming f can be called with indices 0 <= r < n and 0 <= c < n (for some n).
  Return a new function which can be called on 0 <= r < n-1 and 0 <= c < n-1,
  which will skip the row and column specified omit-r and omit-c"
  [f omit-r omit-c]
  (fn [r0 c0]
    ;; CHALLENGE: student must complete the implementation.
    (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
    ;; HINT 2 line(s)
    ))

(defn mat-to-function
  "Convert a matrix, i.e. a vector of vectors of numbers into a function
  which can be called with the row and column index (zero-based) to extract
  the entry from the matrix."
  [mat]
  (assert (vector? mat))
  (assert (< 0 (count mat)) (format "%s expected to have size > 0" mat))
  (assert (every? vector? mat)) ;; is mat a vector of vectors
  (assert (every? (fn [row] ;; all of the same length, ie a square matrix
                    (= (count mat) (count row))) mat))
  (fn [r0 c0]
    ;; CHALLENGE: student must complete the implementation.
    (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
    ;; HINT 1 line(s)
    ))

(defn determinant
  "Compute the determinant of a square matrix (dim >= 1)
  using the Laplacian expansion.  This has n! complexity."
  [mat]
  (letfn [(det [dim f]
            (case dim
              (1) (f 0 0)
              (2) (- ;; CHALLENGE: student must complete the implementation.
              (2) (- (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
              (2) (- ;; HINT 2 line(s)
                   )
              (reduce (fn [acc k]
                        ;; CHALLENGE: student must complete the implementation.
                        (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
                        ;; HINT 7 line(s)
                        )
                      0
                      (range dim))))]
    (det (count mat)
         (mat-to-function mat))))


