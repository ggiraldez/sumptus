(ns sumptus.migrations
  (:require [sumptus.db :refer [database-url]]
            [ragtime.core :as core]
            [ragtime.strategy :as strategy]
            [ragtime.jdbc :as jdbc]))

(def datastore
  (let [connection-uri (str "jdbc:" database-url)]
    (jdbc/sql-database {:connection-uri connection-uri})))

(def migrations
  (jdbc/load-resources "migrations"))

(defn migrate-all []
  (let [index (core/into-index migrations)
        strategy strategy/raise-error]
    (core/migrate-all datastore index migrations strategy)))
