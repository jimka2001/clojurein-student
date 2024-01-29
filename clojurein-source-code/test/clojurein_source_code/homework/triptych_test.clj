(ns clojurein-source-code.homework.triptych-test
  (:require [clojurein-source-code.homework.triptych :as sut]
            [clojurein-source-code.homework.util :refer [*time-out* testing-with-timeout]]
            [clojure.set :refer [union intersection difference subset?]]
            [clojure.pprint :refer [cl-format]]
            [clojurein-source-code.common.util :refer [almost-equal time-call]]
            [clojure.test :refer [deftest is]]))

(defmacro test-testing [name & exprs]
  `(testing-with-timeout ~name
     (binding [*time-out* 2000]
       (println [:testing ~name])
       (print "   ")
       (time (do ~@exprs)))))

(defn extract-feature [feature-values card]
  (first (intersection feature-values card)))

(defn control-find-all-triptychs
  [cards]
  (set (for [c1 cards
             c2 cards
             :when (not= c1 c2)
             :let [c3 (sut/complete-triptych c1 c2)]
             :when (contains? cards c3)]
         #{c1 c2 c3})))


(defn extract-triptychs
  "Given a remaining-deck (list of cards) and a table (set of cards)
  attempt to find a triptych on the table, remove those cards from
  the table, and deal three more cards from remaining-deck onto the
  table, and try again.  If there are not three cards from in the remaining-deck,
  then 3 cards cannot be dealt, so deal them all onto the table.
  Repeat until all the cards from the deck are exhausted.
  Return a 2-vector containing the set of triptychs
  found, and the remaining cards on the table."
  [remaining-deck table]
  (is (seq? remaining-deck)
      (cl-format nil "expecting list, got ~A: ~A" (type remaining-deck) remaining-deck))
  (is (set? table))
  
  (loop [remaining-deck remaining-deck
         table table
         found-triptychs #{}]

    (let [triptychs (sut/find-all-triptychs-as-seq table)]
      (if (empty? triptychs)
        [found-triptychs table]
        (let [triptych (first triptychs)
              deal-size (max 3 (count remaining-deck))]
          (recur (drop deal-size remaining-deck)
                 (union (difference table triptych)
                        (set (take deal-size remaining-deck)))
                 (conj found-triptychs triptych)))))))


(deftest t-deck-size
  (test-testing "deck size"
    (is (= 81 (count sut/deck)))))

(deftest t-deck-cards
  (test-testing "deck cards"
    (doseq [card sut/deck]
      (is (set? card)
          (cl-format nil "~A not a valid card" card))
      (is (= 4 (count card))
          (cl-format nil "~A not a valid card" card))
      (is (every? keyword? card)
          (cl-format nil "~A not a valid card" card))
      (is (= 1 (count (intersection sut/colors card)))
          (cl-format nil "~A not a valid card" card))
      (is (= 1 (count (intersection sut/numbers card)))
          (cl-format nil "~A not a valid card" card))
      (is (= 1 (count (intersection sut/shadings card)))
          (cl-format nil "~A not a valid card" card))
      (is (= 1 (count (intersection sut/shapes card)))
          (cl-format nil "~A not a valid card" card))
      (is (sut/card? card)
          (cl-format nil "~A not a valid card" card)))))


(deftest t-triptych
  (test-testing "test is triptych"
    (is (sut/triptych? #{#{:oval :red :one :solid},
                         #{:oval :green :one :solid},
                         #{:oval :purple :one :solid}}))

    (is (sut/triptych? #{#{:squiggle :red :striped :three},
                         #{:oval :purple :solid :two},
                         #{:diamond :green :outlined :one}}))

    ;; not three cards
    (is (not (sut/triptych? #{#{:oval :red :one :solid},
                              #{:oval :purple :one :solid}})))
    
    ;; not number-compatible
    (is (not (sut/triptych? #{#{:squiggle :red :striped :three},
                              #{:oval :purple :solid :one},
                              #{:diamond :green :outlined :one}})))
    ;; invalid feature values
    (is (not (sut/triptych? #{#{:w :x :y :z},
                              #{:a :b :c :d},
                              #{:1 :2 :3 :4}})))))


(deftest t-complete-triptych-0
  (test-testing "complete triptych 0"
    (is (= #{:red :squiggle :solid :three}
           (sut/complete-triptych #{:red :squiggle :solid :two},
                                  #{:red :squiggle :solid :one})))
    (is (= #{:oval :red :striped :three}
           (sut/complete-triptych #{:oval :purple :solid :one},
                                  #{:oval :green :outlined :two})))
    (is (= #{:red :diamond :three :solid}
           (sut/complete-triptych #{:red :oval :one :solid},
                                  #{:red :squiggle :two :solid})))))

(deftest t-complete-triptych-1
  (test-testing "complete triptych 1"
    (doseq [c1 sut/deck
            c2 sut/deck
            :when (not= c1 c2)
            :let [c3 (sut/complete-triptych c1 c2)
                  candidate #{c1 c2 c3}]]
      (is (sut/triptych? candidate)))))



(deftest t-find-all-triptychs
  (test-testing "test find-all-triptychs"
    (let [shuffled (into () (shuffle sut/deck))
          [triptychs remaining] (extract-triptychs (drop 12 shuffled)
                                                   (set (take 12 shuffled))
                                        )]
      (doseq [triptych triptychs]
        (is (sut/triptych? triptych)))
      (is (empty? (sut/find-all-triptychs-as-seq remaining))))))

(deftest t-overlapping
  (test-testing "overlapping triptychs"
                ;;      color shape    shading number
    (let [triptych1 #{#{:red :squiggle :solid :two},
                      #{:red :squiggle :solid :one},
                      #{:red :squiggle :solid :three}}
          triptych2 #{#{:red :squiggle :solid :two},
                      #{:red :diamond  :solid :one},
                      #{:red :oval     :solid :three}}
          triptychs (sut/find-all-triptychs-as-set (union triptych1 triptych2))]
      (is (subset? #{triptych1 triptych2} triptychs)
          (cl-format nil "did not find all the triptychs:~%found ~A,~% expecting ~A"
                     triptychs #{triptych1 triptych2}))

      (is (= nil (sut/find-triptych #{})))
      (let [triptych (sut/find-triptych (union triptych1 triptych2))]
        (is triptych "find-triptych should have found an triptych in this case")
        (is (contains? #{triptych1 triptych2} triptych))))))



(deftest t-5-cap-a
  (test-testing "5-cap a"
    (is (not (sut/find-triptych #{#{:green :squiggle :one :solid}
                                  #{:green :oval :two :solid}
                                  #{:green :diamond :two :solid}
                                  #{:purple :oval :one :solid}
                                  #{:red :squiggle :one :solid}})))
    (is (not (sut/find-triptych #{#{:green :oval :one :solid}
                                  #{:green :oval :two :solid}
                                  #{:green :diamond :one :solid}
                                  #{:purple :oval :one :solid}
                                  #{:red :squiggle :two :solid}})))
    (is (sut/find-triptych #{#{:green :oval :one :solid}
                             #{:green :oval :two :solid}
                             #{:green :diamond :one :solid}
                             #{:purple :oval :one :solid}
                             #{:red :squiggle :one :solid}}))))

(deftest t-5-cap-b
  (test-testing "5-cap b"
    (is (not (sut/find-triptych #{#{:red :squiggle :one :solid},
                                    #{:red :squiggle :three :outlined},
                                    #{:red :oval :two :striped},
                                    #{:green :oval :one :solid},
                                  #{:purple :oval :two :striped}})))))

(deftest t-5-cap-c
  (test-testing "5-cap c"
    (is (sut/find-cap #{#{:red :squiggle :one :solid}
                        #{:red :squiggle :three :outlined}
                        #{:red :oval :two :striped}
                        #{:green :oval :one :solid}                        
                        #{:purple :oval :two :striped}}
                      3))))
 

(defn test-cap
  [n]
  (let [cap (sut/find-cap (set sut/deck) n)]

    (is cap
        (cl-format nil "failed to find a ~A-cap from: deck" n))
    (is (= n (count cap))
        (cl-format nil "found cap of wrong size ~A expecting ~A" (count cap) n))
    (let [triptych (sut/find-triptych cap)]
      (is (not triptych)
          (cl-format nil "~&~
                        found triptych in what is expected to be a cap~@
                        cap = ~A~@
                        triptych = ~A" triptych cap)))))



(defn test-cap-subset
  [target-size]
  (let [small-deck #{#{:oval :red :solid :one},
                     #{:oval :red :solid :two},
                     #{:oval :red :striped :one},
                     #{:oval :purple :solid :one},
                     #{:squiggle :red :solid :one}}
        cap1  (sut/find-cap small-deck 3)]
    (is (subset? cap1 small-deck)
        (cl-format nil "found ~A which is not a subset of ~A"
                   cap1 small-deck))
    (doseq [d  (range (inc target-size) (count sut/deck))
            :let [small-deck (set (take d sut/deck))
                  cap (sut/find-cap small-deck target-size)]]

      (when cap
        (is (set? cap))
        (is (every? sut/card? cap))
        (is (subset? cap small-deck)
            (cl-format nil "found ~A is not a subset of ~A" cap small-deck))))))

(deftest t-cap-is-subset-3
  (test-testing "cap is subset 3"
    (test-cap-subset 3)))

(deftest t-cap-is-subset-5
  (test-testing "cap is subset 5"
    (test-cap-subset 5)))

(deftest t-find-cap
  (test-testing "find cap"
    (let [cap (sut/find-cap #{#{:red :squiggle :one :solid},
                              #{:red :squiggle :two :solid},
                              #{:red :squiggle :three :solid},
                              #{:green :oval :one :solid}}
                            3)]
      (is cap "should have found a non-triptych")
      (is (not (sut/find-triptych cap))))))


(deftest t-3-cap
  (test-testing "cap 3"
    (test-cap 3)))

(deftest t-4-cap
  (test-testing "cap 4"
    (test-cap 4)))

(deftest t-5-cap
  (test-testing "cap 5"
    (test-cap 5)))

(deftest t-6-cap
  (test-testing "cap 6"
    (test-cap 6)))

(deftest t-7-cap
  (test-testing "cap 7"
    (test-cap 7)))

(deftest t-8-cap
  (test-testing "cap 8"
    (test-cap 8)))

(deftest t-9-cap
  (test-testing "cap 9"
    (test-cap 9)))

(deftest t-10-cap
  (test-testing "cap 10"
    (test-cap 10)))

;; (deftest t-11-cap
;;   (test-testing "cap 11"
;;     (test-cap 11)))

;; (deftest t-16-cap
;;  (test-testing "cap 16"
;;    (test-cap 16)))

;; (deftest t-17-cap
;;   (test-testing "cap 17"
;;     (test-cap 17)))

;; (deftest t-18-cap
;;   (test-testing "cap 18"
;;     (test-cap 18)))

