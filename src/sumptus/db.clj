(ns sumptus.db
  (:require [clojure.java.jdbc :as sql]
            [environ.core :refer [env]]))

(def db (env :database-url "postgresql://localhost:5432/sumptus"))

;; (sql/db-do-commands db (sql/create-table-ddl :testing [:data :text]))
;; (sql/insert! db :testing {:data "Hello world!"})
;; (sql/query db ["select * from testing"])
;; (sql/db-do-commands db "drop table testing")
