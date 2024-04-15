(ns homework.convolution-test
  (:require [homework.convolution :as sut]
            [common.util :refer [almost-equal
                                 testing-with-timeout *time-out*]]
            [clojure.math :refer [log sin cos]]
            [clojure.test :refer [deftest is]]))

(def convolution-time-out (* 10 60 1000)) ;; 10 sec

(deftest t-clip
  (binding [*time-out* convolution-time-out]
    (testing-with-timeout "clip 0"
                          (is (= 0 ((sut/clip-port sin -1 1) -1)))
                          (is (= 0 ((sut/clip-port sin -1 1) 1)))
                          (is (= 0 ((sut/clip-port sin -1 1) -1.5)))
                          (is (= 0 ((sut/clip-port sin -1 1) 1.5)))
                          
                          (is (= (sin 0) ((sut/clip-port sin -1 1) 0)))
                          (is (= (sin 0.5) ((sut/clip-port sin -1 1) 0.5)))
                          (is (= (sin -0.5) ((sut/clip-port sin -1 1) -0.5)))
)))

(deftest t-ln-0
  (binding [*time-out* convolution-time-out]
    (testing-with-timeout "ln 0"
                          (is ((almost-equal 0.001)
                               (sut/integral (fn [x] (/ 1.0 x))
                                             1.0 2.0 0.00001)
                               (log 2))))))

(deftest t-ln-1
  (binding [*time-out* convolution-time-out]
    (testing-with-timeout "ln 1"
                          (doseq [k (range 2 10)]
                          (is ((almost-equal 0.001)
                               (sut/integral (fn [x] (/ 1.0 x))
                                             1.0 k 0.00001)
                               (log k)))))))

(deftest t-sin-1
  (binding [*time-out* convolution-time-out]
    (testing-with-timeout "sin 1"
                          (doseq [k (range 2 10)
                                  :let [expected (- (cos 0) (cos k))
                                        i (sut/integral sin
                                                  0.0 k 0.00001)]]
                            (is ((almost-equal 0.001)
                                 expected i)
                                (format "%f != %f" expected i)
                                )))))
                            

(deftest t-convolve-commutative
  (binding [*time-out* convolution-time-out]
    (let [fs [sin cos (fn [x] (* x x))
              (fn [x] (+ x 1))
              (fn [x] (* (sin x) (sin x)))]]
      (testing-with-timeout "convolution commutative"
                            (doseq [f fs
                                    g fs]
                              (let [c1 (sut/convolution f g -1.0 1.0)
                                    c2 (sut/convolution g f -1.0 1.0)]
                                (doseq [k (range 10)
                                        :let [x (+ -2.0 (* 4 (/ 10.0 k)))]]
                              (is ((almost-equal 0.01)
                                   (c1 x 0.0001)
                                   (c2 x 0.0001))))))))))
