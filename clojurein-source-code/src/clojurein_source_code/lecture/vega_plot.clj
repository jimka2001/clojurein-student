(ns clojurein-source-code.lecture.vega-plot
  (:require [oz.core :as oz]))

(defn series-scatter-plot
  "Pop up image in browser showing a graph with multiple curves.
  Each curve corresponds to a series specified in the given `data`."
  [chart-title x-label y-label data]
  ;; data is of form [[string [(x y) (x y) (x y) ...]]
  ;;                  [string [(x y) (x y) (x y) ...]]
  ;;                  ...]
  (let [polished-data (for [[label aseq] data
                            [x y] aseq]
                        {:x (float x)
                         :y (float y)
                         :series label})
        scatter-plot {:data {:values polished-data}
                      :title {:text chart-title}
                      :description chart-title
                      :encoding {:x {:field "x" :type "quantitative"}
                                 :y {:field "x" :type "quantitative"}
                                 :color {:field "series" :type "nominal"}}
                      :mark "symbol"}]
    (oz/view! scatter-plot)))
    
