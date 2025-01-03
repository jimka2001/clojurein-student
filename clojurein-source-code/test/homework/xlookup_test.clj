(ns homework.xlookup-test
  (:require [homework.xlookup :as sut]
            [clojure.java.io :as io]
            [clojure.string :refer [trim]]
            [common.util :refer [*time-out* testing-with-timeout is]]
            [clojure.test :refer [deftest]]))

(def csv-1
  "#1, 2, 3 comment
col0,col1,col2
  x,y,z
  a,b,c")
  
(def csv-2
  "col0,col1,col2
  x,1,2
  a,10,20")
  
(def csv-3
  "A,B,C,D
  x,2,3,4
  a,20,30,40
  q,r,s,t")

(def csv-4
  "A,B,C,D
  a,20,30,40
  x,2,3,4
  a,200,300,400
  q,r,s,t")
  
  

(deftest t-read-1
  (testing-with-timeout "read-1"
    (is (= (sut/read-csv csv-1 :parsers {"col0" trim})
           [["col0" "col1" "col2"]
            [{"col0" "x" "col1" "y" "col2" "z"}
             {"col0" "a" "col1" "b" "col2" "c"}]]))
    ))

(deftest t-read-2
  (testing-with-timeout "read-2"
    (let [[headers tbl] (sut/read-csv csv-1 :parsers {"col0" trim})]
      (is (= (sut/rename-columns headers tbl {"col1" "COL-1"})
             [["col0" "COL-1" "col2"]
              [{"col0" "x" "COL-1" "y" "col2" "z"}
               {"col0" "a" "COL-1" "b" "col2" "c"}]])))
    ))

(deftest t-read-3
  (testing-with-timeout "read-3"
    (let [[headers tbl] (sut/read-csv-and-rename csv-1 :parsers {"col0" trim} :rename {"col1" "COL-1"})]
      (is (= [headers tbl]
             [["col0" "COL-1" "col2"]
              [{"col0" "x" "COL-1" "y" "col2" "z"}
               {"col0" "a" "COL-1" "b" "col2" "c"}]])))
    ))

(deftest t-read-4
  (testing-with-timeout "read-4"
    (let [s (io/resource "US-baby-names/namesbystate/CT.TXT")]
      (with-open [r (io/reader s)]      
        (let [[[headers] lines] (sut/read-csv r
                                              :parsers {"count" parse-long}
                                              :headers ["state" "gender" "year" "name" "count"])]
          (is (= 10815
                 (reduce (fn [acc m]
                           (cond (not= "Anna" (get m "name"))
                                 acc

                                 :else
                                 (+ acc (get m "count"))))
                         0
                         lines))))))))


(deftest t-merge-1
  (testing-with-timeout "merge-1"
    (is (= [[["col0" "col1" "col2"] ["col0" "COL-11" "COL-12"]]
            [{"col0" "x", "col1" "y", "col2" "z", "COL-11" "1", "COL-12" "2"}          
             {"col0" "a", "col1" "b", "col2" "c", "COL-11" "10", "COL-12" "20"}]]
           
           (sut/join-csv csv-1 {"col0" trim} {} "col0"
                                csv-2 {"col0" trim} {"col1" "COL-11"
                                                     "col2" "COL-12"} "col0")))))

(deftest t-merge-2
  (testing-with-timeout "merge-2"
    (is (= [[["col0" "col1" "col2"] ["A" "B" "C" "D"]]
            [{"col0" "x", "A" "x" "col1" "y", "col2" "z", "B" "2" "C" "3" "D" "4"}
            {"col0" "a", "A" "a" "col1" "b", "col2" "c", "B" "20" "C" "30" "D" "40"}]]
           
           (sut/join-csv csv-1 {"col0" trim} {} "col0"
                                csv-3 {"A" trim} {} "A")))

    (is (= [[["A" "B" "C" "D"] ["col0" "col1" "col2"] ]
            [{"A" "x", "col0" "x" "col1" "y", "col2" "z", "B" "2" "C" "3" "D" "4"}
             {"A" "a", "col0" "a" "col1" "b", "col2" "c", "B" "20" "C" "30" "D" "40"}]]
           
           (sut/join-csv csv-3 {"A" trim} {} "A"
                                csv-1 {"col0" trim} {} "col0")))
    ))

(deftest t-merge-3
  (testing-with-timeout "merge-2"
    (is (= [[[ "col0" "col1" "col2"] ["A" "B" "C" "D"]]
            [{"col0" "x", "col1" "y", "col2" "z", "A" "x" "B" "2" "C" "3" "D" "4"}
            {"col0" "a", "col1" "b", "col2" "c", "A" "a" "B" "20" "C" "30" "D" "40"}
            {"col0" "a", "col1" "b", "col2" "c", "A" "a" "B" "200" "C" "300" "D" "400"}
            ]]
           
           (sut/join-csv csv-1 {"col0" trim} {} "col0"
                                csv-4 {"A" trim} {} "A")))
    ))


(deftest t-write-1
  (testing-with-timeout "write-1"
    (is (= "col0,col1,col2,A,B,C,D
x,y,z,x,2,3,4
a,b,c,a,20,30,40
a,b,c,a,200,300,400
"
           (sut/write-join-csv csv-1 {"col0" trim} {} "col0"
                               csv-4 {"A" trim} {} "A")))))

