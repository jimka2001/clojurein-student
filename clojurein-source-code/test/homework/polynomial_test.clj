(ns homework.polynomial-test
  (:require [homework.polynomial :as sut]
            [common.util :refer [almost-equal almost-equal-seq
                                 is testing-with-timeout *time-out*]]
            [clojure.pprint :refer [cl-format]]
            [clojure.test :refer [deftest]]))


(defn random-polynomial [order]
  (reduce (fn [m exponent]
            (assoc m exponent (* (if (= 1 (rand-int 2))
                                   1 -1) (rand 1.0))))
          {}
          (random-sample 0.5 (range (inc order)))))

(def polynomial-time-out (* 10 60 1000)) ;; 10 sec

(def fixed-polynomials
  [sut/one
   sut/zero
   {0 3, 1 0.1, 2 -0.5}
   {1 1.0, 2 2.0}
   {2 -2.0, 3 0.125, 1 1.0}
   {2 -2.1, 3 0.125, 1 1.0, 0 -0.5}
   {2 -2.2, 3 0.125, 1 1.0}
   {2 -2.3, 3 0.125, 1 1.0, 0 -0.25}
   {2 -2.4, 3 0.125, 1 1.0}
   {2 -2.3, 3 0.125, 1 -0.5, 0 -0.25}])
  
(def random-polynomials
  (for [_ (range 50)]
    (random-polynomial 5)))


(def polynomials (concat fixed-polynomials random-polynomials))

(def xs [1.0 0.5 0.25 -0.8 0.75])

(def epsilon 0.00001)

(deftest t-degree
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "degree"
      (is (= 2 (sut/poly-degree {1 0.1, 2 0.2})))
      (is (= 3 (sut/poly-degree {3 0.1, 2 0.2})))
      (is (= 0 (sut/poly-degree {}))))))

(deftest t-equal
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "equal"
      (is (sut/poly-equal
           {1 0.1, 2 0.2}
           {1 0.1, 2 0.2}))
      (is (sut/poly-equal
           {1 0.1, 2 0.2, 3 0.0}
           {1 0.1, 2 0.2, 4 0.0}))
      (is (sut/poly-equal
           {1 42, 2 43, 3 0}
           {1 42.0, 2 43.0, 4 0.0}))

      (is (sut/poly-equal
           {1 0.1, 2 0.2, 4 0.0}
           {1 0.1, 2 0.2, 3 0.0}))
      (is (sut/poly-equal
           {1 42.0, 2 43.0, 4 0.0}
           {1 42, 2 43, 3 0}))
      )))
                        
                        

(deftest t-almost-equal
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "almost-equal"
      (is (sut/poly-almost-equal 0.001
                                 {1 0.1, 2 0.2}
                                 {1 0.1000000001, 2 0.2}))
      (is (sut/poly-almost-equal 0.001
                                 {1 0.1, 2 0.2},
                                 {1 0.1, 2 0.20000001}))
      (is (sut/poly-almost-equal 0.001
                                 {1 0.1, 2 0.2}
                                 {1 0.1000000001, 2 0.20000001}))
      (is (not (sut/poly-almost-equal 0.00001
                                      {1 0.1}
                                      {1 0.11})))
      (is (not (sut/poly-almost-equal 0.00001
                                      {1 1.0}
                                      {1 2.0})))
      (is (not (sut/poly-almost-equal 0.00001
                                      {1 1.0, 2 2.0},
                                      {1 2.0, 2 2.0})))
      (is (not (sut/poly-almost-equal 0.00001
                                      {0 1.0, 1 1.0, 2 2.0}
                                      {0 1.0, 1 2.0, 2 2.0}))))))

(deftest t-evaluate
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "evaluate"
      (doseq [p polynomials
              x xs]
        (sut/poly-evaluate p x))

      (doseq [x xs]
        (is ((almost-equal  0.001)
             (sut/poly-evaluate {2 1} x)
             (* x x))
            (cl-format false "x=~A" x)))
      (doseq [n (range 10)
              x xs]
        (is ((almost-equal 0.001)
             (sut/poly-evaluate {n 1} x)
             (Math/pow x n))
            (cl-format false "x=~A n=~A" x n)))
      (doseq [x xs
              c1 '(0.5, 0.25)
              c2 '(0.5, 0.25)
              :let [y (+ (* c1 (Math/pow x 3))
                         (* c2 (Math/pow x 2))
                         -1.0)
                    p {3 c1, 2 c2, 0 -1.0}]]
        (is ((almost-equal 0.0001)
             y
             (sut/poly-evaluate p x))))
      (doseq [x xs]
        (is (== 1 (sut/poly-evaluate sut/one x)))
        (is (== 0 (sut/poly-evaluate sut/zero x)))))))


