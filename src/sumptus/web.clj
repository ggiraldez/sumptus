(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring])
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
  (let [port (Integer. (or (System/getenv "PORT") "5000"))]
    (start port)))
