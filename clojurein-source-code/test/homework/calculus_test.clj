(ns homework.calculus-test
  (:require [homework.calculus :as sut]
            [common.util :refer [almost-equal
                                 testing-with-timeout *time-out*]]
            [clojure.math :refer [log sin cos random]]
            [clojure.test :refer [deftest is]]))

(def calculus-time-out (* 10 60 1000)) ;; 10 sec

(defn find-contradiction 
  "search randomly within an interval attempting to find an x value where
  f(x) and g(x) differ by more than epsilon.
  If no such x value is found, return false,
  otherwise return a triple indicating the x value and the corresponding values of f and g."
  [f g lower upper epsilon reps]
  (loop [reps reps]

    (let [x (+ lower (* (- upper lower) (random)))]
      (cond (= 0 reps)
            false

            (> (abs (- (f x) (g x))) epsilon)
            [x (f x) (g x)]

            :else
            (recur (dec reps))))))


(deftest t-ln-0
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "ln 0"
                          (is ((almost-equal 0.001)
                               (sut/integral (fn [x] (/ 1.0 x))
                                             1.0 2.0 15 0.00001)
                               (log 2))))))

(deftest t-ln-0b
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "ln 0b"
                          (letfn [(i [x]
                                    (sut/integral (fn [t] (/ 1.0 t))
                                                  1.0 x 10 0.00001))]
                            (let [contradiction (find-contradiction i log 2 3 0.0001 100)]
                              (is (= false contradiction)))))))




(deftest t-ln-1
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "ln 1"
                          (doseq [k (range 2 10)]
                          (is ((almost-equal 0.001)
                               (sut/integral (fn [x] (/ 1.0 x))
                                             1.0 k 20 0.00001)
                               (log k)))))))

(deftest t-sin-1
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "sin 1"
                          (doseq [k (range 2 10)
                                  :let [expected (- (cos 0) (cos k))
                                        i (sut/integral sin
                                                        0.0 k 20 0.00001)]]
                            (is ((almost-equal 0.001)
                                 expected i)
                                (format "%f != %f" expected i)
                                )))))
                            


