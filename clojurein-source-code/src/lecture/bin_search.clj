(ns lecture.bin-search)

(defn make-cubic [a b c d]
  (fn [x]
    (+ (* a x x x)
       (* b x x)
       (* c x)
       d)))

(defn make-cubic-by-roots [s r1 r2 r3]
  ;; finish this function
  )

(defn bin-search
  ([f epsilon]
   (loop [left -1.0
          right 1.0]
     {:pre [(< left right)]}
     (println [:a left right])
     (if (> (* (f left) (f right)) 0) ;; if same sign, both negative or both positive
       (let [expand (/ (- right left) 2)]
         (recur (- left expand)
                (+ right expand)))
       (bin-search left right f epsilon))))
  ([left right f epsilon]
   {:pre [(< left right)]}
   (let [mid (/ (+ left right) 2)
         fm (f mid)]
     (println [:b left right mid fm])

     (cond (< (- right left) epsilon)
           mid

           (= (f left) 0.0)
           left

           (= (f right) 0.0)
           right
           
           (> (* (f left) (f right)) 0)
           (throw (ex-info "function does not have opposite signs at endpoints"
                           {:left left
                            :right right}))
           
           (> (f left) 0)
           (recur left right
                       (fn [x] (- (f x)))
                       epsilon)
           
           (> fm 0)
           (recur left mid f epsilon)

           :else
           (recur mid right f epsilon)))))


;; (let [cu (make-cubic 1.0 2.0 -0.5 -1.0)
;;       root (bin-search cu 0.00001)]
;;   (printf "root=%s\n" root)
;;   (printf "f(root)=%s\n" (cu root)))

;; (bin-search -10.0 10.0 (fn [x] 100.0) 0.0003)

