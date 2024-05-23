(ns lecture.baby-name-plot-test
  (:require [lecture.baby-name-plot :as sut]
            [common.util :refer [testing-with-timeout]]
            [clojure.test :refer [deftest is]]))

(deftest t-baby-name-normalized-data
  (testing-with-timeout "baby-name-normalized-data"
                        (is
     (sut/baby-name-normalized-data "Juan" "M" "MS"))))
