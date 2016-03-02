(ns sumptus.migrations
  (:require [sumptus.db :refer [database-url]]
            [ragtime.core :as core]
            [ragtime.protocols :as protocols]
            [ragtime.strategy :as strategy]
            [ragtime.jdbc :as jdbc]
            [to-jdbc-uri.core :refer [to-jdbc-uri]]
            [clojure.tools.logging :as log]))

(defn datastore []
  (let [connection-uri (to-jdbc-uri database-url)]
    (jdbc/sql-database {:connection-uri connection-uri})))

(defn migrations []
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
  (let [migrations (map logging-migration (migrations))
        index (core/into-index migrations)
        strategy strategy/raise-error]
    (log/info "Running migrations")
    (core/migrate-all (datastore) index migrations strategy)))
