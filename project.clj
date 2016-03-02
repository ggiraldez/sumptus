(defproject sumptus "0.1.0-SNAPSHOT"
  :description "Simple Expense Tracker"
  :url "http://sumptus.herokuapp.com/"
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]
                 [environ "1.0.2"]
                 [ragtime "0.5.2"]
                 [com.carouselapps/to-jdbc-uri "0.5.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.6"]]
  :plugins [[lein-environ "1.0.2"]]
  :main ^:skip-aot sumptus.web
  :uberjar-name "sumptus-standalone.jar"
  :profiles {:dev     {:env {:database-url "postgresql://localhost:5432/sumptus"
                             :port         "5000"}}
             :test    {:env {:database-url "postgresql://localhost:5432/sumptus-test"}}
             :uberjar {:aot :all}})
