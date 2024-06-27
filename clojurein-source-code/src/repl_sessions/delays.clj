(ns repl-sessions.delays)

(let [d1 (delay (println "first delay object")
                100)
      d2 (delay (println "second delay object")
                200)
      d3 (delay (println "third delay object")
                200)
      ]
  (list @d1 (if @d2 @d1 @d3)))
