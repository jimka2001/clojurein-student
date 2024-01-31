(ns clojurein-source-code.lecture.baby-name-plot
  (:require [clojure.string :refer [split]]
            [clojure.java.io :as io]
            [clojurein-source-code.common.util :refer [find-if]]
            [clojurein-source-code.lecture.vega-plot :as vp]))

(defn baby-name-plot
  "Generate plot of single baby name."
  [name-target gender-target state-target]
  (let [data (let [txt-file (format "US-baby-names/namesbystate/%s.TXT" state-target)
                   s (io/resource txt-file)]
               (assert s (format "resource not found: %s" txt-file))
               (with-open [r (io/reader s)]
                 (assert r (format "cannot create reader from %s" s))
                 (doall (for [line (line-seq r)
                              :let [[state gender year-raw _name count-raw]  (split line #"[,]")]
                              :when (= state state-target)
                              :when (= gender gender-target)]
                          [(Integer/parseInt year-raw) (Integer/parseInt count-raw)]))))]
    (vp/series-scatter-plot "Baby Name data" ;; chart-title
                            "Year" ;; x-label
                            "Total Names" ;; y-label
                            [[(format "%s-%s-%s" name-target gender-target state-target) data]])))
          
(defn sample-plot-1 []
  (baby-name-plot "John" "M" "CA"))

;; (sample-plot-1)

(defn baby-name-normalized-data
  "return a seqeunce of pairs [year percentage] where percentage measure
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
                    (doall (for [line (line-seq r)
                                 :let [[state gender year-raw name count-raw]  (split line #"[,]")]
                                 :when (= state state-target)
                                 :when (= gender gender-target)]
                             [(Integer/parseInt year-raw) name (Integer/parseInt count-raw)]))))
        grouped (group-by first triples)]
    (for [[year triples] grouped
          found-triple (find-if (fn [[_ name _]] (= name name-target))
                                triples)
          :let [born-count (reduce + (map (fn [[_ _ count]] count) triples))]]
      [year (/ (* 100.0 (nth found-triple 2))
               born-count)])))
  

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
  (plot-baby-names-normalized "Baby Names 1" [["Juan" "M" "MS"]
                                            ["Juan" "M" "CA"]])
  (plot-baby-names-normalized "Baby Names 2" [["Blanche" "F" "MS"]
                                            ["Minnie"  "F" "MS"]])
  (plot-baby-names-normalized "Baby Names 3" [["John","M","NY"]])
  (plot-baby-names-normalized "Baby Names 4" [["Arnold" "M" "FL"]
                                              ["Arnold" "M" "CA"]]))

;;(sample-plot-2)
