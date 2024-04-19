;; code from https://gist.github.com/andfadeev/35c351682c2df7d168c0473e4b96323e
;; Thanks to Andrey Fadeev
;;     @andrey.fadeev
;;     buymeacoffee.com/andrey.fadeev

(ns concurrency
  (:require [clj-http.client :as client]
            [clojure.tools.logging :as log]
            [com.climate.claypoole :as cp]))

;; log/info is not working for me, so we use prn for the moment
(defmacro pr-info [& body]
  `(log/info ~@body)
  ;; `(prn ~@body)
  )

;; we won't touch STM (software transactional memory), agents, refs etc
;; let's focus on things that are actually used a lot in real applications


;; first of all we have immutable DS in clojure and just that removed a huge slice of all
;; concurrency related problems
;; if we pass a map to some library function or some code that we don't know
;; we don't event worry that original map that we pass will be changed - IT's Immutable!

(defn some-scary-black-box-fn
  [my-object])
(comment
  (let [my-object {:name "value"}]
    (some-scary-black-box-fn my-object)
    my-object))


;; so why we even need to have parallel threads - to make your application faster
;; and utilize resources better, if we have to spin 10 independent requests (and combine results later)
;; taking each 5 seconds execute one-by-one is 50 seconds, but all in parallel is just 5sec (maybe a bit more)


;; execute something in a thread

(defn run-java-thread
  []
  (pr-info "Before thread")
  (.start (new Thread (fn []
                        (Thread/sleep 1000)
                        (pr-info "from thread"))))
  (pr-info "After thread"))

;; but that's too much code + java interop

;; clojure Future instead
(defn run-clojure-future
  []
  (pr-info "Before future")
  (let [f
        (future
          (Thread/sleep 1000)
          (pr-info "From future body")
          (pr-info "from future")
          {:result "from future"})]
    (pr-info "After future")
    (pr-info "Result of future is " (deref f)))
  )

(defn serial-requests
  []
  (let [urls (->> (client/get "https://pokeapi.co/api/v2/pokemon-species?limit=100"
                              {:as :json})
                  :body
                  :results
                  (map :url))]

    (->> urls
         (map (fn [url]
                (-> (client/get url {:as :json})
                    :body
                    (select-keys [:name :shape]))))
         (doall))))


(defn parallel-requests
  []
  (let [urls (->> (client/get "https://pokeapi.co/api/v2/pokemon-species?limit=100"
                              {:as :json})
                  :body
                  :results
                  (map :url))]

    (->> urls
         (map (fn [url]
                (future
                  (-> (client/get url {:as :json})
                      :body
                      (select-keys [:name :shape])))))
         (map deref)
         (doall))))



;; Atoms

(def counter 0)

(defn inc-counter-test
  []
  (alter-var-root #'counter (constantly 0))
  (let [f1 (future
             (dotimes [_ 1000]
               ;(Thread/sleep 1)
               (alter-var-root #'counter (constantly (inc counter))))
             (pr-info "f1 ends")
             ::f1
             )
        f2 (future
             (dotimes [_ 1000]
               ;(Thread/sleep 2)
               (alter-var-root #'counter (constantly (inc counter))))
             (pr-info "f2 ends")
             ::f2)]
    (and (deref f1) (deref f2))
    (pr-info "Here!" @f1 @f2)
    counter

    )

  )

(def counter-atom (atom 0))
(def counter-atom-f1 (atom 0))
(def counter-atom-f2 (atom 0))

(defn inc-counter-atom-test
  []
  (reset! counter-atom 0)
  (reset! counter-atom-f1 0)
  (reset! counter-atom-f2 0)

  (let [f1 (future
             (dotimes [_ 10000]
               (swap! counter-atom (fn [x]
                                     (swap! counter-atom-f1 inc)
                                     (inc x))))
             ::f1)
        f2 (future
             (dotimes [_ 10000]
               (swap! counter-atom (fn [x]
                                     (swap! counter-atom-f2 inc)
                                     (inc x))))
             ::f2)]
    (pr-info "Here!" @f1 @f2)
    (pr-info (deref counter-atom)
         (deref counter-atom-f1)
         (deref counter-atom-f2))
))

(swap! counter-atom (fn [old-value arg1]
                      (println old-value arg1)
                      old-value
                      ) 1)


;; be careful with pmap - it's potentially will not give you the boost as you expect
;; so when you learn clojure there map but also pmap, and looks like people just assume that it's a magic fn to make
;; the processing faster, it's actually try but only for cpu intensive tasks
(comment (pmap (fn [input]) [1 2 3]))


;; futures are easy and great, but what if you want more control
;; so futures are execute on an unbounded thread pool, so it's hard to control how much is running at max
;; we have claypoole library to the resque

(defn claypoole-pmap-example
  []
  (let [urls []
        responses (cp/pmap (cp/threadpool 100)
                           (fn [url]
                             (client/get url)) urls)]
    (doall responses)))

(defn claypoole-parallel-requests
  []
  (let [urls (->> (client/get "https://pokeapi.co/api/v2/pokemon-species?limit=100"
                              {:as :json})
                  :body
                  :results
                  (map :url))]
    (->> urls
         (cp/pmap
           (cp/threadpool 100)
           (fn [url]
             (-> (client/get url {:as :json})
                 :body
                 (select-keys [:name :shape]))))
         (doall))))