(deftest t-plus
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "plus"
      (doseq [p1 polynomials
              p2 polynomials]
        (sut/poly-plus p1 p2)))))


(deftest t-plus-commutative
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "plus commutative"
      ;; check commutativity
      (doseq [p1 polynomials
              p2 polynomials]
        (is (sut/poly-almost-equal 0.001
                                   (sut/poly-plus p1 p2)
                                   (sut/poly-plus p2 p1)))))))

(deftest t-plus-associative
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "plus associativity"
      ;; check associativity
      (doseq [p1 polynomials
              p2 polynomials
              p3 polynomials]
        (is (sut/poly-almost-equal 0.001
                                   (sut/poly-plus (sut/poly-plus p1 p2) p3)
                                   (sut/poly-plus p1 (sut/poly-plus p2 p3)))
            (format "Discovered non-associative input for poly-plus\np1=%s\np2=%sp3=%s"
                    p1 p2 p3))))))

(deftest t-plus-evaluate
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "plus evaluatd"
      (doseq [x xs
              p1 polynomials
              p2 polynomials
              :let [p12 (sut/poly-plus p1 p2)
                    p1x (sut/poly-evaluate p1 x)
                    p2x (sut/poly-evaluate p2 x)
                    p12x (sut/poly-evaluate p12 x)]]
        (is ((almost-equal 0.0001)
             (+ p1x p2x)
             p12x)
            (cl-format false "p1=~A p2=~A p12=~A x=~A p1x=~A p2x=~A  p12x=~A"
                       p1 p2 p12 x p1x p2x p12x))))))

(deftest t-scale
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "scale"
      (doseq [p polynomials
              s '(0.0, 1.0, -1.0, 0.5, 0.25, 0.125)]
        (sut/poly-scale s p))

      (doseq [p polynomials
              s '(0.0, 1.0, -1.0, 0.5, 0.25, 0.125)
              x xs
              :let [spx (sut/poly-evaluate (sut/poly-scale s p) x)
                    px  (sut/poly-evaluate p x)]]
        (is ((almost-equal 0.0001)
             spx
             (* s px)))))))

(deftest t-subtract
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "subtract"
      (doseq [p1 polynomials
              p2 polynomials]
        (sut/poly-subtract p1 p2))
      (doseq [x xs
              p1 polynomials
              p2 polynomials
              :let [p12 (sut/poly-subtract p1 p2)
                    p1x (sut/poly-evaluate p1 x)
                    p2x (sut/poly-evaluate p2 x)
                    p12x (sut/poly-evaluate p12 x)]]
        (is ((almost-equal 0.0001)
             (- p1x p2x)
             p12x)
            (cl-format false "p1=~A p2=~A p12=~A x=~A p1x=~A p2x=~A  p12x=~A"
                       p1 p2 p12 x p1x p2x p12x)))
      (doseq [p1 polynomials
              p2 polynomials
              :let [p12a (sut/poly-subtract p1 p2)
                    p12b (sut/poly-plus p1 (sut/poly-scale -1.0, p2))]]
        (is (sut/poly-almost-equal 0.001
                                   p12a
                                   p12b))))))

(deftest t-times
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "times"
      (doseq [p1 polynomials
              p2 polynomials]
        (sut/poly-times p1 p2)))))

(deftest t-times-commutative
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "times commutative"
      ;; check commutativity
      (doseq [p1 polynomials
              p2 polynomials]
        (is (sut/poly-almost-equal 0.001
                                   (sut/poly-times p1 p2)
                                   (sut/poly-times p2 p1)))))))


(deftest t-times-associative
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "times associative"
      ;; check associativity
      (doseq [p1 polynomials
              p2 polynomials
              p3 polynomials]
        (is (sut/poly-almost-equal 0.001
                                   (sut/poly-times (sut/poly-times p1 p2)
                                                   p3)
                                   (sut/poly-times p1
                                                   (sut/poly-times p2 p3))))))))

(deftest t-times-eval
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "times evaluate product"
      (doseq [x xs
              p1 polynomials
              p2 polynomials
              :let [p12  (sut/poly-times p1 p2)
                    p1x  (sut/poly-evaluate p1 x)
                    p2x  (sut/poly-evaluate p2 x)
                    p12x (sut/poly-evaluate p12 x)]]
        (is ((almost-equal 0.001)
             (* p1x p2x)
             p12x)
            (cl-format false "p1=~A p2=~A p12=~A x=~A p1x=~A p2x=~A  p12x=~A"
                       p1 p2 p12 x p1x p2x p12x))))))

