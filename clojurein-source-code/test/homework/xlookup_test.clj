(ns homework.xlookup-test
  (:require [homework.xlookup :as sut]
            [clojure.string :refer [trim]]
            [common.util :refer [*time-out* testing-with-timeout is]]
            [clojure.test :refer [deftest]]))

(def csv-1
  "col0,col1,col2
  x,y,z
a,b,c")
  
(def csv-2
  "col0,col1,col2
  x,1,2
a,10,20")
  
  

(deftest t-read-1
  (testing-with-timeout "read-1"
    (is (= (sut/read-csv-string csv-1 {"col0" trim} {})
           [{"col0" "x" "col1" "y" "col2" "z"}
            {"col0" "a" "col1" "b" "col2" "c"}]))

    (is (= (sut/read-csv-string csv-1 {"col0" trim} {"col1" "COL-1"})
           [{"col0" "x" "COL-1" "y" "col2" "z"}
            {"col0" "a" "COL-1" "b" "col2" "c"}]))
))

(deftest t-merge-1
  (testing-with-timeout "merge-1"
    (is (= [{"col0" "x", "col1" "y", "col2" "z", "COL-11" "1", "COL-12" "2"}          
            {"col0" "a", "col1" "b", "col2" "c", "COL-11" "10", "COL-12" "20"}]
           
           (sut/join-csv-string csv-1 {"col0" trim} {} "col0"
                                csv-2 {"col0" trim} {"col1" "COL-11"
                                                     "col2" "COL-12"} "col0")))))
