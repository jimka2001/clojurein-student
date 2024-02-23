(ns lecture.vega-plot
  (:require [oz.core :as oz]
            [clojure.pprint :refer [pprint]]
            [clojure.java.browse :refer [browse-url]]
            [clojure.java.shell :refer [sh]]
            [clojure.tools.trace :as trace]))

(defn series-format-plot-data 
  "encode plotting data into an hashmap ready to pass to oz/view!"
  [chart-title x-label y-label data]
  ;; data is of form [[string [(x y) (x y) (x y) ...]]
  ;;                  [string [(x y) (x y) (x y) ...]]
  ;;                  ...]

  (let [polished-data (for [[label aseq] data
                            [x y] aseq]
                        {:x (float x)
                         :y (float y)
                         :series label})]
    {:data {:values polished-data}
     :title {:text chart-title}
     :description chart-title
     :axes [{:orient "bottom"
             ;; TODO this is NOT setting the label
             :title x-label}
            {:orient "left"
             ;; TODO this is NOT setting the label
             :title y-label}]
     :encoding {:x {:field "x" ;; x-label
                    :type "quantitative"}
                :y {:field "y" ;; y-label
                    :type "quantitative"}
                :color {:field "series"
                        :type "nominal"}}
     :mark "line"}))

(defn series-scatter-plot
  "Pop up image in browser showing a graph with multiple curves.
  Each curve corresponds to a series specified in the given `data`."
  [chart-title x-label y-label data]
  ;; data is of form [[string [(x y) (x y) (x y) ...]]
  ;;                  [string [(x y) (x y) (x y) ...]]
  ;;                  ...]
  (let [tmp  (.getAbsolutePath (java.io.File/createTempFile chart-title ".svg"))
        formatted-data (series-format-plot-data chart-title x-label y-label data)]

    (oz/export! formatted-data tmp)
    tmp))

(defn browse-image [file-name]
  (browse-url (str "file://" file-name)))

(defn view-image [file-name]
  (case (System/getProperty "os.name")
    ("Mac OS X")
    (sh "open" "-a" "Google Chrome" file-name)

    :otherwise
    (browse-image file-name)))


