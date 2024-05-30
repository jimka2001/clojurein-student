# Maps, Hash tables

## Examples from Graph Theory
### Finding element in sequence

Clojure documentation recomments:
```
(some #(= 3 %) [1 2 3 4 5 ])
; => true

(some #(= 30 %) [1 2 3 4 5 ])
; => false

(some #(= nil %) [1 2 3 4 5 ])
; => false

(some #(= nil %) [1 2 nil 3 4 5 ])
; => true
```

Don't use `contains?`.   Contains test for a key, not an element
```
(contains? {:a 1 :b 2} :a)
; => true

(contains? {:a 1 :b 2} :c)
; => true
```
The problem is the keys of a vector are the indices.

```
(contains? ["a" "b" "c"] 2)
; => true   ;; because the vector has a 2nd element

(contains? [1 10 100] 1)
; => true   ;; because the vector has a 1st element, which is 10

(contains? [1 10 100] 3)
; => false   ;; because the vector has no element at index 3
```

You may also use the function `member` defined in `common.util`

```
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
```


### Adjacence List
Different methods of generating adjacency lists, including `group-by`
Generate 
### Generate Paths
Given a list of length-n paths in a graph, generate the list of paths of length n+1

## Example Polynomials
