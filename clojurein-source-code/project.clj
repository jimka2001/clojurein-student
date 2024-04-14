(defproject clojurein-source-code "0.1.0-SNAPSHOT"
  :description "Source code for Clojurein, Introduction to Clojure Elective"
  :license {:name "BSD"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.trace "0.7.11"] ;; DOCKER OMIT
                 [metasoarous/oz "2.0.0-alpha5"]    ;; DOCKER OMIT
                 ]
  :source-paths ["src"]
  :test-paths ["test"]

  :plugins [[lein-exec "0.3.7"]]
  :target-path "target/%s"
  :jvm-opts [ 
             "-Xms1500m", "-Xmx1500m"
             ;; MAC only
             ;; the --add-opens= is for supressing the following warnings ;; MAC only
             ;; WARNING: An illegal reflective access operation has occurred ;; MAC only
             ;; WARNING: Illegal reflective access by clojure.lang.InjectedInvoker/0x0000000800232040 (file:/Users/jimka/.m2/repository/org/clojure/clojure/1.10.3/clojure-1.10.3.jar) to method com.sun.xml.internal.stream.writers.XMLStreamWriterImpl.writeCharacters(java.lang.String) ;; MAC only
             ;; WARNING: Please consider reporting this to the maintainers of clojure.lang.InjectedInvoker/0x0000000800232040 ;; MAC only
             ;; WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations ;; MAC only
             ;; WARNING: All illegal access operations will be denied in a future release ;; MAC only

             , "--add-opens=java.xml/com.sun.xml.internal.stream.writers=ALL-UNNAMED" ;; MAC only
             ]
  :profiles {:test {:plugins [[lein-test-report-junit-xml "0.2.0"]]
                    :test-report-junit-xml {:output-dir "."}
                    }
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
