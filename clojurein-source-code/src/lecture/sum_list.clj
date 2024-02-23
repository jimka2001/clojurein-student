(ns lecture.sum-list)


(defn sum-list-seq
  "Sequential implementation of sum-list using divide and conquer"
  [max-length data]
  (if (< (count data) max-length)
    (do
      (println data)
      (reduce + data))
    (let [mid (/ (count data) 2)
          [head tail] (split-at mid data)
          s1 (sum-list-seq max-length head)
          s2 (sum-list-seq max-length tail)]
      (+ s1
         s2))))

(defn sum-list-par
  "Parallel implementation of sum-list using divide and conquer"
  [max-length data]
  (if (< (count data) max-length)
    (do
      (println data)
      (reduce + data))
    (let [mid (/ (count data) 2)
          [head tail] (split-at mid data)
          s1 (future (sum-list-par max-length head))
          s2 (future (sum-list-par max-length tail))]
      (+ @s1
         @s2))))

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

;; (sum-list 10 (into [] (range 10000)))

