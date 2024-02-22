(ns clojurein-source-code.lecture.sample-vega
  (:require [oz.core :as oz]
            [clojurein-source-code.lecture.vega-plot :as vp]))

;; This example comes directly from
;; https://github.com/metasoarous/oz


(defn play-data [& names]
  (for [n names
        i (range 20)]
    {:time i
     :item n
     :quantity (+ (Math/pow (* i (count n)) 0.8)
                  (rand-int (count n)))}))

(def line-plot
  {:data {:values (play-data "monkey" "slipper" "broom")}
   :encoding {:x {:field "time" :type "quantitative"}
              :y {:field "quantity" :type "quantitative"}
              :color {:field "item" :type "nominal"}}
   :mark "line"})

;; Render the plot
;;(oz/view! line-plot)

;;(oz/export! line-plot "/tmp/xyz.svg")

(defn simple-test-vp []
  (vp/series-format-plot-data "testing chart"
                              "this is the x axis"
                              "this is the y axis"
                              [["1st-curve" '[(0 0) (1 1) (2 3)]]
                               ["2nd-curve" '[(0.5 2.1) (1.1 0.6) (1.6 0.3) (2.1 0.1)]]]))



;; (println (oz/view! (simple-test-vp)))
;; (browse-image "/tmp/file.svg")
(println [:data (simple-test-vp)])
(oz/export! (simple-test-vp) "/tmp/file3.svg")
(vp/view-image "/tmp/file3.svg")



