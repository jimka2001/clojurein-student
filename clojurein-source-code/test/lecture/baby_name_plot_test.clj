(ns clojurein-source-code.lecture.baby-name-plot-test
  (:require [clojurein-source-code.lecture.baby-name-plot :as sut]
            [clojure.pprint :refer [cl-format]]
            [clojurein-source-code.common.util :refer [testing-with-timeout]]
            [clojure.test :refer [deftest is testing]]))

(deftest t-baby-name-normalized-data
  (testing-with-timeout "baby-name-normalized-data"
                        (is
     (sut/baby-name-normalized-data "Juan" "M" "MS"))))
