(ns homework.frenchnames
  (:require [clojure.string :refer [lower-case split index-of]]
            [clojure.java.io :as io]
            [clojure.pprint :refer [cl-format]]))

(defn elide-string
  "Given a string `name`, return a new string having certain characters with
  diacritical marks replaced with with simple characters.
  A character may be replaced with itself, with one character, or in some
  cases with two characters.
   E.g.,  'æ' => \"ae\"
          'â' | 'ä' | 'à' => \"a\"
          'è' | 'é' | 'ë' | 'ê' => \"e\"
   etc for every special character in the file
  This function throws an error when an unhandled character is encountered.
  The student should fix the function that that no unhandled characters
  occur in the test cases.
   "
  [name]
  (reduce str ""
          (mapcat (fn [c]
                    (let [new-string
                          (if (or (<= (int \a) (int c) (int \z))
                                  (<= (int \A) (int c) (int \Z))
                                  (= c \-)
                                  (= c \'))
                            (str c)
                            (case c
                              (\æ) "ae"
                              (\â  \ä \à) "a"
                              (\ç) "c"
                              ;; CHALLENGE: student must complete the implementation.
                              (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
                              ;; HINT 5 line(s)
                              ))]
                      (if new-string
                        new-string
                        (throw (ex-info (cl-format false "unknown character [~A] [~A]" c (int c))
                                        {:int (int c)
                                         :char c})))))
                  name)))

(defn baby-names-per-year
  ;; this function returns a Map which associates a year to count, where count is
  ;; the number of babies named name-target in that year of the specified
  ;; gender.  E.g., if
  ;; fred = (baby-names-per-year "fred" "M"), then (fred 2012)==100 means
  ;; there were 100 boy babies born named fred in 2012."
  ([name-target gender-target]
   (throw (ex-info "Missing single expression, not yet implemented" {}))
   )

  ;; This function returns a hash-map which associates
  ;;   to each year (key) the number of babies born (value) in that
  ;;   year of the given gender and the given name.
  ;;
  ;; Some French names can be spelled using different characters.
  ;; E.g., jerome vs jérôme vs jérome
  ;; This function has a Boolean flag indicating whether to elide different
  ;; spellings into one.  if elide=false, then "jerome" and "jérôme" are
  ;; considered different names, thus the Map returned might have keys
  ;; "jerome" and "jérôme".  If elide=true, then "jerome" and "jérôme"
  ;; are considered the same, in which case the Map returned will have
  ;; key "jerome" but not "jérôme" ... babies named "jérôme" are considered
  ;; to have the name "jerome" when elide=true.
  ;;
  ;; If elide is true, then the Map returned will not contain key "jérôme",
  ;;    but it may contain "jerome", and the total count for each year
  ;;    will contain the total of the babies named "jérôme" + babies named
  ;;    "jérome" + babies named "jerome" etc.
  ;; If elide is false, then the Map may contain keys "jérôme", "jérome",
  ;;    "jerome" etc.
  ([name-target gender-target elide]
   (assert (= name-target (lower-case name-target))
           (cl-format false "baby-names-per-year must be called with lower case name, not [~A]" name-target))
   (let [s (io/resource "France-baby-names/nat2020.csv")]
     (with-open [r (io/reader s)]
       ;; remember to drop the first line of file: sexe;preusuel;annais;nombre
       ;; skip lines where name is _PRENOMS_RARES
       ;; skip lines where the year is XXXX
       ;; names are in caps (e.g., FABIENNE) but nameTarget will be given as lower case (e.g., fabienne)
       
       ;; CHALLENGE: student must complete the implementation.
       (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
       ;; HINT 24 line(s)
       ))))


(defn count-names-in-year-range
  "count the number of babies born between year-min and year-max (including both year-min and year-max)
   of the given gender and name.
   Return an integer.
   The parameter elide, indicates (as above) whether to consider names
    such as \"jerome\" and \"jérôme\" as the same or different.
   If elide is true, then \"jerome\" and \"jérôme\" are considered the same.
   If elide is false, then \"jerome\" and \"jérôme\" are considered to be different names.
   "
  [name gender year-min year-max elide]
  (let [names (baby-names-per-year name gender elide)]
    ;; CHALLENGE: student must complete the implementation.
    (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
    ;; HINT 4 line(s)
    ))

(defn hyphenated-names
  "Many French names are hyphenated such as \"jean-albert\" and \"anne-marie\".
   Given a name such as \"jean\" return all other names X such that \"jean-X\" or \"X-jean\"
   is a name registered in the resource data base.
   We only care about exact names.  for example \"jean-jerome\" and \"jean-jérôme\" are different.
   Do not attempt to elide names.
   Careful!  If the given base-name never appears as some hyphenated form,
     then the empty set should be returned.
   Careful!  Some names have multiple hyphens:  e.g., abd-el-kader
   Ignore any line for which year = XXXX.
   WARNING some name might be hyphenated with itself.  E.g. jean-jean or marie-marie.
   Read the instructions carefully to understand what to do in this case."
  [base-name]
  (assert (= base-name (lower-case base-name))
          (cl-format false "hyphenated-names must be called with lower case name, not [~A]" base-name))
  (let [s (io/resource "France-baby-names/nat2020.csv")]
    (with-open [r (io/reader s)]

      ;; remember to drop the first line of file: sexe;preusuel;annais;nombre
      ;; skip lines where name is _PRENOMS_RARES
      ;; skip lines where the year is XXXX
      ;; names are in caps (e.g., FABIENNE) but name-target will be given as lower case (e.g., fabienne)
      (set (for
               ;; CHALLENGE: student must complete the implementation.
               (throw (ex-info "Missing one or more expressions, not yet implemented" {}))
               ;; HINT 14 line(s)
             )
       )
      )))
