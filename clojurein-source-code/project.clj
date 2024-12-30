(defproject clojurein-source-code "0.1.0-SNAPSHOT"
  :description "Source code for Clojurein"
  :license {:name "BSD"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.3"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [org.clojure/tools.trace "0.7.11"] ;; DOCKER OMIT
                 [metasoarous/oz "2.0.0-alpha5"]    ;; DOCKER OMIT
                 [org.clj-commons/claypoole "1.2.2"]    ;; DOCKER OMIT
                 [org.clojure/tools.logging "1.3.0"]    ;; DOCKER OMIT
                 [org.slf4j/slf4j-reload4j "2.0.13"]    ;; DOCKER OMIT
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]    ;; DOCKER OMIT
                 [org.slf4j/slf4j-api "1.7.26"]    ;; DOCKER OMIT
                 [org.slf4j/jul-to-slf4j "1.7.25"]    ;; DOCKER OMIT
                 [org.slf4j/jcl-over-slf4j "1.7.25"]    ;; DOCKER OMIT
                 [org.slf4j/log4j-over-slf4j "1.7.26"]    ;; DOCKER OMIT
                 ]
  :source-paths ["src"]
  :test-paths ["test"]

  :plugins [[lein-exec "0.3.7"]]
  :target-path "target/%s"
  :jvm-opts [ 
             "-Xms1500m"
             "-Xmx1500m"
             ;; MAC only
             ;; the --add-opens= is for supressing the following warnings ;; MAC only
             ;; WARNING: An illegal reflective access operation has occurred ;; MAC only
             ;; WARNING: Illegal reflective access by clojure.lang.InjectedInvoker/0x0000000800232040 (file:/Users/jimka/.m2/repository/org/clojure/clojure/1.10.3/clojure-1.10.3.jar) to method com.sun.xml.internal.stream.writers.XMLStreamWriterImpl.writeCharacters(java.lang.String) ;; MAC only
             ;; WARNING: Please consider reporting this to the maintainers of clojure.lang.InjectedInvoker/0x0000000800232040 ;; MAC only
             ;; WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations ;; MAC only
             ;; WARNING: All illegal access operations will be denied in a future release ;; MAC only

             "--add-opens=java.xml/com.sun.xml.internal.stream.writers=ALL-UNNAMED" ;; MAC only
             "-Dclojure.tools.logging.factory=clojure.tools.logging.impl/slf4j-factory" ;; DOCKER OMIT
             ]
  :profiles {:test {:plugins [[lein-test-report-junit-xml "0.2.0"]]
                    :test-report-junit-xml {:output-dir "."}
                    }
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
