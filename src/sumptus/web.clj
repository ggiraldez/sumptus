(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]
            [environ.core :refer [env]]
            [sumptus.migrations :as migrations]
            [clojure.tools.logging :as log])
  (:gen-class))

(defroutes routes
  (GET "/" [] "<h1>Sumptus</h1>")
  (GET "/ping" []
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "pong"}))

(defn start [port]
  (ring/run-jetty #'routes {:port port
                            :join? false}))

(defn -main []
  (log/info "Starting Sumptus application")
  (migrations/migrate-all)
  (let [port (Integer. (env :port))]
    (start port)))

;; For development
;; (defonce server (-main))
;; (.stop server)