(deftest t-one
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "one"
      (doseq [p  polynomials]
        (is (sut/poly-almost-equal 0.0001
                                   p
                                   (sut/poly-times sut/one p)))
        (is (sut/poly-almost-equal 0.0001
                                   p
                                   (sut/poly-times p sut/one)))))))

(deftest t-zero
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "zero"
      (doseq [p polynomials]
        (is (sut/poly-almost-equal 0.0001
                                   p  (sut/poly-plus sut/zero  p)))
        (is (sut/poly-almost-equal 0.0001
                                   p (sut/poly-plus p sut/zero)))
        (is (sut/poly-almost-equal 0.0001
                                   sut/zero (sut/poly-times sut/zero  p)))
        (is (sut/poly-almost-equal 0.0001
                                   sut/zero (sut/poly-times p sut/zero)))))))

(deftest t-power
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "power"
      (doseq [n (range 1 20)]
        (is (sut/poly-almost-equal 0.0001
                                   sut/zero
                                   (sut/poly-power sut/zero  n))
            (cl-format false "zero to any positive power (~A) should be zero, got ~A" n (sut/poly-power sut/zero  n)))
        (is (sut/poly-almost-equal 0.0001
                                   sut/one  (sut/poly-power sut/one  n))
            "one to any positive power should be 1"))
      (doseq [p polynomials]
        (when (not (sut/poly-almost-equal 0.0001
                                        p sut/zero))
          ;; cannot raise 0^0
          (is (sut/poly-almost-equal 0.0001
                                     sut/one
                                     (sut/poly-power p  0))
              "one raised to zero should be one"))
        (is (sut/poly-almost-equal 0.0001
                                   p
                                   (sut/poly-power p  1))
            "p raised to one should be p")))))

(deftest t-power-2
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "power 2"
      (doseq [x xs
              poly polynomials
              n (range 5)
              :let [p1 (sut/poly-power poly  n)
                    poly_x  (sut/poly-evaluate poly  x)
                    p1x  (sut/poly-evaluate p1  x)
                    poly_xn (Math/pow poly_x  n)]]
        (is ((almost-equal 0.0001)
             p1x
             poly_xn)
            (cl-format false "poly=~A n=~A x=~A" poly n x))))))

(deftest t-poly-from-roots
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "poly-from-roots"
      (is (= (sut/poly-from-roots [1])
             {1 1, 0 -1}))
      (is (= (sut/poly-from-roots [1 1])
             {2 1, 1 -2, 0 1}))
      (is (= (sut/poly-from-roots [1 1 1])
             {3 1, 2 -3, 1 3, 0 -1}))
      )))
                        
(deftest t-canonicalize
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial canonicalize"
      (is (= {} (sut/canonicalize {3 0})))
      (is (= {} (sut/canonicalize {3 0.0})))
      (is (= {} (sut/canonicalize {3 0.0, 2 0})))
      (is (= {1 42} (sut/canonicalize {3 0.0, 2 0, 1 42})))
      (is (= {1 42, 5 33} (sut/canonicalize {5 33, 3 0.0, 2 0, 1 42}))))))


(deftest t-divide
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial divide"
      (let [[q r] (sut/poly-divide (sut/poly-from-roots [1 2 3])
                                   (sut/poly-from-roots [1 2]))]
        (is (= (sut/poly-from-roots [3]) q))
        (is (= sut/zero r)))
      (is (= (sut/poly-divide (sut/poly-from-roots [1 2 3 -4])
                              (sut/poly-from-roots [1 2]))
             [(sut/poly-from-roots [3 -4])
              sut/zero])))))

(deftest t-divide-a
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial divide"
      (doseq [a (range 1 10)
              b (range 1 10)]
        (let [[q r] (sut/poly-divide (sut/poly-from-roots [a b])
                                     (sut/poly-from-roots [a b]))]
          (is (= sut/one q))
          (is (= sut/zero r)))))))

(deftest t-divide-b
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial divide"
      (doseq [a (range 1 2)
              b (range 1 2)]
        (let [[q r] (sut/poly-divide (sut/poly-from-roots [a b])
                                     (sut/poly-from-roots [a]))]
          (is (= (sut/poly-from-roots [b]) q))
          (is (= sut/zero r)))))))


