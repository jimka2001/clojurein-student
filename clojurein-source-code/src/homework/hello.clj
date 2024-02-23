(ns homework.hello
  (:require [clojure.pprint :refer [cl-format]]))

;; The purpose of this function it to assure that the student knows
;; how to write a basic clojure function, matching parens quotation
;; marks etc, and that the student understands how to run the
;; test cases provided in the project.
(defn hello
  "This function computes a String, but does not print it.
  Given a string, who, such as \"Jim\", the function
  computes the string \"Hello, Jim.\", i.e.,
  Beginning with \"Hello\" with a capital H
  then a comma, \",\",
  then a space \" \",
  then the value of who
  and finally a period, \".\""
  [who]
  (throw (ex-info "Missing single expression, not yet implemented" {}))
  )

