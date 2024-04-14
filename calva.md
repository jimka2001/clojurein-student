# Using Visual Studio Code

To use this project with VS Code:

0. Install the [Calva](https://calva.io) extension in VS Code.
1. Start the project and connect the REPL to Calva.

## Starting and connecting the project REPL

From the command pallette issue the command: **Calva: Start a Project REPL and Connect (aka Jack-In)**, two windows/panes will open:

* The Calva Jack-in terminal. This will show output from the Clojure process
* The Calva REPL Window (a special file) will open to the right ->
  * This file will have show the message `; Jack-in done.` when the  REPL is connected
  * This is where results of your evaluations of Clojure code will be shown, as well as things you `println` from Clojure code.

## Calva Crash Course

To easily find Calva and VS Code commands, use the **Command Palette** and type to search. You can bring up the command palette with `Cmd/Ctrl+P` (Cmd on Mac) or `F1`.

### Editing

By default Calva protects the Lisp structure of the Code by:
* always adding a balancing opening bracket when you type an opening bracket
  * Typing an opening bracket with something selected will wrap the selection with opening and closing brackets.
* not deleting closing brackets when you backspace over them
  * To force delete a bracket, use `Alt/Option + Backspace`

A good command to learn early is **Expand Selection**. Pressing it once will select the “Current” form. Pressing it repeatedly will select increasingly encapsulating forms. Now you can easily select something and wrap it in

> **Note**: The “Current” form is an important Calva concept, that basically means the Lisp expression closest to the cursor.

### Evaluating forms

Calva has many commands for evaluating forms. Evaluating a form means that Calva will send it to the REPL, which evaluates them and gives Calva the result. Calva will then show the results inline in the editor, but only the one line of it. The full results will be printed in the REPL window. The most important evaluation commands are two:

1. **Calva: Evaluate Top Level Form (defun)**, `Alt/Option + Enter`
   * Top level forms are typically `(def ...)` and `(defn ...)`, or forms inside `(comment ...)`.
   * Place the cursor anywhere inside a function and press `Alt/Option + Enter`.
1. **Calva: Evaluate Current Form (or selection, if any)**, `Ctrl + Enter`
   * Place the cursor at the start or the end of a form and press `Ctrl + Enter`. E.g. if we use a vertical bar to represent the cursor
       ```clojure
       (println (str "foo" 42)|)
       ```
     This will evaluate `(str "foo" 42)`.

### Running tests in the REPL/editor

The most important command for running tests in this project is:

* **Calva: Run Tests for Current Namespace**

For Calva to find the tests you will first need to load the relevant test file. E.g for [homework/hello.clj](clojurein-source-code/src/homework/hello.clj) the test file is [homework/hello_test.clj](clojurein-source-code/test/homework/hello_test.clj). To load it open it and run the command:
* **Calva: Load/Evaluate Current File and its Requires/Dependencies**

> **Note**: The exercise files will contain code you can evaluate to load the test files.

## Example: `homework/hello.clj`

1. Open [homework/hello.clj](clojurein-source-code/src/homework/hello.clj)
1. Read the instructions in the file
1. Evaluate the namespace using the **Calva: Evaluate Top Level Form (defun)** command with the cursor somewhere within the `(ns ...)` form.
1. Load the test file (see above)
1. Fix the `(defn hello ...)` function to implement the functionality it specifies.
1. Run the namespace tests: **Calva: Run Tests for Current Namespace**

Repeat the last two steps until Calva reports that all tests are passing.