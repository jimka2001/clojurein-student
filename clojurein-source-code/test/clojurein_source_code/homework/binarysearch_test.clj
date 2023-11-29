(ns clojurein-source-code.homework.binarysearch-test
  (:require [clojurein-source-code.homework.binarysearch :as sut]
            [clojure.pprint :refer [cl-format]]
            [clojurein-source-code.common.util :refer [almost-equal]]
            [clojurein-source-code.homework.util :refer [with-timeout *time-out*]]
            [clojure.test :refer [deftest is testing use-fixtures]]))

(use-fixtures :each (with-timeout *time-out*))

(deftest t-tolerance
  (testing "tolerance"
    (is ((almost-equal 0.0) 1.0 1.0))
    (is ((almost-equal 0.5) 1.0 1.2))
    (is ((almost-equal 0.5) 100.1  100.2))
    (is ((almost-equal 0.1) 1000.01  1000.05))
    (is (not ((almost-equal 0.01) 0.001  0.101)))))


(deftest t-max-depth
  (testing "max depth"
    ;; check for max-depth exceeded
    (is (= false (sut/bin-search-by-boolean 0.0  100.0  #(>= % 5.0)  0.01   2)))
    (is (= false (sut/bin-search-by-boolean 0.0  100.0  #(>= % 5.0)  0.01  3)))))

(deftest t-no-result-in-rante
  (testing "no result in range"
    ;; check for no such result in range
    (is (= false (sut/bin-search-by-boolean 10.0  20.0 
                                     #(> % 3.4)  0.01  100)))))

(deftest t-find-switch
  (testing "find switching point"
    ;; check for successfully finding the switching point
    (doseq [left [-10] ;; (range -10 0)
            right [2] ;; (range 2 11)
            c [1] ;; (range 1 11)
            :let [cross (/ c 10.0)]]
      (do (is ((almost-equal 0.1) cross
               (sut/bin-search-by-boolean left  right  #(> % cross )
                                          0.01  1000))
              (cl-format 
               false "~&~
                  58: failed to find cross=~A ~@
                  between left=~A and right=~A"
               cross left right))
          (is ((almost-equal 0.1) cross
               (sut/bin-search-by-boolean left  right
                                          #(<= % cross)
                                          0.01  1000))
              (cl-format 
               false "~&~
                  67: failed to find cross=~A ~@
                  between left=~A and right=~A~@
                  c=~A"
               cross left right c))))))

(deftest t-find-range
  (testing "find range and switching point"
    ;; check for successfully finding the switching point
    (doseq [c (range 1 11)
            :let [cross (/ c 10.0)]]
      (is ((almost-equal 0.1) cross
           (sut/bin-search-by-boolean #(> % cross)  0.01))
          (cl-format false "~&~
                            failed to find cross=~A" cross))
      (is ((almost-equal 0.1) cross
           (sut/bin-search-by-boolean #(<= % cross) 0.01))
          (cl-format false "~&~
                            failed to find cross=~A" cross)))))
