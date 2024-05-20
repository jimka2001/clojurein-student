# Unit 1 - Language Overview


## Hello World

### Project layout

The `src` directory
The `src/lecture` directory
The `src/homework` directory
The `src/common` directory

The `test` directory
The `test/lecture` directory
The `test/homework` directory
The `test/common` directory


### The clojure file

The content of `src/lecture/greeting.clj`.

	(ns lecture.greeting)
	
	(defn hello
	  "print a greeting"
	  [name]
	  (println ["Hello" name]))

## Printing

See https://clojuredocs.org for lots of examples

### Default printing from the REPL

```
user> (+ 1 2 3)
6
user>
```

### Functions which print

- `println` --- 
Same as `print` followed by `(newline)`

- `print` --- 
Prints the object(s) to the output stream that is the current value
of `*out*`.  `print` and `println` produce output for human consumption.
"Human consumption" means, for example, that a string is printed as-is --- without surrounding double quotes and without nonprinting characters being escaped.


- `newline` --- Writes a platform-specific newline to `*out*`

- `pr` --- `(pr)`, `(pr x)`, `(pr x & more)` ---
Prints the object(s) to the output stream that is the current value
of `*out*`.  Prints the object(s), separated by spaces if there is
more than one.  By default, `pr` and `prn` print in a way that objects
can be read by the reader

- `prn` ---
Same as `pr` followed by `(newline)`. Observes `*flush-on-newline*`

- `printf` ---
Prints formatted output, as per `format`

- `pprint` ---
`(pprint object)`, `(pprint object writer)` ---
Pretty print object to the optional output writer. If the writer is not provided, 
print the object to the currently bound value of `*out*`.


- `cl-format` --- `(cl-format writer format-in & args)` ---
An implementation of a Common Lisp compatible format function. `cl-format` formats its
arguments to an output stream or string based on the format control string given. It 
supports sophisticated formatting of structured data.

### Printing to a string

- `println-str` ---
`println` to a string, returning it

- `prn-str` ---
`prn` to a string, returning it

- `format` ---
Formats a string using `java.lang.String.format`, see `java.util.Formatter` for `format`
string syntax

- `cl-format nil` ---
`cl-format` to a string, returning it.


- `with-out-string` --- `(with-out-str & body)` ---
Evaluates exprs in a context in which `*out*` is bound to a fresh
`StringWriter`.  Returns the string created by any nested printing
calls.


### Variables involved in printing 
- `*flush-on-newline*`

When set to true, output will be flushed whenever a newline is printed.

- `*out*`

A `java.io.Writer` object representing standard output for print operations.

- `*err*`

A `java.io.Writer` object representing standard error for print operations.


## Language Concepts and Syntax

Review file     `clojurein-source-code/src/repl_sessions/cftbat-code-03.clj`
