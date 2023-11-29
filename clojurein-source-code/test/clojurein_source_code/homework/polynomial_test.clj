(ns clojurein-source-code.homework.polynomial-test
  (:require [clojurein-source-code.homework.polynomial :as sut]
            [clojurein-source-code.common.util :refer [almost-equal]]
            [clojure.pprint :refer [cl-format]]
            [clojurein-source-code.homework.util :refer [with-timeout *time-out*]]
            [clojure.test :refer [deftest is testing use-fixtures]]))

(use-fixtures :each (with-timeout 10000))

(defn random-polynomial [order]
  (reduce (fn [m exponent]
            (assoc m exponent (* (if (= 1 (rand-int 2))
                                   1 -1) (rand 1.0))))
          {}
          (random-sample 0.5 (range (inc order)))))


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

(deftest t-almost-equal
  (testing "almost-equal"
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
                                    {0 1.0, 1 2.0, 2 2.0})))))

(deftest t-evaluate
  (testing "evaluate"
    (doseq [p polynomials
            x xs]
      (sut/evaluate p x))

    (doseq [x xs]
      (is ((almost-equal  0.001)
           (sut/evaluate {2 1} x)
           (* x x))
          (cl-format false "x=~A" x)))
    (doseq [n (range 10)
            x xs]
      (is ((almost-equal 0.001)
           (sut/evaluate {n 1} x)
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
           (sut/evaluate p x))))
    (doseq [x xs]
      (is (= 1.0 (sut/evaluate sut/one x)))
      (is (= 0.0 (sut/evaluate sut/zero x))))))


(deftest t-plus
  (testing "plus"
    (doseq [p1 polynomials
            p2 polynomials]
      (sut/plus p1 p2))))
(deftest t-plus-commutative
  (testing "plus commutative"
    ;; check commutativity
    (doseq [p1 polynomials
            p2 polynomials]
      (is (sut/poly-almost-equal 0.001
           (sut/plus p1 p2)
           (sut/plus p2 p1))))))

(deftest t-plus-associative
  (testing "plus associativity"
    ;; check associativity
    (doseq [p1 polynomials
            p2 polynomials
            p3 polynomials]
      (is (sut/poly-almost-equal 0.001
           (sut/plus(sut/plus p1 p2) p3)
           (sut/plus p1 (sut/plus p2 p3)))))))

(deftest t-plus-evaluate
  (testing "plus evaluatd"
    (doseq [x xs
            p1 polynomials
            p2 polynomials
            :let [p12 (sut/plus p1 p2)
                  p1x (sut/evaluate p1 x)
                  p2x (sut/evaluate p2 x)
                  p12x (sut/evaluate p12 x)]]
      (is ((almost-equal 0.0001)
           (+ p1x p2x)
           p12x)
          (cl-format false "p1=~A p2=~A p12=~A x=~A p1x=~A p2x=~A  p12x=~A"
                     p1 p2 p12 x p1x p2x p12x)))))

