(ns clojurein-source-code.common.util)


(defn member
  "Determines whether the given target is an element of the given sequence."
  [target items]
  (boolean (cond
             (empty? items) false
             (nil? target) (some nil? items)
             (false? target) (some false? items)
             :else (reduce (fn [_acc item]
                             (if (= item target)
                               (reduced true)
                               false)) false items))))


(defn almost-equal 
  "Returns a binary function which can be used to test whether two
  given arguments are with a given tolerance of each other."
  [tolerance]
  (assert (float? tolerance))
  (fn [x y]
    (assert (or (integer? x) (float? x)))
    (assert (or (integer? y) (float? y)))
    (or (= x y)
        (<= (abs (- x y)) tolerance))))


