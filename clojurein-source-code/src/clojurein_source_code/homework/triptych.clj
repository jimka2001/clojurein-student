(ns clojurein-source-code.homework.triptych
  (:require [clojure.set :refer [union intersection difference]]
            ;; [clojure.pprint :refer [cl-format]]
            [clojurein-source-code.common.util :refer [re-chunk member]]))

(def colors  #{:red :purple :green})
(def shapes #{:oval :squiggle :diamond})
(def numbers #{:one :two :three})
(def shadings #{:solid :striped :outlined})
(def features #{colors  shapes  numbers  shadings})

;; The sequence of all possible 81 cards.
(def deck
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn card?
  "This is a valid card if for each feature
   there is exactly one element of card which is in feature.
   i.e.  exactly one color  exactly one shape  etc."
  [card]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn feature-compatible?
  "Given a feature such as color or shape 
   determine whether the given set of cards
   are compatible w.r.t that feature.
   E.g.  are they all the same color  or all they all different color"
  [feature cards]
  (assert (= 3 (count cards)))
  ;; intersect each card with the feature, and union this intersection
  ;; for each card.  this will result in either 3 different values or
  ;; will result in 1 value in which case the cards ARE feature-compatible.
  ;; However, if it results in a set of size 2,
  ;; these the cards are NOT feature compatible.
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )


(defn compatible?
  "Given a set of 3 cards  decide whether they are feature
  compatible for all features: colors  numbers  shapes  shadings"
  [cards]
  (assert (set? cards))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )


(defn triptych? [cards]
  "We represent a card as a Set of 4 strings, one string representing color,
   one string representing shape, one string representing number, and one
   string representing shading. triptych? should return true if its
   argument is a Triptych"
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn complete-triptych
  "given two cards, c1 and c2, return the uniquely determined 3rd card, c3,
  for which #{c1, c2, c3} is a Triptych."
  [c1 c2]
  (assert (card? c1) c1)
  (assert (card? c2) c2)
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn find-all-triptychs-as-set
  "Given a set of 0 or more cards  return a set of all
  Triptychs which can be formed.  Beware that sometimes the
  same card can appear in two (or more) different Triptychs.
  When such is the case each such Triptych should be included
  in the return value set."
  [cards]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn find-triptych
  "Given a set of 0 or more cards  return nil if there are no three
  cards which form a valid Triptych,  otherwise return
  that Triptych.  Be aware that more that one Triptych may
  exist among the given cards.  But only one should be
  returned."
  [cards]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn cap?
  "Return Boolean indicating whether the given set or seq of cards
  is a cap.  It is a cap if it contains no three cards which
  form a triptych."
  [cards]
  (empty? (re-chunk 1 (find-all-triptychs-as-seq cards)))
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

(defn grow-cap-by-one
  "returns a lazy-seq of caps.  each cap
  is the given cap plus one additional card from partial-deck"
  [cap partial-deck]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )

;; Given a set of cards and a target size between 3 and 18 inclusive,
(defn find-cap
  "find a subset of the given size consisting of cards which contain no
  Triptych.
  Returns nil if no such set exists of the specified size"
  [cards target-size]
  ;; CHALLENGE: student must complete the implementation.
  (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
  )
