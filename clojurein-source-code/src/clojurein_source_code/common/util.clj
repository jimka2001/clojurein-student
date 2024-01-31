(ns clojurein-source-code.common.util)

(defn tails
  "Return a lazy list of tails of the given collection."
  [coll]
  (lazy-seq (if (empty? coll)
              ()
              (cons coll (tails (rest coll))))))

(defn time-call
  "Evaluates thunk and returns a 2-vector [value elapsed-time]
  where value is the return value of the thunk and elapsed-time
  is the number of nano it took evaluating the function."
  [thunk]
  (let [start (. System (nanoTime))
        value (thunk)
        stop (. System (nanoTime))]
    [value (- stop start)]))


(defn member
  "Determines whether the given target is an element of the given sequence (or given set)."
  [target items]
  (boolean (cond
             (empty? items) false
             (nil? target) (some nil? items)
             (false? target) (some false? items)
             (set? items) (contains? items target)
             :else (reduce (fn [_acc item]
                             (if (= item target)
                               (reduced true)
                               false)) false items))))


(defn almost-equal 
  "Returns a binary function which can be used to test whether two
  given arguments are within a given tolerance of each other."
  [tolerance]
  (assert (float? tolerance))
  (fn [x y]
    (assert (number? x))
    (assert (number? y))
    (or (= x y)
        (<= (abs (- x y)) tolerance))))

(defn almost-equal-seq
  "Returns a binary function which can be used to test whether two
  given sequence arguments are element-wise within a given tolerance of each other."
  [tolerance]
  (assert (float? tolerance))
  (let [ae (almost-equal tolerance)]
    (fn [x-seq y-seq]
      (and (= (count x-seq) (count y-seq))
           (every? true? (map ae x-seq y-seq))))))


(defn re-chunk 
  "Given a lazy sequence, change the chunking buffer size to n.
  This code was taken directory from
  https://clojuredocs.org/clojure.core/chunk"
  [n xs]
  (lazy-seq
    (when-let [s (seq (take n xs))]
      (let [cb (chunk-buffer n)]
        (doseq [x s] (chunk-append cb x))
        (chunk-cons (chunk cb) (re-chunk n (drop n xs)))))))

