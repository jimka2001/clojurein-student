(ns clojurein-source-code.homework.runaverage
  )

  
(defn run-average
  "
  run-average returns a value indicating the running average of a given
  list of floating-point numbers.  The average of how many numbers is determined
  by the given run-length.   E.g., if run-ength==3, then we want to calculate
  successive averages of 3 consecutive numbers.
  run-average returns a sequence of pairs.
  The first component of each pair is the given number, i.e., the corresponding element of
  samples.  The second component of each pair is the average of 3 (or run-length)
  many elements of samples.
  There is an exception for the first run-length - 1 elements.  In this case
  you don't have run-length number of elements to average, so find the average
  of all the elements so far.
  Example, suppose samples = [1.0 2.0 6.0 4.0 2.4]
           and     run-length = 3
    this function should return a sequence equivalent to
    [[1.0  a1] [2.0  a2] [6.0  a3] [4.0 a4]  [2.4 a5]]
    where a1 = 1.0 / 1
          a2 = (1.0 + 2.0) / 2
          a3 = (1.0 + 2.0 + 6.0) / 3
          a4 = (2.0 + 6.0 + 4.0) / 3
          a5 = (6.0 + 4.0 + 2.4) / 3
  "
  [run-length samples]
  (loop [samples (seq samples)
         history () ;; history is ([sample average] ...)
         ]
    (if (empty? samples)
      (reverse history)
      ;; CHALLENGE: student must complete the implementation.
      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
      )))
