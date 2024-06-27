;; code from https://gist.github.com/andfadeev/35c351682c2df7d168c0473e4b96323e
;; Thanks to Andrey Fadeev
;;     @andrey.fadeev
;;     buymeacoffee.com/andrey.fadeev

(ns concurrency
  (:require [clj-http.client :as client]
            [clojure.tools.logging :as log]
            [com.climate.claypoole :as cp]))

(defmacro pr-info [& body]
  `(log/info ~@body)
  ;; convert back to prn to simplify the output
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


;; so why do we even need to have parallel threads? - to make your
;; application faster and utilize resources better.  If we have to
;; spin 10 independent requests (and combine results later) taking
;; each 5 seconds execute one-by-one is 50 seconds, but all in
;; parallel is just 5sec (maybe a bit more)


;; execute something in a thread

(defn run-java-thread
  [data]
  (pr-info "Before thread")
  (.start (new Thread (fn []
                        (Thread/sleep 1000)
                        (pr-info (format "from thread: data=%s" data)))))
  (pr-info "After thread"))

;;(run-java-thread 100)

;; but that's too much code + java interop

;; clojure Future instead
(defn run-clojure-future
  [data]
  (pr-info "Before future")
  (let [f
        (future
          (Thread/sleep 1000)
          (pr-info (format "From future body: data=%s" data))
          (pr-info (format "from future body: data=%s" data))
          {:result "from future"
           :data data
           })]
    (pr-info "After future")
    (pr-info "Result of future is " (deref f))
    (pr-info "After deref"))
  )

;;(run-clojure-future 100)

(defn serial-requests
  []
  (let [urls (->> (client/get "https://pokeapi.co/api/v2/pokemon-species?limit=100"
                              {:as :json})
                  :body
                  :results
                  (map :url))]
    (->> urls
         (map (fn [url]
                (pr-info (format "treating url: %s" url))
                (-> (client/get url {:as :json})
                    :body
                    (select-keys [:name :shape]))))
         (doall))))

;; (serial-requests)

(defn parallel-requests
  []
  (let [urls (->> (client/get "https://pokeapi.co/api/v2/pokemon-species?limit=100"
                              {:as :json})
                  :body
                  :results
                  (map :url))]

    (->> urls
         (map (fn [url]
                (pr-info (format "starting future for url: %s" url))
                (future
                  (-> (client/get url {:as :json})
                      :body
                      (select-keys [:name :shape])))))
         (doall)
         (map (fn [f] 
                (pr-info (format "deref %s" f))
                (deref f)))
         (doall))))



;; Atoms

(def counter 0)

(defn inc-counter-test
  [n]
  (alter-var-root #'counter (constantly 0))
  (let [f1 (future
             (dotimes [_ n]
               ;(Thread/sleep 1)
               (alter-var-root #'counter (constantly (inc counter))))
             (pr-info "f1 ends")
             ::f1
             )
        f2 (future
             (dotimes [_ n]
               ;(Thread/sleep 2)
               (alter-var-root #'counter (constantly (dec counter))))
             (pr-info "f2 ends")
             ::f2)]
    (and (deref f1) (deref f2))
    (pr-info "Here!" @f1 @f2)
    counter

    )
  )

;;(inc-counter-test 1000)


(def counter-atom (atom 0))
(def counter-atom-f1 (atom 0))
(def counter-atom-f2 (atom 0))

(defn inc-counter-atom-test
  "increment and decrement the atom the same number of times"
  [n] ;; e.g. n=10000
  (reset! counter-atom 0)

  (let [f1 (future
             (dotimes [_ n]
               (swap! counter-atom inc))
             ::f1)
        f2 (future
             (dotimes [_ n]
               (swap! counter-atom dec))
             ::f2)]
    (pr-info "Here!" @f1 @f2)
    (pr-info (deref counter-atom))
))

;;(inc-counter-atom-test 1000)


(defn inc-counter-atom-test
  "increment and decrement the atom the same number of times, but count the re-tries"
  [n] ;; e.g. n=10000
  (reset! counter-atom 0)
  (reset! counter-atom-f1 0)
  (reset! counter-atom-f2 0)

  (let [f1 (future
             (dotimes [_ n]
               (swap! counter-atom (fn [x]
                                     (swap! counter-atom-f1 inc)
                                     (inc x))))
             ::f1)
        f2 (future
             (dotimes [_ n]
               (swap! counter-atom (fn [x]
                                     (swap! counter-atom-f2 inc)
                                     (dec x))))
             ::f2)]
    (pr-info "Here!" @f1 @f2)
    (pr-info (deref counter-atom)
         (deref counter-atom-f1)
         (deref counter-atom-f2))
))

(inc-counter-atom-test 1000)


(comment
  (swap! counter-atom (fn [old-value arg1]
                        (println old-value arg1)
                        old-value
                        )
         1))


;; be careful with pmap - it's potentially will not give you the boost as you expect
;; so when you learn clojure there is map but also pmap,
;; and looks like people just assume that it's a magic fn to make
;; the processing faster, it's actually try but only for cpu intensive tasks
(comment (pmap (fn [input]) [1 2 3]))


;; futures are easy and great, but what if you want more control
;; so futures are execute on an unbounded thread pool,
;; so it's hard to control how much is running at max
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
