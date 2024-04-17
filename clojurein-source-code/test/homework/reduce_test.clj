(ns homework.reduce-test
  (:require [homework.reduce :as sut]
            [common.util :refer [testing-with-timeout *time-out*]]
            [clojure.pprint :refer [cl-format]]
            [clojure.test :refer [deftest is testing]]))



;; 2
(deftest t-running-sum
  (testing-with-timeout "running sum"
    (is (= '((1 1) (2 3) (3 6))
           (sut/running-sum '(1 2 3))))
    (is (= ()
           (sut/running-sum ())))
    (is (= '((1 1))
           (sut/running-sum '(1))))
    (is (= '((1 1) (-1 0) (0 0) (1 1) (-1 0) (1 1) (-1 0))
           (sut/running-sum '(1  -1  0  1  -1  1  -1))))))

;; 3
(deftest t-harmonic-sum
  (testing-with-timeout "harmonic sum"
    (is (= 1.0
           (sut/harmonic-sum '(1))))
    (is (= (/ 1.0 2)
           (sut/harmonic-sum '(2))))
    (is (< (- (sut/harmonic-sum '(1 2 4 8))
              (+ (/ 1.0 1)
                 (/ 1.0 2)
                 (/ 1.0 4)
                 (/ 1.0 8))) 0.000001))
    (is (< (- (sut/harmonic-sum '(1 -2 4 -8))
              (+ (/ 1.0 1)
                 (/ -1.0 2)
                 (/ 1.0 4)
                 (/ -1.0 8))) 0.000001))))

;; 4
(deftest t-plus
  (testing-with-timeout "plus"
    (is (= [0.0 0.0]
           (sut/plus [0.0 0.0] [0.0 0.0])))
    (is (= [4.0 6.0]
           (sut/plus [1.0 2.0] [3.0 4.0])))))


;; 5
(deftest t-plus-identity
  (testing-with-timeout "plus_identity"
    (doseq [re (range -100 100 7)
            im (range -100 100 13)
            :let [z [(float re) (float im)]]]
      (is (= z (sut/plus (sut/plus) z)))
      (is (= z (sut/plus z (sut/plus)))))))

;; 6
(deftest t-times
  (testing-with-timeout "times"
    (is (= [12.0  13.0]
           (sut/times [1.0 0.0] [12.0 13.0])))
    (is (= [-1.0   0.0]
           (sut/times [0.0 1.0] [0.0  1.0])))
    (is (= [-31.0 -29.0]
           (sut/times [3.0 5.0] [-7.0  2.0])))))

;; 7
(deftest t-times-identity
  (testing-with-timeout "times_identity"
    (doseq [re (range -100 100 7)
            im (range -100 100 13)
            :let [z [(float re) (float im)]]]
      (is (= z (sut/times (sut/times) z)))
      (is (= z (sut/times z (sut/times)))))))

;; 8
(deftest t-singleton-list
  (testing-with-timeout "plus singleton list"
    (doseq [re (range -100 100 7)
            im (range -100 100 13)
            :let [z [(float re) (float im)]]]
      (is (= z (sut/plus z))
          (cl-format false 
                     "failed to add singleton list for z=~A" z)))))

;; 9
(deftest t-plus-list
  (testing-with-timeout "plus list"
    (is (= [0.0 0.0]
           (sut/plus )))
    (is (= [1.0 2.0]
           (sut/plus [1.0 2.0])))
    (is (= [-5.0 6.0]
           (sut/plus [1.0 0.0] [1.0 0.0] [3.0 2.0] [-10.0 4.0])))))

;; 10
(deftest t-times-list
  (testing-with-timeout "times list"
    (is (= [1.0 0.0]
           (sut/times )))
    (is (= [1.0 2.0]
           (sut/times [1.0 2.0])))
    (is (= [-1.0 0.0]
           (sut/times [0.0 1.0] [0.0 1.0])))
    (is (= [-38.0 -8.0]
           (sut/times [1.0 0.0] [1.0 0.0] [3.0 2.0] [-10.0 4.0])))))

;; 11
(deftest t-times-singleton-list
  (testing-with-timeout "times singleton list"
    (doseq [re (range -100 100 7)
            im (range -100 100 13)
            :let [z [(float re) (float im)]]]
      (is (= z (sut/times z))
          (cl-format false "failed to multiply singleton list with z=~A" z)))))
