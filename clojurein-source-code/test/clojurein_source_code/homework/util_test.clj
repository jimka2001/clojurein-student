(ns clojurein-source-code.homework.util-test
  (:require [clojurein-source-code.common.util :as sut]
            [clojure.pprint :refer [cl-format]]
            [clojure.test :refer [deftest is testing]]))

(deftest t-tails
  (testing "tails"
    (is (= '((1 2 3)
             (2 3)
             (3))
           (sut/tails '(1 2 3))))

    (is (= '()
           (sut/tails '())))))

(deftest t-member
  (testing "member"
    (is (= true
           (sut/member 1 '(0 1 2))))
    (is (= true
           (sut/member true '(0 true 2))))
    (is (= false
           (sut/member true '(0 1 2))))
    (is (= false
           (sut/member nil '(0 1 2))))
    (is (= false
           (sut/member nil '(0 false 2))))
    (is (= false
           (sut/member false '(0 nil 2))))
    (is (= true
           (sut/member nil '(0 nil 2))))



    (is (= true
           (sut/member 1 [0 1 2])))
    (is (= true
           (sut/member true '(0 true 2))))
    (is (= false
           (sut/member true [0 1 2])))
    (is (= false
           (sut/member nil [0 1 2])))
    (is (= false
           (sut/member nil [0 false 2])))
    (is (= false
           (sut/member false [0 nil 2])))
    (is (= true
           (sut/member nil [0 nil 2])))


    (is (= true
           (sut/member nil #{0 nil 2})))
    (is (= true
           (sut/member false #{0 false 2})))
    (is (= false
           (sut/member nil #{0 false 2})))
    (is (= false
           (sut/member false #{0 true 2})))))


(deftest t-almost-equal
  (testing "almost equal"
    (is (= true ((sut/almost-equal 0.01) 1.0 1.00001)))
    (is (= false ((sut/almost-equal 0.01) 1.0 2.00001)))))
