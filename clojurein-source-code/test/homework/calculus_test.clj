(ns homework.calculus-test
  (:require [homework.calculus :as sut]
            [common.util :refer [almost-equal
                                 testing-with-timeout *time-out*]]
            [clojure.math :refer [log sin cos random]]
            [clojure.test :refer [deftest is testing]]))

(def calculus-time-out (* 10 60 1000)) ;; 10 sec

(defn find-contradiction 
  "search randomly within an interval attempting to find an x value where
  f(x) and g(x) differ by more than epsilon.
  If no such x value is found, return false,
  otherwise return a triple indicating the x value and the corresponding values of f and g."
  [f g lower upper epsilon reps]
  (assert (number? upper))
  (assert (number? lower))
  (assert (< lower upper))
  (assert (float? epsilon))
  (assert (< 0 epsilon))
  (assert (int? reps))
  (assert (< 0 reps))
  
  (loop [reps reps]

    (let [x (+ lower (* (- upper lower) (random)))]
      (cond (= 0 reps)
            false

            (> (abs (- (f x) (g x))) epsilon)
            [x (f x) (g x)]

            :else
            (recur (dec reps))))))


(deftest t-integral-ln-0
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "integral ln 0"
                          (is ((almost-equal 0.001)
                               (sut/integral (fn [x] (/ 1.0 x))
                                             1.0 2.0 15 0.00001)
                               (log 2))))))

(deftest t-integral-ln-0b
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "integral ln 0b"
                          (letfn [(i [x]
                                    (sut/integral (fn [t] (/ 1.0 t))
                                                  1.0 x 10 0.00001))]
                            (let [contradiction (find-contradiction i log 2 3 0.0001 100)]
                              (is (= false contradiction)))))))




(deftest t-intregral-ln-1
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "integral ln 1"
                          (doseq [k (range 2 10)]
                          (is ((almost-equal 0.001)
                               (sut/integral (fn [x] (/ 1.0 x))
                                             1.0 k 20 0.00001)
                               (log k)))))))

(deftest t-integral-sin-1
  (binding [*time-out* calculus-time-out]
    (testing-with-timeout "integral sin 1"
                          (doseq [k (range 2 10)
                                  :let [expected (- (cos 0) (cos k))
                                        i (sut/integral sin
                                                        0.0 k 20 0.00001)]]
                            (is ((almost-equal 0.001)
                                 expected i)
                                (format "%f != %f" expected i)
                                )))))
                            

(deftest t-derivative-0
  (binding [*time-out* calculus-time-out]
    (testing "derivative 0"
      (letfn [(== [a b]
                ((almost-equal 0.001) a b))
              (f+ [x]
                (if (< 0 x) ;; if x positive
                  (* 3 x)
                  0.0))
              (f- [x]
                (if (> 0 x) ;; if x negative
                  (* 3 x)
                  0.0))
                ]
        (is (== (sut/derivative+ f+ 0.0 0.01 0.00001)
               3.0))

        (is (== (sut/derivative+ f+ 0.1 0.01 0.00001)
               3.0))

        (is (== (sut/derivative- f+ 0.0 0.01 0.00001)
             0.0))

        (is (== (sut/derivative- f+ -0.1 0.01 0.00001)
             0.0))

        (is (== (sut/derivative+ f- 0.0 0.01 0.00001)
               0.0))

        (is (== (sut/derivative- f- 0.0 0.01 0.00001)
               3.0))))))

      

(deftest t-derivative-1
  (binding [*time-out* calculus-time-out]
    (testing "derivative 1"
      (doseq [[k f f'] [[1 (fn [_x] 5.3) (fn [_x] 0)]
                        [2 sin cos]
                        [3 cos (fn [x] (- (sin x)))]

                        [4 (fn [x] (+ (* 3 x) 1)) (fn [_x] 3)]
                        [5 (fn [x] (+ (* 3 x x) x 1)) (fn [x] (+ (* 6 x) 1))]]
              :let [epsilon 0.0001
                    dx+ (fn [x] (let [d (sut/derivative+ f x 0.01 epsilon)]
                                  (assert (number? d))
                                  d))]
              x (range -10.0 10.0 0.1)]
        ;; derivative+
        (is (number? (dx+ x)) (format "derivative+ of %s at x=%s, is not a number, got %s"
                                      f x (dx+ x)))
        (is (< (abs (- (dx+ x) (f' x))) epsilon)
            (format "[k=%s] error in derivative+ computation: expecting %s, got %s, difference %s"
                    k (f' x) (dx+ x)
                    (abs (- (dx+ x) (f' x)))))
        ))))

(deftest t-derivative-2
  (binding [*time-out* calculus-time-out]
    (testing "derivative 1"
      (doseq [[k f f'] [[1 (fn [_x] 5.3) (fn [_x] 0)]
                        [2 sin cos]
                        [3 cos (fn [x] (- (sin x)))]

                        [4 (fn [x] (+ (* 3 x) 1)) (fn [_x] 3)]
                        [5 (fn [x] (+ (* 3 x x) x 1)) (fn [x] (+ (* 6 x) 1))]]
              :let [epsilon 0.0001
                    dx- (fn [x] (let [d (sut/derivative- f x 0.01 epsilon)]
                                  (assert (number? d))
                                  d))]
              x (range -10.0 10.0 0.1)]
        ;; derivative+

        ;; derivative-
        (is (number? (dx- x)) (format "derivative- of %s at x=%s, is not a number, got %s"
                                      f x (dx- x)))
        (is (< (abs (- (dx- x) (f' x))) epsilon)
            (format "[k=%s] error in derivative- computation: expecting %s, got %s, difference %s"
                    k (f' x) (dx- x)
                    (abs (- (dx- x) (f' x)))))
        ))))

(deftest t-derivative-3
  (binding [*time-out* calculus-time-out]
    (testing "derivative 1"
      (doseq [[k f f'] [[1 (fn [_x] 5.3) (fn [_x] 0)]
                        [2 sin cos]
                        [3 cos (fn [x] (- (sin x)))]

                        [4 (fn [x] (+ (* 3 x) 1)) (fn [_x] 3)]
                        [5 (fn [x] (+ (* 3 x x) x 1)) (fn [x] (+ (* 6 x) 1))]]
              :let [epsilon 0.0001
                    dx (fn [x] (let [d (sut/derivative f x 0.01 epsilon)]
                                 (assert (number? d))
                                 d))]
              x (range -10.0 10.0 0.1)]


        ;; derivative
        (is (number? (dx x)) (format "derivative of %s at x=%s, is not a number, got %s"
                                      f x (dx x)))
        (is (< (abs (- (dx x) (f' x))) epsilon)
            (format "[k=%s] error in derivative computation: expecting %s, got %s, difference %s"
                    k (f' x) (dx x)
                    (abs (- (dx x) (f' x)))))


        ))))



                                  
                                          
                                  
