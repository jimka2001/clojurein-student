(ns clojurein-source-code.homework.recursion-test
  (:require [clojurein-source-code.homework.recursion :as sut]
            [clojurein-source-code.homework.util :refer [testing-with-timeout *time-out*]]
            [clojure.test :refer [deftest is testing]]))



(def recursion-limit 100000)

;; (deftest t-simple-recursion-a
;;   (testing-with-timeout "simple recursion a"
;;     (is (== 0 (sut/sum-by-simple-recursion ()))
;;         "expecting sum=0")
;;     (is (== 0 (sut/sum-by-simple-recursion '(0 0 0 0 0 0 0 0 0 0)))
;;         "expecting sum=0")
;;     (is (== 15 (sut/sum-by-simple-recursion '(1 2 3 4 5)))
;;         "expecting sum=15")))

(deftest t-loop-recur
  (testing-with-timeout "loop recur"
    (let [large-list (map (constantly 0) (range 1 recursion-limit))]
      (is (== 0 (sut/sum-by-loop-recur ())))
      (is (== 0 (sut/sum-by-loop-recur '(0 0 0 0 0 0 0 0 0 0))))
      (is (== 15 (sut/sum-by-loop-recur '(1 2 3 4 5))))
      (is (== 0 (sut/sum-by-loop-recur large-list))))))

(deftest t-reduce
  (testing-with-timeout "reduce"
    (let [large-list (map (constantly 0) (range 1 recursion-limit))]
      (is (== 0 (sut/sum-by-reduce ())))
      (is (== 0 (sut/sum-by-reduce '(0 0 0 0 0 0 0 0 0 0))))
      (is (== 15 (sut/sum-by-reduce '(1 2 3 4 5))))
      (is (== 0 (sut/sum-by-reduce large-list))))))

(deftest t-sum-doubles-a
  (testing-with-timeout "sum doubles a"
    (for [f [;; sut/sum-by-simple-recursion
             sut/sum-by-loop-recur
             sut/sum-by-reduce]]
      (do (is (== 0.0 (f ())))
          (is (== 0.0 (f '(0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0))))
          ;; we should actually have exact equality
          (is (== 16.5 (f '(1.5 2.0 3.25 4.0 5.75))))
          ))
    (for [f [;; sut/sum-by-simple-recursion
             sut/sum-by-loop-recur
             sut/sum-by-reduce]
          :let [long-list (map (constantly 0.0) (range recursion-limit))]]
      (is (== 0.0 (f long-list))))))

;; (deftest t-product-doubles
;;   (testing-with-timeout "HW product doubles simple recursion a"
;;     (is (== 1.0 (sut/product-by-simple-recursion ())))
;;     (is (== 0.0 (sut/product-by-simple-recursion '(0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0))))
;;     (is (== 1.0 (sut/product-by-simple-recursion '(1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0))))
    
;;     ;; we should actually have exact equality
;;     (is (== 12.0 (sut/product-by-simple-recursion '(2.0  3.0  4.0  0.5))))))

(deftest t-product-doubles-loop-recur
  (testing-with-timeout "HW product doubles loop/recur"
    (is (== 1.0 (sut/product-by-loop-recur ())))
    (is (== 0.0 (sut/product-by-loop-recur '(0.0 0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0))))
    (is (== 1.0 (sut/product-by-loop-recur '(1.0 1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0))))
    (is (== 0.0 (sut/product-by-loop-recur '(1.0 1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  0.0))))
    ;; we should actually have exact equality
    (is (== 12.0 (sut/product-by-loop-recur (list 2.0  3.0  4.0  0.5))))

    (let [long-list (map (constantly 0.0) (range recursion-limit))]
      (is (== 0.0 (sut/product-by-loop-recur long-list))))))

(deftest t-product-doubles-reduce
  (testing-with-timeout "HW product doubles reduce"
    (is (== 1.0 (sut/product-by-reduce ())))
    (is (== 0.0 (sut/product-by-reduce '(0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0  0.0))))
    (is (== 1.0 (sut/product-by-reduce '(1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0))))
    (is (== 0.0 (sut/product-by-reduce '(1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  1.0  0.0))))
    ;; we should actually have exact equality
    (is (== 12.0 (sut/product-by-reduce '(2.0  3.0  4.0  0.5))))
    (let [long-list (map (constantly 0.0) (range recursion-limit))]
      (is (== 0.0 (sut/product-by-reduce long-list))))
    (let [long-list (map (constantly 1.0) (range recursion-limit))]
      (is (== 1.0 (sut/product-by-reduce long-list))))))



      
