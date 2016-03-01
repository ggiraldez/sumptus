(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]))

(defroutes routes
  (GET "/" [] "<h1>Sumptus</h1>")
  (GET "/ping" []
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "pong"}))

(defn -main []
  (ring/run-jetty #'routes {:port 5000 :join? false}))
