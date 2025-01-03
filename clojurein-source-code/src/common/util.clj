(ns common.util
  (:require  [clojure.test :refer [do-report testing testing-contexts-str *testing-contexts* assert-expr]]
             [clojure.pprint :refer [cl-format]]))

(defn tails
  "Return a lazy list of tails of the given collection."
  [coll]
  (lazy-seq (if (empty? coll)
              ()
              (cons coll (tails (rest coll))))))

(defn time-call
  "Evaluates thunk and returns a 2-vector [value elapsed-time]
  where value is the return value of the thunk and elapsed-time
  is the number of nano it took evaluating the function."
  [thunk]
  (let [start (. System (nanoTime))
        value (thunk)
        stop (. System (nanoTime))]
    [value (- stop start)]))


(defn member
  "Determines whether the given target is an element of the given sequence (or given set)."
  [target items]
  (boolean (cond
             (empty? items) false
             (nil? target) (some nil? items)
             (false? target) (some false? items)
             (set? items) (contains? items target)
             :else (reduce (fn [_acc item]
                             (if (= item target)
                               (reduced true)
                               false)) false items))))


(defn almost-equal 
  "Returns a binary function which can be used to test whether two
  given arguments are within a given tolerance of each other."
  [tolerance]
  (assert (float? tolerance))
  (fn [x y]
    (assert (number? x))
    (assert (number? y))
    (or (= x y)
        (<= (abs (- x y)) tolerance))))

(defn almost-equal-seq
  "Returns a binary function which can be used to test whether two
  given sequence arguments are element-wise within a given tolerance of each other."
  [tolerance]
  (assert (float? tolerance))
  (let [ae (almost-equal tolerance)]
    (fn [x-seq y-seq]
      (and (= (count x-seq) (count y-seq))
           (every? true? (map ae x-seq y-seq))))))


(defn re-chunk 
  "Given a lazy sequence, change the chunking buffer size to n.
  This code was taken directory from
  https://clojuredocs.org/clojure.core/chunk"
  [n xs]
  (lazy-seq
    (when-let [s (seq (take n xs))]
      (let [cb (chunk-buffer n)]
        (doseq [x s] (chunk-append cb x))
        (chunk-cons (chunk cb) (re-chunk n (drop n xs)))))))

(defn find-if
  "Find the first element in the sequence which makes the predicate true.
  If such an item is found, a singleton list of the item is returned,
  otherwise nil is returned.
  "
  [f col]
  (reduce (fn [_ item] (if (f item)
                           (reduced (list item))
                           nil))
          nil
          (re-chunk 1 col)))


(def ^:dynamic *time-out* (* 2 60 1000)) ;; 2 seconds


(defmacro util-try-expr
  "modified copy of clojure.test/try-expr"
  {:added "1.1"}
  [msg form]
  `(try ~(assert-expr msg form)
        (catch Throwable t#
          (do-report {:type :error, :message ~msg,
                      :expected '~form, :actual t#})
          (throw t#))))

(defmacro is
  "Generic assertion macro.  'form' is any predicate test.
  'msg' is an optional message to attach to the assertion.
  
  Example: (is (= 4 (+ 2 2)) \"Two plus two should be 4\")

  Special forms:

  (is (thrown? c body)) checks that an instance of c is thrown from
  body, fails if not; then returns the thing thrown.

  (is (thrown-with-msg? c re body)) checks that an instance of c is
  thrown AND that the message on the exception matches (with
  re-find) the regular expression re."
  {:added "1.1"} 
  ([form] `(is ~form nil))
  ([form msg] `(util-try-expr ~msg ~form)))

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

(defn type-check [f x]
  {:pre [(f x)]}
  x)

(defn write-to-string [unary]
  (let [writer (java.io.StringWriter.)]
    (unary writer)
    (.toString writer)))

(defmacro with-output-to-string [var & body]
  `(write-to-string (fn [~var] ~@body)))
