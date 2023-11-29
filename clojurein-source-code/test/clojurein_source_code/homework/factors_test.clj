(ns clojurein-source-code.homework.factors-test
  (:require [clojurein-source-code.homework.factors :as sut]
            [clojure.pprint :refer [cl-format]]
            [clojure.math :refer [sqrt ceil]]
            [clojure.test :refer [deftest is testing]]))

(defn prime?
  [p]
  (and (int? p)
       (< 1 p)
       (or (= p 2)
           (empty? (filter (fn [f]
                             (= 0 (mod p f)))
                           (range 2 (inc (ceil (sqrt p)))))))))


;; code from https://stackoverflow.com/questions/22440673/picking-random-elements-from-a-vector
(defn choose-random [r v]
  (mapv v (repeatedly r #(rand-int (count v)))))

(def some-primes (into [] (filter prime? (range 2 500))))

;; prime-factors

(deftest t-trivial-prime-factors
  (testing "trivialprime-factors"
    (is (= [] (sut/prime-factors 1)))
    (is (= [2] (sut/prime-factors 2)))
    (is (= [3] (sut/prime-factors 3)))
    (is (= [5] (sut/prime-factors 5)))))


(deftest t-prime-factors
  (testing "prime-factors"
    (doseq [k (range 2 100 3)
            :let [fs (sut/prime-factors k)
                  product (reduce * fs)]]
      (is (= k product)
          (cl-format nil "product of factors is ~A, expecting ~S computed factors=~A"
                     product k fs))
      (doseq [p fs]
        (is (prime? p)
            (cl-format nil "computed a factor ~A of ~A which is not prime: computed factors=~A"
                       p k fs))))))

(deftest t-prime-factors-2
  (testing "prime-factors-2"
    (dotimes [_ 5000]
      (let [n (inc (rand-int 12))
            primes (choose-random n some-primes)
            composite (reduce *' primes)
            fs (sut/prime-factors composite)]
        (is (= (sort fs)
               (sort primes)))))))
      
              
      
