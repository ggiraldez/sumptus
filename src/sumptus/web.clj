(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as ring]
            [ring.util.response :as response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [environ.core :refer [env]]
            [sumptus.migrations :as migrations]
            [sumptus.actions :as actions]
            [clojure.tools.logging :as log])
  (:gen-class))

(defroutes routes
  #'actions/routes
  (GET "/ping" []
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "pong"}))

(def application
  (wrap-defaults #'routes site-defaults))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (log/info "Starting Sumptus application")
  (migrations/migrate-all)
  (let [port (Integer. (env :port))]
    (start port)))

;; For development
;; (defonce server (-main))
;; (.stop server)
