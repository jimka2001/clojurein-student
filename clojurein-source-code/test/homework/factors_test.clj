(ns homework.factors-test
  (:require [homework.factors :as sut]
            [clojure.pprint :refer [cl-format]]
            [common.util :refer [re-chunk testing-with-timeout *time-out* member]]
            [clojure.math :refer [sqrt ceil]]
            [clojure.test :refer [deftest is testing]]))

(defn prime?
  [p]
  (and (int? p)
       (< 1 p)
       (or (= p 2)
           (and (not= 0 (mod p 2))
                (empty? (filter (fn [f]
                                  (= 0 (mod p f)))
                                (re-chunk 1 (range 3 (inc (sqrt p)) 2))))))))


;; code from https://stackoverflow.com/questions/22440673/picking-random-elements-from-a-vector
(defn choose-random [r v]
  (mapv v (repeatedly r #(rand-int (count v)))))

(def some-primes (into [] (cons 2 (filter prime? (range 3 500 2)))))

;; prime-factors

(deftest t-trivial-prime-factors
  (testing-with-timeout "trivialprime-factors"
    (is (= [] (sut/prime-factors 1)))
    (is (= [2] (sut/prime-factors 2)))
    (is (= [3] (sut/prime-factors 3)))
    (is (= [5] (sut/prime-factors 5)))))

(deftest t-trivial-singleton-factor
  (testing-with-timeout "trivial-singleton-factor"
    (doseq [k (range 2 1000)]
      (if (prime? k)
        (is (= [k] (sut/prime-factors k)))
        (is (not= [k] (sut/prime-factors k)))))))

(deftest t-trivial-pairs
  (testing-with-timeout "trivial pairs"
    (doseq [k (range 2 500)
            :when (prime? k)
            j (range (inc k) 500)
            :when (prime? j)
            :let [factors (sut/prime-factors (* k j))]]
      (is (= 2 (count factors)))
      (is (member k factors))
      (is (member j factors)))))

(deftest t-prime-factors
  (testing-with-timeout "prime-factors"
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
  (testing-with-timeout "prime-factors-2"
    (dotimes [_ 5000]
      (let [n (inc (rand-int 12))
            primes (choose-random n some-primes)
            composite (reduce *' primes)
            fs (sut/prime-factors composite)]
        (is (= (sort fs)
               (sort primes))
            (cl-format nil "for n=~A computed factors ~A but expecting ~A"
                       composite (sort fs) (sort primes))
            )))))

(deftest t-prime-factors-bigint
  (testing-with-timeout "prime-factors-bigint"
    (let [factors [2 2 2
                   3 3 3
                   5 5 5
                   7 7 7
                   11 11 11
                   13 13 13
                   17 17 17
                   19 19 19]]
      (is (= factors (sort (sut/prime-factors (reduce *' factors))))))))
