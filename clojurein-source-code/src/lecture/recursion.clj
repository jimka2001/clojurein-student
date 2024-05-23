(ns lecture.recursion)

(defn sum-list-sr [nums]
  ;; (assert (list? nums))
  {:pre [(list? nums)]}
  (if (empty? nums)
    0
    (+ (first nums)
       (sum-list-sr (rest nums)))))

(defn sum-vector-sr
  ([nums]
   (assert (vector? nums))
   (sum-vector-sr nums 0))
  ([nums start]
   (assert (vector? nums))
   (if (= start (count nums))
     0
     (+ (nth nums start)
        (sum-vector-sr nums (inc start))))))


(defn sum-list-tr 
  ([nums]
   (assert (list? nums))
   (sum-list-tr nums 0))
  ([nums acc]
   (assert (list? nums))
   (if (empty? nums)
     acc
     (recur (rest nums)
            (+ acc (first nums))))))


(defn sum-vector-tr
  ([nums]
   (assert (vector? nums))
   (sum-vector-tr nums 0 0))
  ([nums start acc]
   (if (= start (count nums))
     acc
     (recur nums (inc start) (nth nums start )))))

(defn sum-reduce
  [nums]
  (reduce + 0 nums))

