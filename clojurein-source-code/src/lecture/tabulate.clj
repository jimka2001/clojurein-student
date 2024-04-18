(ns lecture.tabulate)


(defn tabulate [n f]
  (mapv f (range n)))

(tabulate 3 dec)
