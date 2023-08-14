(ns clojurein-source-code.homework.recursion
  (:require [clojurein-source-code.homework.challenge :refer [???]]))


(defn sum-by-simple-recursion
  ""
  [nums]
  (if (empty? nums)
    0
    (+ (???)
       (sum-by-simple-recursion (???)
                                ))))

(defn sum-by-loop-recur
  ""
  [nums]
  (loop [acc 0
         nums nums]
    (if (empty? nums)
      (???)
      (recur (???)
             (???)
             ))))


(defn sum-by-reduce
  ""
  [nums]
  (reduce (???)
          (???)
          ))

(defn product-by-simple-recursion
  ""
  [nums]
  (if (empty? nums)
    (???)
    ((???)
                 (???)
                 (???)
                 )))

(defn product-by-loop-recur
  ""
  [nums]
  (loop [acc (???)
         nums (???)
         ]
    (if (empty? (???)
                )
      (???)
      (recur (???)
             (???)
             ))))


(defn product-by-reduce
  ""
  [nums]
  (reduce (???)
          (???)
          ))
