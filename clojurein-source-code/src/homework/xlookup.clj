(ns homework.xlookup
  (:require [clojure.data.csv :as csv]
            [common.util :refer [member with-output-to-string]]
            [clojure.set :refer [rename-keys]]
            [clojure.java.io :as io])
  )


(defn read-csv
  "Given a string representing the content of a csv file,
  return a pair [list-of-headers list-of-maps], where list-of-headers is the
  list of header strings, in order, and list-of-maps is one per line in the csv.
  whose keys are the headers on the first line, and whose values are the corresponding
  values in the column of the rest of the lines.
  `parsers` is a map of header to parser function.   the function should accept a string
  and return the interpreted value, the tbl will be populated by returns values
  from this function if given.   E.g., if the table contains a column named \"xyz\" with a value such
  as 1 in the column, then it is normally
  read as the string \"10\"; however if you would like this converted to an integer,
  you'll specify the function parse-long: {\"xyz\" parse-long}.
  If `headers` is `nil` then interpret the first (non-comment) line as the headers list.
  If `headers` is not nil, then interpret the first (non-comment) as csv data.
  "
  [csv & {:keys [parsers headers comment? separator]
          :as options
          :or {comment?  (fn [[start]] (= \# (get start 0)))
               separator \,
               parsers {}
               headers nil}
          }]
  (cond (string? csv)
        (let [tbl (filter (complement comment?) (csv/read-csv csv
                                                              :separator separator))
              headers (or headers (first tbl))
              body (if (:headers options)
                     tbl ;; if headers was given as :headers, the don't skip first line
                     (rest tbl))]
          [headers
           (for [line body]
             (into {} (map (fn [header cell]
                             ;; header is a header string which indicates the name
                             ;; of one of the csv columns.
                             ;; (get parsers header) returns nil or a function which will parse
                             ;;  the string in the csv column.  If (get parsers header) returns
                             ;;  nil, then the string value should not be *parsed*, otherwise
                             ;;  the parsing function should be called on the string in the cell
                             ;;  to obtain the desired value.
                             ;; CHALLENGE: student must complete the implementation.
                             (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
                             ;; HINT 2 line(s)
                             )
                           headers line)))])
        (= (type csv)
           java.io.BufferedReader)
        (read-csv (slurp csv) options)

        :else
        (throw (ex-info "read-csv cannot handle first argument"
                        {:csv csv
                         :type (type csv)}))
))

(defn rename-columns 
  "`headers` is a sequence of strings
  `lines` is a sequence of maps
  `rename` is a map of old-name to new-name
  Returns a pair which is a new sequence of headers and
  a sequence of maps, having the keys renamed according to the `rename` map"
  [headers lines rename]
  [(for [header headers]
     ;; CHALLENGE: student must complete the implementation.
     (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
     ;; HINT 1 line(s)
     )
   (for [line-map lines]
     ;; CHALLENGE: student must complete the implementation.
     (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
     ;; HINT 1 line(s)
     )])

(defn read-csv-and-rename
  [csv & {:keys [parsers headers comment? separator rename]
          :as options
          :or {rename {}}
          }]
  (let [[headers lines] (read-csv csv (dissoc options :rename))]
    (rename-columns headers lines rename)))


(defn join-csv
  "Return a sequence of tables, each table corresponds to a line from csv-string-1 merged with a line
  from csv-string-2 where the value of field-1 of csv-1 equals the value field-2 of csv-2.
  If field-1 is different than field-2, then both appear in the output table."
  [csv-1 parsers-1 rename-1 field-1
   csv-2 parsers-2 rename-2 field-2]
  (let [[headers-1 tbl-1] (read-csv-and-rename csv-1 :parsers parsers-1 :rename rename-1)
        [headers-2 tbl-2] (read-csv-and-rename csv-2 :parsers parsers-2 :rename rename-2)]
    [[headers-1 headers-2]
     (for [
           ;; CHALLENGE: student must complete the implementation.
           (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
           ;; HINT 5 line(s)
           ]
       ;; compute a new map containing all the keys of line-1 and line-2.
       ;; if line-1 and line-2 have a common key, then the value from line-2
       ;; is used and the value from line-1 is discarded.
       ;; CHALLENGE: student must complete the implementation.
       (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
       ;; HINT 1 line(s)
       )]))

(defn write-join-csv [csv-1 parsers-1 rename-1 field-1
                      csv-2 parsers-2 rename-2 field-2]
  (let [[[headers-1 headers-2] joined] (join-csv csv-1 parsers-1 rename-1 field-1
                                                 csv-2 parsers-2 rename-2 field-2)
        headers-3 (concat headers-1 (filter (fn [x] (not (member x headers-1))) headers-2))]
    (with-output-to-string writer
      (csv/write-csv writer (cons headers-3
                                  (for [m joined]
                                    (for [key headers-3]
                                      (get m key))))))))
