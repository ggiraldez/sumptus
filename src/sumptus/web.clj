(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]
            [environ.core :refer [env]])
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
  (let [port (Integer. (env :port "5000"))]
    (start port)))

;; For development
;; (def server (-main))
;; (.stop server)