(deftest t-divide-c
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial divide"
      (doseq [a (range 1 2)
              b (range 1 2)
              c (range 1 2)]
        (let [[q r] (sut/poly-divide (sut/poly-from-roots [a b c])
                                     (sut/poly-from-roots [a b]))]
          (is (= (sut/poly-from-roots [c]) q))
          (is (= sut/zero r)))))))

(deftest t-derivative
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial derivative"
      (doseq [p random-polynomials
              :let [d (sut/poly-derivative p)]]
        (if (zero? (sut/poly-degree p))
          (is (sut/poly-equal sut/zero d))
          (is (= (- (sut/poly-degree p) 1)
                 (sut/poly-degree (sut/poly-derivative p))))))

      ;; TODO add more tests for derivative
      )))

(deftest t-quadratic
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "quadratic formula"
      ;; TODO add more tests 
      )))

(deftest t-poly-roots-by-inflection-points
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "poly-roots-by-inflection-points"
      ;; TODO add more tests 
      )))


(deftest t-roots-1
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots"
      (let [ae (almost-equal-seq 0.001)]
        (doseq [a (range -10 10)]
          (let [p (sut/poly-from-roots [a])
                rs (sut/poly-roots p epsilon)]
            (is (ae [a] rs)
                (cl-format false "a=~A rs=~A" a rs))))))))

(deftest t-roots-2
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots"
      (let [ae (almost-equal-seq 0.001)]
        ;; degree 2
        (doseq [a (range -10 10)
                b (range -10 10)]
          (let [p (sut/poly-from-roots [a b])
                rs (sut/poly-roots p epsilon)]
            (is (ae (sort [a b])
                    rs)
                (cl-format false "a=~A b=~A rs=~A" a b rs))))))))

(deftest t-roots-3a
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots-a"
      ;; degree 3
      (let [ae (almost-equal-seq 0.001)
            control [-10 -10 -9]
            p (sut/poly-from-roots control)
            rs (sut/poly-roots p epsilon)]
        (is (ae (sort control)
                rs)
            (cl-format false "expecting=~A got rs=~A" control rs))))))

(deftest t-roots-3b
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots-b"
      ;; degree 3
      (let [ae (almost-equal-seq 0.001)
            control [-9 0 9]
            p (sut/poly-from-roots control)
            rs (sut/poly-roots p epsilon)]
        (is (ae (sort control)
                rs)
            (cl-format false "expecting=~A got rs=~A" control rs))))))

(deftest t-roots-3
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots"
      ;; degree 3
      (let [ae (almost-equal-seq 0.001)]
        (doseq [a (range -10 10)
                b (range (inc a) 10)
                c (range (inc b) 10)]
          (let [control [a b c]
                p (sut/poly-from-roots control)
                rs (sut/poly-roots p epsilon)]
            (is (ae (sort control)
                    rs)
                (cl-format false "expecting=~A got rs=~A" control rs))))))))


(deftest t-roots-4
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots"
      (let [ae (almost-equal-seq 0.001)]
        ;; degree 4
        (doseq [a (range -10 10 2)
                b (range (inc a) 10 2)
                c (range (inc b) 10 3)
                d (range (inc c) 10 5)]
          (let [control [a b c d]
                p (sut/poly-from-roots control)
                rs (sut/poly-roots p epsilon)]
            (is (ae (sort control)
                    rs)
                (cl-format false "expecting=~A got rs=~A" control rs))))))))


(deftest t-roots-5
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots"
      ;; degree 5
      (let [ae (almost-equal 0.01)]
        (doseq [a (range -10 10 2)
                b (range (inc a) 10 2)
                c (range (inc b) 10 3)
                d (range (inc c) 10 5)
                ]
          (let [control [a b c d (- d)]
                p (sut/poly-from-roots control)
                rs (sut/poly-roots p (/ epsilon 10))]
            (doseq [r rs]
              (is (some (fn [x]
                          (ae r x)) control)
                  (cl-format false "~A missing in ~A" r control)))))))))

(deftest t-roots-6
  (binding [*time-out* polynomial-time-out]
    (testing-with-timeout "polynomial roots"
      ;; degree 6
      (let [ae (almost-equal 0.01)]
        (doseq [a (range -10 10 2)
                b (range (inc a) 10 2)
                c (range (inc b) 10 3)
                d (range (inc c) 10 5)
                ]
          (let [control [a b (- b) c (- d) d]
                p (sut/poly-from-roots control)
                rs (sut/poly-roots p (/ epsilon 10))]
            (doseq [r rs]
              (is (some (fn [x]
                          (ae r x)) control)
                  (cl-format false "~A missing in ~A" r control)))))
        ))))