(deftest t-scale
  (testing "scale"
    (doseq [p polynomials
            s '(0.0, 1.0, -1.0, 0.5, 0.25, 0.125)]
      (sut/scale s p))

    (doseq [p polynomials
            s '(0.0, 1.0, -1.0, 0.5, 0.25, 0.125)
            x xs
            :let [spx (sut/evaluate (sut/scale s p) x)
                  px  (sut/evaluate p x)]]
      (is ((almost-equal 0.0001)
           spx
           (* s px))))))

(deftest t-subtract
  (testing "subtract"
    (doseq [p1 polynomials
            p2 polynomials]
      (sut/subtract p1 p2))
    (doseq [x xs
            p1 polynomials
            p2 polynomials
            :let [p12 (sut/subtract p1 p2)
                  p1x (sut/evaluate p1 x)
                  p2x (sut/evaluate p2 x)
                  p12x (sut/evaluate p12 x)]]
      (is ((almost-equal 0.0001)
           (- p1x p2x)
           p12x)
          (cl-format false "p1=~A p2=~A p12=~A x=~A p1x=~A p2x=~A  p12x=~A"
                     p1 p2 p12 x p1x p2x p12x)))
    (doseq [p1 polynomials
            p2 polynomials
            :let [p12a (sut/subtract p1 p2)
                  p12b (sut/plus p1 (sut/scale -1.0, p2))]]
      (is (sut/poly-almost-equal 0.001
           p12a
           p12b)))))

(deftest t-times
  (testing "times"
    (doseq [p1 polynomials
            p2 polynomials]
      (sut/times p1 p2))))

(deftest t-times-commutative
  (testing "times commutative"
    ;; check commutativity
    (doseq [p1 polynomials
            p2 polynomials]
      (is (sut/poly-almost-equal 0.001
           (sut/times p1 p2)
           (sut/times p2 p1))))))


(deftest t-times-associative
  (testing "times associative"
    ;; check associativity
    (doseq [p1 polynomials
            p2 polynomials
            p3 polynomials]
      (is (sut/poly-almost-equal 0.001
           (sut/times (sut/times p1 p2)
                      p3)
           (sut/times p1
                      (sut/times p2 p3)))))))

(deftest t-times-eval
  (testing "times evaluate product"
    (doseq [x xs
            p1 polynomials
            p2 polynomials
            :let [p12  (sut/times p1 p2)
                  p1x  (sut/evaluate p1 x)
                  p2x  (sut/evaluate p2 x)
                  p12x (sut/evaluate p12 x)]]
      (is ((almost-equal 0.001)
           (* p1x p2x)
           p12x)
          (cl-format false "p1=~A p2=~A p12=~A x=~A p1x=~A p2x=~A  p12x=~A"
                     p1 p2 p12 x p1x p2x p12x)))))

(deftest t-one
  (testing "one"
    (doseq [p  polynomials]
      (is (sut/poly-almost-equal 0.0001
           p
           (sut/times sut/one p)))
      (is (sut/poly-almost-equal 0.0001
           p
           (sut/times p sut/one))))))

(deftest t-zero
  (testing "zero"
    (doseq [p polynomials]
      (is (sut/poly-almost-equal 0.0001
          p  (sut/plus sut/zero  p)))
      (is (sut/poly-almost-equal 0.0001
          p (sut/plus p sut/zero)))
      (is (sut/poly-almost-equal 0.0001
           sut/zero (sut/times sut/zero  p)))
      (is (sut/poly-almost-equal 0.0001
           sut/zero (sut/times p sut/zero))))))

(deftest t-power
  (testing "power"
    (doseq [n (range 1 20)]
      (is (sut/poly-almost-equal 0.0001
           sut/zero
           (sut/power sut/zero  n))
          (cl-format false "zero to any positive power (~A) should be zero, got ~A" n (sut/power sut/zero  n)))
      (is (sut/poly-almost-equal 0.0001
           sut/one  (sut/power sut/one  n))
          "one to any positive power should be 1"))
    (doseq [p polynomials]
      (if (not (sut/poly-almost-equal 0.0001
                p sut/zero))
        ;; cannot raise 0^0
        (is (sut/poly-almost-equal 0.0001
             sut/one
             (sut/power p  0))
            "one raised to zero should be one"))
      (is (sut/poly-almost-equal 0.0001
           p
           (sut/power p  1))
          "p raised to one should be p"))))

(deftest t-power-2
  (testing "power 2"
    (doseq [x xs
            poly polynomials
            n (range 5)
            :let [p1 (sut/power poly  n)
                  poly_x  (sut/evaluate poly  x)
                  p1x  (sut/evaluate p1  x)
                  poly_xn (Math/pow poly_x  n)]]
      (is ((almost-equal 0.0001)
           p1x
           poly_xn)
          (cl-format false "poly=~A n=~A x=~A" poly n x)))))
