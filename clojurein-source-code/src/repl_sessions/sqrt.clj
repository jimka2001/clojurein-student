(ns repl-sessions.sqrt)

(defn sqrt [x epsilon]
  (cond (= 0 x)
        0

        (= 1 x)
        1

        (> 0 x)
        nil

        (< x 1)
        (/ (sqrt (/ x) epsilon))

        :else
        (loop [min 0
               max x]
          (let [mid (/ (+ max min) 2)]
            (cond
              (< (- max min) epsilon)
              mid
              
              (< (* mid mid) x)
              (recur mid max)
              
              :else
              (recur min mid))))))

(sqrt 0.4789 0.0000001)
