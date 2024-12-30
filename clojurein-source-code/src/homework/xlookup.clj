(ns homework.xlookup
  (:require [clojure-csv.core :as csv]
            [clojure.java.io :as io])
  )


(defn read-csv-string
  "Similar to read-csv, except that the first argument is a string
  containing the csv file content, rather than a file name"
  [csv-as-string parsers rename]
  (let [tbl (csv/parse-csv csv-as-string)
        headers (first tbl)
        body (rest tbl)]
    (for [line body]
      (into {} (map (fn [header cell] 
                      ;; CHALLENGE: student must complete the implementation.
                      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
                      ;; HINT 2 line(s)
                      )
                    headers line)))))

(defn file-content-as-string [fname]
  (with-open [r (or (io/reader fname)
                    (throw (ex-info "cannot open" {:fname fname})))]
    (read-csv-string (slurp r))))
  
(defn read-csv
  "Given the path to a csv file, return a list of maps (one per line in the csv)
  whose keys are the headers on the first line, and whose values are the corresponding
  values in the column of the rest of the lines.
  `parsers` is a map of header to parser function.   the function should accept a string
  and return the interpreted value.
  `rename` is a map to rename headers, mapping name as read from file, to new name in returned
  data structure"
  [csv-fname parsers rename]
  (read-csv-string (file-content-as-string csv-fname) parsers rename))

(defn join-csv-string [csv-string-1 parsers-1 rename-1 field-1
                       csv-string-2 parsers-2 rename-2 field-2]
  (let [tbl-1 (read-csv-string csv-string-1 parsers-1 rename-1)
        tbl-2 (read-csv-string csv-string-2 parsers-2 rename-2)]
    (for [
          ;; CHALLENGE: student must complete the implementation.
          (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
          ;; HINT 5 line(s)
          ]
      ;; CHALLENGE: student must complete the implementation.
      (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
      ;; HINT 1 line(s)
      )))


(defn join-csv [csv-fname-1 parsers-1 rename-1 field-1
                csv-fname-2 parsers-2 rename-2 field-2]
  (join-csv-string (file-content-as-string csv-fname-1) parsers-1 rename-1 field-1
                   (file-content-as-string csv-fname-2) parsers-2 rename-2 field-2))
