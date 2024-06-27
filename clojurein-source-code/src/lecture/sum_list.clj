
(ns lecture.sum-list)

(defn fractions 
  "return a randomized list of m-many rational numbers summing to 0"
  [m] 
  (shuffle (for [k (range m)
                 :let [n (inc (rand-int 100))
                       d (inc (rand-int 100))]
                 a [n (- n)]]
             (/ a d))))

(def some-data (fractions 1000))


(defn sum-list-seq
  "Sequential implementation of sum-list using divide and conquer"
  [max-length data]
  (if (< (count data) max-length)
    (do
      (println data)
      (reduce + data))
    (let [mid (quot (count data) 2)
          [head tail] (split-at mid data)
          s1 (sum-list-seq max-length head)
          s2 (sum-list-seq max-length tail)]
      (+ s1
         s2))))


;;(sum-list-seq 20 some-data)

(defn sum-list-par
  "Parallel implementation of sum-list using divide and conquer"
  [max-length data]
  (if (< (count data) max-length)
    (do
      (println data)
      (reduce + data))
    (let [mid (quot (count data) 2)
          [head tail] (split-at mid data)
          s1 (future (sum-list-par max-length head))
          s2 (future (sum-list-par max-length tail))]
      (+ @s1
         @s2))))

(sum-list-par 20 some-data)


(defn sum-list-par-monoid
  "Parallel implementation of sum-list using divide and conquer"
  [data op ident]
  (cond
    (empty? data)
    ident

    (= 1 (bounded-count 2 data))
    (do
      (println [:singleton (first data)])
      (first data))

    :else
    (do
      (println [:summing (count data)
                :data data])
      (let [mid (/ (count data) 2)
            [head tail] (split-at mid data)
            s1 (future (sum-list-par-monoid head op ident))
            s2 (future (sum-list-par-monoid tail op ident))]
        (op @s1
            @s2)))))


(defn add-in-n-threads [n col]
  (let [m (quot (count col) n)
        future-sums (doall (for [nums (partition-all m col)]
                              (future (reduce + nums))))]
    (reduce (fn [acc f] (+ acc @f)) 
            0 future-sums)))

;;(let [numbers (fractions 1000000)] 
;;  (time (reduce + numbers))
;;  (time (add-in-n-threads 5 numbers)))

;; (partition-all 4 (range 21))



