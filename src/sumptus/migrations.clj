(ns sumptus.migrations
  (:require [sumptus.db :refer [database-url]]
            [ragtime.core :as core]
            [ragtime.strategy :as strategy]
            [ragtime.jdbc :as jdbc]
            [to-jdbc-uri.core :refer [to-jdbc-uri]]))

(def datastore
  (jdbc/sql-database {:connection-uri (to-jdbc-uri database-url)}))

(def migrations
  (jdbc/load-resources "migrations"))

(defn migrate-all []
  (let [index (core/into-index migrations)
        strategy strategy/raise-error]
    (core/migrate-all datastore index migrations strategy)))
