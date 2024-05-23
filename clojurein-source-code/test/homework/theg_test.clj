(ns homework.theg-test
  (:require [homework.theg :as sut]
            [common.util :refer [member testing-with-timeout]]
            [clojure.test :refer [deftest is]]))


(deftest t-reversed-edges
  (testing-with-timeout "reversed edges"
    (is (= (sut/reversed-edges ())
           ()))
    (is (= (sut/reversed-edges '((1 2)))
           '((2 1))))
    (is (= (sut/reversed-edges '((1 2) (3 4)))
           '((2 1) (4 3))))
    
    (is (= (sut/reversed-edges [])
           ()))
    (is (= (sut/reversed-edges ['(1 2)])
           '((2 1))))
    (is (= (sut/reversed-edges ['(1 2) '(3 4)])
           '((2 1) (4 3))))
    (is (= (sut/reversed-edges [[1 2]])
           '((2 1))))
    (is (= (sut/reversed-edges [[1 2] [3 4]])
           '((2 1) (4 3))))))

(deftest t-building
  (testing-with-timeout "building adjacency list"
    (is (member 2 (get (sut/make-adj [[1 2]] false) 1)))
    (is (member 1 (get (sut/make-adj [[1 2]] false) 2)))
    (is (= (sut/make-adj [[1 2]] false)
           {1 #{2}
            2 #{1}}))
    (is (= (sut/make-adj [[1 2]] true)
           {1 #{2}}))
    
    (is (empty? (get (sut/make-adj [[ 1 2]] true) 2)))

    (is (= (sut/make-adj [[1 2] [2 3]] false)
           {1 #{2}
            2 #{1 3}
            3 #{2}}))
    (is (= (sut/make-adj '(("a" "b") ("b" "c") ("a" "c") ("b" "d")) true)
           {"a" #{"b" "c"}
            "b" #{"c" "d"}}))))



(deftest t-collect-connected-list
  (testing-with-timeout "collect connected vertices, list"
    (let [edges '((1  2) (3  4) (4  2))]
      (is (= (sut/reachable-vertices edges 1 false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  2  false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  3  false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  4  false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  1  true)
             #{1  2}))
      (is (= (sut/reachable-vertices edges  2  true)
             #{2}))
      (is (= (sut/reachable-vertices edges  3  true)
             #{3  4  2}))
      (is (= (sut/reachable-vertices edges  4  true)
             #{4  2})))))

(deftest t-collect-connected-vector
  (testing-with-timeout "collect connected vertices, vector"
    (let [edges [[1  2] [3  4]  [4  2]]]
      (is (= (sut/reachable-vertices edges 1 false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  2  false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  3  false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  4  false)
             #{1  2  3  4}))
      (is (= (sut/reachable-vertices edges  1  true)
             #{1  2}))
      (is (= (sut/reachable-vertices edges  2  true)
             #{2}))
      (is (= (sut/reachable-vertices edges  3  true)
             #{3  4  2}))
      (is (= (sut/reachable-vertices edges  4  true)
             #{4  2})))))


(deftest t-discovered-graph-list
  (testing-with-timeout "disconnected graph list"
    ;; now a disconnected graph
    (let [edges '((1 2) (1 3) (1 4) (4 1) 
                  (10.1 11) (10.1 12) (11 12))]
      (is (= (sut/reachable-vertices edges 11 false)
             #{10.1 11 12}))
      (is (= (sut/reachable-vertices edges 11 true)
             #{11 12}))
      (is (= (sut/reachable-vertices edges 1 false)
             #{1 2 3 4}))
      (is (= (sut/reachable-vertices edges 1 true)
             #{1 2 3 4})))))

(deftest t-discovered-graph-vector
  (testing-with-timeout "disconnected graph vector"
    ;; now a disconnected graph
    (let [edges [[1 2] [1 3] [1 4] [4 1]
                 [10.1 11] [10.1 12] [11 12]]]
      (is (= (sut/reachable-vertices edges 11 false)
             #{10.1 11 12}))
      (is (= (sut/reachable-vertices edges 11 true)
             #{11 12}))
      (is (= (sut/reachable-vertices edges 1 false)
             #{1 2 3 4}))
      (is (= (sut/reachable-vertices edges 1 true)
             #{1 2 3 4})))))

(deftest t-disconnected-graph-list
  (testing-with-timeout "disconnected graph by List"
    ;; now a disconnected graph
    (let [edges '((1 2) (1 3) (1 4) (4 1) 
                 (10 11) (10 12) (11 12))]
      (is (= (sut/reachable-vertices edges 11 false)
             #{10 11 12}))
      (is (= (sut/reachable-vertices edges 11 true)
             #{11 12}))
      (is (= (sut/reachable-vertices edges 1 false)
             #{1 2 3 4}))
      (is (= (sut/reachable-vertices edges 1 true)
             #{ 1 2 3 4})))))

(deftest t-disconnectted-graph-doubles
  (testing-with-timeout "disconnected graph with doubles"
    ;; now a disconnected graph
    (let [edges '((1.1 2.2) (1.1 3.3) (1.1 4.4) (4.4 1.1) 
                  (10.1 11.1) (10.1 12.2) (11.1 12.2))]
      (is (= (sut/reachable-vertices edges 11.1 false)
             #{10.1 11.1 12.2}))
      (is (= (sut/reachable-vertices edges 11.1 true)
             #{11.1 12.2}))
      (is (= (sut/reachable-vertices edges 1.1 false)
             #{1.1 2.2 3.3 4.4}))
      (is (= (sut/reachable-vertices edges 1.1 true)
             #{1.1 2.2 3.3 4.4})))))

(deftest t-disconnecdted-graph-list-string
  (testing-with-timeout "disconnected graph with List[String]"
    ;; now a disconnected graph
    (let [edges '(("rosalie" "gilbert") ("rosalie" "theophile") ("rosalie" "fred") ("fred" "rosalie") 
                                  ("germaine" "john") ("germaine" "emilienne") ("john" "emilienne"))]
      (is (= (sut/reachable-vertices edges "john" false)
             #{"germaine" "john" "emilienne"}))
      (is (= (sut/reachable-vertices edges  "john" true)
             #{"john" "emilienne"}))
      (is (= (sut/reachable-vertices edges "rosalie" false)
             #{"rosalie" "gilbert" "theophile" "fred"}))
      (is (= (sut/reachable-vertices edges  "rosalie" true)
             #{"rosalie" "gilbert" "theophile" "fred"})))))

(deftest t-disconnected-graph-vector-string
  (testing-with-timeout "disconnected graph with Vector[String]"
    ;; now a disconnected graph
    (let [edges [["rosalie" "gilbert"] ["rosalie" "theophile"] ["rosalie" "fred"] ["fred" "rosalie"]
                 ["germaine" "john"] ["germaine" "emilienne"] ["john" "emilienne"]]]
      (is (= (sut/reachable-vertices edges "john" false)
             #{"germaine" "john" "emilienne"}))
      (is (= (sut/reachable-vertices edges  "john" true)
             #{"john" "emilienne"}))
      (is (= (sut/reachable-vertices edges "rosalie" false)
             #{"rosalie" "gilbert" "theophile" "fred"}))
      (is (= (sut/reachable-vertices edges  "rosalie" true)
             #{"rosalie" "gilbert" "theophile" "fred"})))))

(deftest t-partition-by-dist-directed
  (testing-with-timeout "partition vertices by distance directed"
    (is (= {0 #{0}
            1 #{1 2}}
           (sut/partition-vertices-by-distance [[0 1] [0 2]] 0 true)))

    (is (= {0  #{0}
            1  #{1 2}
            2  #{3}}
           (sut/partition-vertices-by-distance '((0 1) 
                                                 (0 2) 
                                                 (1 2) 
                                                 (1 3))
                                               0 true)))

    (is (= {0   #{0}
            1   #{1 2}
            2   #{3 4}}
           (sut/partition-vertices-by-distance '((0 1) 
                                                 (0 2) 
                                                 (1 2) 
                                                 (1 3) 
                                                 (2 4))
                                               0 true)))

    (is (= {0   #{0}
            1   #{1}
            2   #{2}
            3   #{3}
            4   #{4}
            5   #{5}}
           (sut/partition-vertices-by-distance '((0 1) 
                                                 (1 2) 
                                                 (2 3) 
                                                 (3 4) 
                                                 (4 5)) 
                                               0 true )))

    (is (= {0   #{0}
            1   #{1 5}
            2   #{2}
            3   #{3}
            4   #{4}}
           (sut/partition-vertices-by-distance '((0 1)  (0 5) 
                                                 (1 2) 
                                                 (2 3) 
                                                 (3 4) 
                                                 (4 5))
                                               0 true)))))

(deftest t-partition-by-dist-undirected
  (testing-with-timeout "sut/partition-vertices-by-distance non-directed"
    (is (= {0   #{0}
            1   #{1 2}}
           (sut/partition-vertices-by-distance '((0 1) (0 2)) 
                                               0 false)))
    (is (= {0   #{0}
            1   #{1 2}
            2   #{3}}
           (sut/partition-vertices-by-distance '((0 1) 
                                                 (0 2) 
                                                 (1 2) 
                                                 (1 3))
                                               0 false)))

    (is (= {0   #{0}
            1   #{1 2}
            2   #{3 4}}
           (sut/partition-vertices-by-distance '((0 1) 
                                                 (0 2) 
                                                 (1 2) 
                                                 (1 3) 
                                                 (2 4))
                                               0 false)))

    (is (= {0   #{0}
            1   #{1}
            2   #{2 5}
            3   #{3 4}}
           (sut/partition-vertices-by-distance '((0 1) 
                                                 (1 2) 
                                                 (2 3) 
                                                 (3 4) 
                                                 (4 5) 
                                                 (5 1))
                                               0 false )))

    (is (= {0   #{0}
            1   #{1 5}
            2   #{2 4} 
            3   #{3}}
           (sut/partition-vertices-by-distance '((0 1)  (0 5) 
                                                 (1 2) 
                                                 (2 3) 
                                                 (3 4) 
                                                 (4 5))
                                               0 false)))

    (is (= {0   #{0}
            1   #{1 5}
            2   #{2 4 6}
            3   #{3 7}}
           (sut/partition-vertices-by-distance '((0 1)  (0 5) 
                                                 (1 2)  (1 6) 
                                                 (2 3)  (2 6) 
                                                 (3 4)  (3 7) 
                                                 (4 5) 
                                                 (6 7))
                                               0 false )))))
