(ns homework.determinant-test
  (:require [homework.determinant :as sut]
            [common.util :refer [testing-with-timeout]]
            [clojure.test :refer [deftest is]]))

(deftest t-det-trival
  (testing-with-timeout "det trivial"
    (is (= 1 (sut/determinant [[1]])))))

(deftest t-det-singletons
  (testing-with-timeout "det singletons"
    (doseq [n (range -100 100)]
      (is (= n (sut/determinant [[n]]))))))

(defn mat-tabulate [dim f]
  (mapv (fn [r]
          (mapv (fn [c] (f r c)) (range dim)))
        (range dim)))

(deftest t-identity
  (testing-with-timeout "det identity"
    (is (= 1 (sut/determinant [[1 0]
                               [0 1]])))
    (is (= 1 (sut/determinant [[1 0 0]
                               [0 1 0]
                               [0 0 1]])))
    (doseq [n (range 1 10)]
      (is (= 1 (sut/determinant (mat-tabulate n
                                              (fn [r c]
                                                (if (= r c) 1 0)))))))))

(deftest t-zero
  (testing-with-timeout "det zero"
    (doseq [n (range 1 10)]
      (is (= 0 (sut/determinant (mat-tabulate n
                                              (fn [_r _c]
                                                0))))))))


(deftest t-test-41
  (testing-with-timeout "test 41"
    (is (= 0 (sut/determinant [[1 2 3]
                               [4 5 6]
                               [7 8 9]])))))

(deftest t-test-42
  (testing-with-timeout "test 42"
    (is (= -6 (sut/determinant [[1 2 3 1] 
                                [4 4 6 2]
                                [4 5 6 3]
                                [7 8 9 4]])))
    (is (= -6 (sut/determinant [[1 2 3 1 1] 
                                [4 4 6 2 1]
                                [4 4 6 2 2]
                                [4 5 6 3 1]
                                [7 8 9 4 1]])))
    (is (= 6 (sut/determinant [[1 0 3 1 1 1] 
                               [4 4 6 2 1 2]
                               [4 4 6 2 2 1]
                               [4 4 6 2 2 2]
                               [4 5 6 3 1 1]
                               [7 8 9 4 1 2]])))

    (is (= -6 (sut/determinant [[1 0 3 1 1 1 0] 
              [4 4 6 2 1 2 2]
              [4 4 6 2 1 2 1]
              [4 4 6 2 2 1 2]
              [4 4 6 2 2 2 2]
              [4 5 6 3 1 1 1]
              [7 8 9 4 1 2 3]])))
    ))




(deftest t-test-43
  (testing-with-timeout "test 43"
    (is (= -18612
           (sut/determinant [[1 1 0 3 1 1 1 0] 
                             [4 2 4 6 2 1 2 2]
                             [4 4 3 6 2 1 2 1]
                             [4 4 6 4 2 2 1 2]
                             [4 4 6 2 5 2 1 2]
                             [4 4 6 2 2 6 2 2]
                             [4 5 6 3 1 1 7 1]
                             [7 8 9 4 1 2 3 8]])))))

(deftest t-test-44
  (testing-with-timeout "test 44"
    (is (= 51952
           (sut/determinant [[1 0 1 0 3 1 1 1 0] 
                         [4 2 0 4 6 2 1 2 2]
                         [4 4 3 0 6 2 1 2 1]
                         [4 4 6 4 0 2 2 1 2]
                         [4 4 6 4 2 0 2 1 2]
                         [4 4 6 2 5 2 0 1 2]
                         [4 4 6 2 2 6 2 0 2]
                         [4 5 6 3 1 1 7 1 0]
                         [0 7 8 9 4 1 2 3 8]])))))


(deftest t-test-45
  (testing-with-timeout "test 45"
    (is (= -2253024
           (sut/determinant [[1 0 1 1 0 3 1 1 1 0] 
                         [4 2 0 4 2 6 2 1 2 2]
                         [4 4 3 0 6 3 2 1 2 1]
                         [4 4 6 4 0 2 4 2 1 2]
                         [4 4 6 4 2 0 2 5 1 2]
                         [4 4 6 4 2 0 2 1 6 2]
                         [4 4 6 2 5 2 0 1 2 7]
                         [8 4 4 6 2 2 6 2 0 2]
                         [4 9 5 6 3 1 1 7 1 0]
                         [0 7 1 8 9 4 1 2 3 8]])))))

(deftest t-test-46
  (testing-with-timeout "test 46"
    (is (= 620192
           (sut/determinant [[1 0 1 1 0 -3 1 1 1 0 1] 
                         [4 2 0 4 2 6 2 1 2 1 2]
                         [4 4 3 0 6 3 2 1 1 2 1]
                         [4 4 6 4 0 2 4 1 2 1 2]
                         [4 4 6 4 2 0 1 2 5 1 2]
                         [4 4 6 4 2 1 0 2 5 1 2]
                         [4 4 6 4 1 2 0 2 1 6 2]
                         [4 4 6 1 2 5 2 0 1 2 7]
                         [8 4 1 4 6 2 2 6 2 0 2]
                         [4 1 9 5 6 3 1 1 7 1 0]
                         [1 0 7 1 8 9 4 1 2 3 8]])))))
