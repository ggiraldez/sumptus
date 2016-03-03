(ns sumptus.db
  (:require [clojure.java.jdbc :as sql]
            [environ.core :refer [env]]))

(def database-url (env :database-url))

;; (sql/db-do-commands database-url (sql/create-table-ddl :testing [:data :text]))
;; (sql/insert! database-url :testing {:data "Hello world!"})
;; (sql/query database-url ["select * from testing"])
;; (sql/db-do-commands database-url "drop table testing")

(defn recent-expenses
  ([]
   (recent-expenses 20))
  ([count]
   (sql/query database-url
              [(str "SELECT \"id\", \"when\", category, description, amount "
                    "FROM expenses ORDER BY \"when\" DESC LIMIT ?") count])))
