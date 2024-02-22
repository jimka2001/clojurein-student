(ns clojurein-source-code.homework.hello-test
  (:require [clojurein-source-code.homework.hello :as sut]
            [clojure.string :as string]
            [clojurein-source-code.common.util :refer [testing-with-timeout *time-out*]]
            [clojure.test :refer [deftest is testing]]))


(deftest t-hello-1
  (testing-with-timeout "hello-1"
    (is (= (sut/hello "x")
           "Hello, x."))))

(deftest t-prefix
  (testing-with-timeout "prefix"
    (for [i (range 100)
          :let [who (str i)]]
      (is (= (string/starts-with? (sut/hello who) "Hello, "))))))

(deftest t-suffix
  (testing-with-timeout "suffix"
    (for [i (range 100)
          :let [who (str i)]]
      (is (= (string/ends-with? (sut/hello who) "."))))))

(deftest t-infix
  (testing-with-timeout "infix"
    (for [i (range 100)
          :let [who (str i)]]
      (is (= (string/includes? (sut/hello who) who))))))

(deftest t-trivial
  (testing-with-timeout "trivial"
    (is (= (sut/hello "") "Hello, ."))
    (is (= (sut/hello "Jim")   "Hello, Jim."))
    (is (= (sut/hello "Fred")  "Hello, Fred."))))
