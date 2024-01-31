(ns clojurein-source-code.homework.trig-test
  (:require [clojurein-source-code.homework.trig :as sut]
            [clojurein-source-code.lecture.util :refer [testing-with-timeout *time-out*]]
            [clojurein-source-code.common.util :refer [almost-equal]]
            [clojure.test :refer [deftest is]]))



(deftest t-cos
  (testing-with-timeout "cos"
    (is (< (abs (- (sut/cos 0.0) 1.0)) 0.0001))
    (doseq [x [0.1  0.2  -0.5  (/ Math/PI  2)]]
      (is ((almost-equal 0.001) (Math/cos x) (sut/cos x))))))

(deftest t-cos-range
  (testing-with-timeout "cos range"
    (doseq [periods (range -20 20)
            x (range 0 100)
            :let [theta (/ (* x 2 Math/PI) 100)]]
      (is ((almost-equal 0.001)
           (sut/cos theta)
           (sut/cos (+ theta (* periods 2 Math/PI))))))))


(deftest t-sin
  (testing-with-timeout "sin"
    (is (< (abs (sut/sin 0.0)) 0.0001))
    (doseq [x [0.1  0.2  -0.5  (/ Math/PI 2)]]
      (is ((almost-equal 0.001)
           (sut/sin x)
           (Math/sin x))))))

(deftest t-sin-range
  (testing-with-timeout "sin range"
    (doseq [periods (range -20 20)
            x (range 0 100)
            :let [theta (/ (* x 2 Math/PI) 100)]]
      (is ((almost-equal 0.001)
           (sut/sin theta)
           (sut/sin (+ theta (* periods 2 Math/PI))))))))

(deftest t-pythagoras
  (testing-with-timeout "pythagoras"
    (doseq [x [0.1  0.2  -0.5  (/ Math/PI 2)]
            :let [c (sut/cos x)
                  s (sut/sin x)]]
      (is ((almost-equal 0.0001)
           1.0
           (+ (* c c) (* s s)))))))

(deftest t-exp
  (testing-with-timeout "exp"
    (is ((almost-equal 0.001)
         1.0 (sut/exp 0.0)))
    (doseq [scale (range 1 20)]
      (is ((almost-equal 0.001)
           (Math/exp (* scale 1.0))
           (sut/exp (* scale 1.0)))))))

(deftest t-exp-shifted
  (testing-with-timeout "exp shifted"
    (doseq [x [0.1  0.2  -0.5  (/ Math/PI 2)]]
      ;; e^x * e^-x = e^0 = 1
      (is ((almost-equal 0.001)
           1.0
           (* (sut/exp x) (sut/exp (- x)))))

      ;; exp(x)*exp(1-x) = e = exp(1)
      (is ((almost-equal 0.001)
           (sut/exp 1.0)
           (* (sut/exp x)
              (sut/exp (- 1.0 x))))))))


