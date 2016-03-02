(ns sumptus.migrations
  (:require [sumptus.db :refer [database-url]]
            [ragtime.core :as core]
            [ragtime.protocols :as protocols]
            [ragtime.strategy :as strategy]
            [ragtime.jdbc :as jdbc]
            [to-jdbc-uri.core :refer [to-jdbc-uri]]
            [clojure.tools.logging :as log]))

(def datastore
  (jdbc/sql-database {:connection-uri (to-jdbc-uri database-url)}))

(def migrations
  (jdbc/load-resources "migrations"))

(defn logging-migration [migration]
  (let [id (protocols/id migration)]
    (reify protocols/Migration
      (id [_] id)
      (run-up! [_ store]
        (log/info (str "Applying migration " id))
        (protocols/run-up! migration store))
      (run-down! [_ store]
        (log/info (str "Rolling back migration " id))
        (protocols/run-down! migration store)))))

(defn migrate-all []
  (let [index (core/into-index migrations)
        strategy strategy/raise-error
        migrations (map logging-migration migrations)]
    (log/info "Running migrations")
    (core/migrate-all datastore index migrations strategy)))
