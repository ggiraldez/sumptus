(defproject sumptus "0.1.0-SNAPSHOT"
  :description "Simple Expense Tracker"
  :url "http://sumptus.herokuapp.com/"
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]]
  :main ^:skip-aot sumptus.web
  :uberjar-name "sumptus-standalone.jar"
  :profiles {:uberjar {:aot :all}})
