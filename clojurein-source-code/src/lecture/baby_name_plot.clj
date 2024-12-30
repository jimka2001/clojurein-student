(ns lecture.baby-name-plot
  (:require [clojure.string :refer [split]]
            [clojure.java.io :as io]
            [clojure.math :refer [round]]
            ;; [clojure.tools.trace :as trace] ;; (trace/untrace-ns 'oz.core)
            [common.util :refer [find-if]]
            [common.view :refer [view-image]]
            [common.vega-plot :as vp]
            ;;[cfft.core :refer [fft ifft]]
))



(defn smoothen
  "Accepts a sequence of [x y] pairs, where the x's increase from left to right,
  returns a sequence of [x y] pairs which represent a smoothened curve."
  [xys]
  (map (fn [[x1 y1] [x2 y2]]
         [(/ (+ x1 x2) 2.0)
          (/ (+ y1 y2) 2.0)])
       xys
       (rest xys)))



;; (defn smoothen-with-fft
;;   "Accepts a sequence of [x y] pairs, where the x's increase from left to right,
;;   returns a sequence of [x y] pairs which represent a smoothened curve."
;;   [xys]
;;   (let [f (fft (map second xys))
;;         num-freq (count f)
;;         clip (quot (int (round (* 0.20 num-freq)))
;;                    2)
;;         clipped (drop clip (drop-last clip f))
;;         s (map :real (ifft clipped))
;;         ]
;;     (map (fn [xy-1  y-2]
;;            [(first xy-1) y-2])
;;          xys s)))


(defn baby-name-plot
  "Generate plot of single baby name."
  [name-target gender-target state-target]
  (let [data (let [txt-file (format "US-baby-names/namesbystate/%s.TXT" state-target)
                   s (io/resource txt-file)]
               (assert s (format "resource not found: %s" txt-file))
               (with-open [r (io/reader s)]
                 (assert r (format "cannot create reader from %s" s))
                 ;; doall is necessary because (for ...) is lazy,
                 ;; without the doall, with-open will return a lazy sequence
                 ;; but close the file.  later when the call-site tries to iterate
                 ;; through the lazy-sequence, the file will be already closed and
                 ;; the iteration will fail.
                 (doall (for [line (line-seq r)
                              :let [[state-raw gender year-raw name-raw count-raw]
                                    (split line #"[,]")]
                              :when (= state-raw state-target)
                              :when (= name-raw name-target)
                              :when (= gender gender-target)]
                          ;; collect an [x y] to plot
                          [(Integer/parseInt year-raw)
                           (Integer/parseInt count-raw)]))))]

    (vp/series-scatter-plot "Baby Name data" ;; chart-title
                            "Year" ;; x-label
                            "Total Names" ;; y-label
                            [[(format "%s-%s-%s" name-target gender-target state-target)
                              ;; we must sort by `first` because there's no guarantee that the
                              ;; years are in increasing order
                              (smoothen (sort data))]])))
          
(defn sample-plot-1 []
  (view-image (baby-name-plot "John" "M" "CA")))

;;(sample-plot-1)


(defn baby-name-normalized-data
  "return a seqeunce of pairs [year percentage] where percentage measures
  the fraction of babies born in the specified year having the name `name-target`
  and the gender `gender-target` in the state `state-target`.
  For example a point if [\"John\" \"M\" \"MS\"] is specified,
  then [1970 4.3] means that in 1970 4.3 percent of all boy babies
  born in Mississippi were named John."
  [name-target gender-target state-target]
  (let [triples (let [txt-file (format "US-baby-names/namesbystate/%s.TXT" state-target)
                      s (io/resource txt-file)]
                  (assert s (format "resource not found: %s" txt-file))
                  (with-open [r (io/reader s)]
                    (assert r (format "cannot create reader from %s" s))
                    ;; doall is necessary because (for ...) is lazy,
                    ;; without the doall, with-open will return a lazy sequence
                    ;; but close the file.  later when the call-site tries to iterate
                    ;; through the lazy-sequence, the file will be already closed and
                    ;; the iteration will fail.
                    (doall (for [line (line-seq r)
                                 :let [[state gender year-raw name count-raw]
                                       (split line #"[,]")]
                                 :when (= state state-target)
                                 :when (= gender gender-target)]
                             ;; collect a hash indicating one year/name/count triple
                             {:year (Integer/parseInt year-raw)
                              :name name
                              :count (Integer/parseInt count-raw)}))))
        ;; group the hashes by year
        grouped (group-by :year triples)
        xys (for [[year triples] grouped
                  ;; skip if there fails to be at least one baby with the given name
                  found-triple (find-if (fn [triple] (= (:name triple) name-target))
                                        triples)
                  ;; add up all the babies born this year, all names included
                  :let [born-count (reduce + (map :count triples))]]
               ;; collect an [x y] pair to plot
              [year (/ (* 100.0 (:count found-triple))
                       born-count)])]
    ;; we must sort by `first` because there's no guarantee that the
    ;; years are in increasing order
    (smoothen (sort xys))))
  

(defn plot-baby-names-normalized
  "Plot zero or more curves indicating normalized (percentaged) of babies
  born in particular states of a specified gender.
  `chart-title` specified the name of the curve
  `triples` is a sequence of zero or more triples, each of the form
  [name gender state]  e.g., [\"John\" \"M\" \"MS\"]
  "
  [chart-title triples]
  (vp/series-scatter-plot chart-title
                          "Year" ;; x-label
                          "Percentage" ;; y-label
                          (for [[name-target gender-target state-target] triples]
                            [(format "%s-%s-%s" name-target gender-target state-target)
                             (baby-name-normalized-data name-target gender-target state-target)])))

(defn sample-plot-2 []
  (view-image (plot-baby-names-normalized "Baby Names 1" [["Juan" "M" "MS"]
                                                          ["Juan" "M" "CA"]]))
  (view-image (plot-baby-names-normalized "Baby Names 2" [["Blanche" "F" "MS"]
                                                          ["Minnie"  "F" "MS"]]))
  (view-image (plot-baby-names-normalized "Baby Names 3" [["John","M","NY"]]))
  (view-image (plot-baby-names-normalized "Baby Names 4" [["Arnold" "M" "FL"]
                                                          ["Arnold" "M" "CA"]])))

;;(sample-plot-2)
