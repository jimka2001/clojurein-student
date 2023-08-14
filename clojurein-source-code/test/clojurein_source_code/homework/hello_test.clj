(ns clojurein-source-code.homework.hello-test
  (:require [clojurein-source-code.homework.hello :as sut]
            [clojure.string :as string]
            [clojure.test :refer [deftest is testing]]))

(deftest t-hello-1
  (testing "hello-1"
    (is (= (sut/hello "x")
           "Hello, x."))))

(deftest t-prefix
  (testing "prefix"
    (for [i (range 100)
          :let [who (str i)]]
      (is (= (string/starts-with? (sut/hello who) "Hello, "))))))

(deftest t-suffix
  (testing "suffix"
    (for [i (range 100)
          :let [who (str i)]]
      (is (= (string/ends-with? (sut/hello who) "."))))))

(deftest t-infix
  (testing "infix"
    (for [i (range 100)
          :let [who (str i)]]
      (is (= (string/includes? (sut/hello who) who))))))

(deftest t-trivial
  (testing "trivial"
    (is (= (sut/hello "") "Hello, ."))
    (is (= (sut/hello "Jim")   "Hello, Jim."))
    (is (= (sut/hello "Fred")  "Hello, Fred."))))
