(ns clojurein-source-code.homework.util
  (:require  [clojure.test :refer [is testing-contexts-str *testing-contexts*]]
             [clojure.pprint :refer [cl-format]]))

(def ^:dynamic *time-out* 2000)

(defn with-timeout [timeout-ms]
  (fn [f]
    (let [timeout-val ::test-timeout
          fut         (future (f))
          val         (deref fut timeout-ms timeout-val)]
      (if (= val timeout-val)
        (do
          (future-cancel fut)
          
          (is (= :success (cl-format nil "~A [~A] Test timed out after ~Ams!"
                                     (testing-contexts-str)
                                     *testing-contexts*
                                     timeout-ms))))
        val))))
