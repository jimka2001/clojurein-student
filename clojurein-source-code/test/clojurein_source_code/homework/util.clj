(ns clojurein-source-code.homework.util
  (:require  [clojure.test :refer [is testing testing-contexts-str *testing-contexts*]]
             [clojure.pprint :refer [cl-format]]))

(def ^:dynamic *time-out* (* 2 60 1000)) ;; 2 seconds

(defn call-with-timeout [timeout-ms f]
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
      val)))

(defmacro testing-with-timeout [msg & body]
  `(testing ~msg
     (call-with-timeout *time-out*
                        (fn []
                          ~@body))))
