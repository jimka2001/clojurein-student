# Higher Order Functions

## Sorting

`(sort coll)` and `(sort comp coll)` --- Returns a sorted sequence of the
items in `coll`. If no comparator is supplied, uses `compare`.  comparator
must implement `java.util.Comparator`.  Guaranteed to be stable: equal
elements will not be reordered.  If coll is a Java array, it will be
modified.  To avoid this, sort a copy of the array.

[Comparators](https://clojure.org/guides/comparators)

`(sort-by keyfn coll)` and `(sort-by keyfn comp coll)` ---
Returns a sorted sequence of the items in `coll`, where the sort
order is determined by comparing `(keyfn item)`.  If no comparator is
supplied, uses `compare`.  comparator must implement
`java.util.Comparator`.  Guaranteed to be stable: equal elements will
not be reordered.  If coll is a Java array, it will be modified.  To
avoid this, sort a copy of the array.


---

## Anonymous function syntax

- [Anonymous functions](https://clojure.org/guides/learn/functions#_anonymous_function_syntax)
- [%1, %2 Notation](https://clojure.org/guides/weird_characters#_n_anonymous_function_arguments)
- [% Notation](https://clojure.org/guides/weird_characters#_anonymous_function)


---

## Local functions

Local functions can be specified using the Anonuymous function syntax as shown above
or with the `(fn [x y] ...)` syntax.

Another syntax `letfn` can be used to specify a suite of perhaps mutually recursive functions.

---

## Tabulate

Write a function which takes a size, `n` and a unary function, `f`.  The function should
allocate a vector of size `n` and populate it with `[(f 0) (f 1) ... (f (dec n))]`.

```
(defn tabulate [n f]
  ...)
```

---

## Pipelines
### `map` and `mapcat`
### `filter`
### `cycle`
### Threading, Composition and `->` and `->>`

`(-> x & forms)` Threads the `expr` through the forms. Inserts `x` as the
second item in the first form, making a list of it if it is not a
list already. If there are more forms, inserts the first form as the
second item in second form, etc.

Use of `->` (the "thread-first" macro) can help make code
more readable by removing nesting. It can be especially
useful when using host methods:

    ;; Perhaps easier to read:
    user=> (-> "a b c d" 
               .toUpperCase 
               (.replace "A" "X") 
               (.split " ") 
               first)
    "X"

    ;; Arguably a bit cumbersome to read:
    user=> (first (.split (.replace (.toUpperCase "a b c d") "A" "X") " "))
    "X"



`(->> x & forms)` Threads the `expr` through the `forms`. Inserts `x` as the
last item in the first form, making a list of it if it is not a
list already. If there are more forms, inserts the first form as the
last item in second form, etc.


    ;; An example of using the "thread-last" macro to get
    ;; the sum of the first 10 even squares.
    user=> (->> (range)
                (map #(* % %))
                (filter even?)
                (take 10)
                (reduce +))
    1140

    ;; This expands to:
    user=> (reduce +
                   (take 10
                         (filter even?
                                 (map #(* % %)
                                      (range)))))
    1140

## Convergence

### Binary Search

### Trig functions

### Calculus
