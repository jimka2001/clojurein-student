(ns lecture.bin-search)

(defn make-cubic [a b c d]
  (fn [x]
    (+ (* a x x x)
       (* b x x)
       (* c x)
       d)))

(defn bin-search
  ([f epsilon]
   (loop [left -1.0
          right 1.0]
     (println [:a left right])
     (if (> (* (f left) (f right)) 0)
       (recur (* 2 left)
              (* 2 right))
       (bin-search left right f epsilon))))
  ([left right f epsilon]
   (let [mid (/ (+ left right) 2)
         fm (f mid)]
     (println [:b left right mid fm])

     (cond (< (- right left) epsilon)
           left
           
           (> (* (f left) (f right)) 0)
           nil
           
           (> (f left) 0)
           (bin-search left right
                       (fn [x] (- (f x)))
                       epsilon)
           
           (> fm 0)
           (bin-search left mid f epsilon)

           :else
           (bin-search mid right f epsilon)))))

        

(let [p1 (make-cubic 1.0 2.0 -0.5 -1.0)
      p2 (make-cubic -1.0 2.0 -1.5 -1.0)]
  (bin-search p1 0.001))
