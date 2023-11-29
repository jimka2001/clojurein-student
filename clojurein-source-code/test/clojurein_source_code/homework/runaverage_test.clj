(ns clojurein-source-code.homework.runaverage-test
  (:require [clojurein-source-code.homework.runaverage :as sut]
            [clojurein-source-code.homework.util :refer [with-timeout *time-out*]]
            [clojure.pprint :refer [cl-format]]
            [clojurein-source-code.common.util :refer [almost-equal]]
            [clojure.test :refer [deftest is testing use-fixtures]]))

(use-fixtures :each (with-timeout *time-out*))

(deftest t-almost-equal-2
  (testing "almost equal 2"
    (doseq [data ['(1.0  2.0  6.0  4.0  2.4  3.5  7.7)
                  [1.0  2.0  6.0  4.0  2.4  3.5  7.7]]
            :let [calculated  (sut/run-average 2 data)
                  control [[1.0  1.0] [2.0  1.5] [6.0  4.0] [4.0  5.0] [2.4  3.2] [3.5  2.95] [7.7  5.6]]]]
      (dorun (map (fn [[a1 b1] [a2 b2]]
                    (is (= a1 a2))
                    (is ((almost-equal 0.001) b1 b2)))
                  calculated
                  control)))))

(deftest t-almost-equal-3
  (testing "almost equal 3 "
    (doseq [data ['(1.0  2.0  6.0  4.0  2.4  3.5  7.7)
                  [1.0  2.0  6.0  4.0  2.4  3.5  7.7]]
            :let [calculated (sut/run-average 3 data)
                  control '((1.0  1.0)  (2.0  1.5)  (6.0  3.0)  (4.0  4.0)  (2.4  4.133333333333334) 
                            (3.5  3.3)  (7.7  4.533333333333333))]]
      (dorun (map (fn [[a1 b1] [a2 b2]]
                    (is (= a1 a2))
                    (is ((almost-equal 0.001) b1 b2)))
                  calculated
                  control)))))

(deftest t-almost-equal-4
  (testing "almost equal 4"
    (doseq [data ['(1.0  2.0  6.0  4.0  2.4  3.5  7.7)
                  [1.0  2.0  6.0  4.0  2.4  3.5  7.7]]
            :let [calculated (sut/run-average 4 data)
                  control '((1.0 1.0) (2.0 1.5) (6.0 3.0) (4.0 3.25) (2.4 3.6) (3.5 3.975) (7.7, 4.4))]]
      (dorun (map (fn [[a1 b1] [a2 b2]]
                    (is (= a1 a2))
                    (is ((almost-equal 0.001) b1 b2)
                        (cl-format false "~&~
                                          expecting b1 = b2~@
                                          b2=~A~@
                                          b1=~A~@
                                          a1=~A~@
                                          a2=~A" b1 b2 a1 a2)))
                  calculated
                  control)))))

(deftest t-almost-equal-5
  (testing "almost equal 5"
    (doseq [data ['(1.0  2.0  6.0  4.0  2.4  3.5  7.7)
                  [1.0  2.0  6.0  4.0  2.4  3.5  7.7]]
            :let [calculated (sut/run-average 5 data)
                  control '((1.0  1.0)  (2.0  1.5)  (6.0  3.0)  (4.0  3.25)  (2.4  3.08)  (3.5  3.58)  (7.7  4.72))]]
      (dorun (map (fn [[a1 b1] [a2 b2]]
                    (is (= a1 a2))
                    (is ((almost-equal 0.001) b1 b2)))
                  calculated
                  control)))))
