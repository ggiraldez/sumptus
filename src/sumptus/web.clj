(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]
            [environ.core :refer [env]]
            [sumptus.migrations :as migrations]
            [sumptus.db :as db]
            [clojure.tools.logging :as log]
            [hiccup.core :refer :all]
            [hiccup.page :refer [html5]]
            [hiccup.form :as form])
  (:gen-class))

(defn new-expense-form []
  (html
   (form/form-to
    [:post "/add-expense"]
    [:fieldset
     [:div
      (form/label :when "When")
      (form/text-field {:type :date} :when)]
     [:div
      (form/label :category "Category")
      (form/text-field :category)]
     [:div
      (form/label :description "Description")
      (form/text-field :description)]
     [:div
      (form/label :amount "Amount")
      (form/text-field {:type :number} :amount)]
     [:div
      (form/submit-button "Add expense")]])))

(defn expense-table-row [{:keys [id when category description amount]}]
  (html
   [:tr {:data-expense-id id}
    [:td when]
    [:td category]
    [:td description]
    [:td (format "$%.2f" amount)]]))

(defn recent-expenses-list []
  (let [expense-list (db/recent-expenses)]
    (html
     [:h2 "Recent expenses"]
     [:table
      [:thead
       [:tr
        [:th "When"]
        [:th "Category"]
        [:th "Description"]
        [:th "Amount"]]]
      [:tbody
       (for [expense expense-list] (expense-table-row expense))]])))

(defn home [req]
  (html5
   [:h1 "Sumptus"]
   (new-expense-form)
   (recent-expenses-list)))

(defroutes routes
  (GET "/" [] home)
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
