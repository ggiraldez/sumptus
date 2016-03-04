(ns sumptus.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as ring]
            [ring.util.response :as response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.anti-forgery :as anti-forgery]
            [environ.core :refer [env]]
            [sumptus.migrations :as migrations]
            [sumptus.db :as db]
            [clojure.tools.logging :as log]
            [hiccup.core :refer :all]
            [hiccup.page :refer [html5]]
            [hiccup.form :as form]
            [clj-time.format :refer [formatter parse]]
            [clj-time.local :refer [to-local-date-time]]
            clj-time.jdbc)
  (:gen-class))

(defn new-expense-form []
  (html
   (form/form-to
    [:post "/add-expense"]
    (anti-forgery/anti-forgery-field)
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
  (let [expense-list (db/recent-expenses {:count 20})]
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

(defn home []
  (html5
   [:h1 "Sumptus"]
   (new-expense-form)
   (recent-expenses-list)))

(def date-formatter (formatter "yyyy-MM-dd"))

(defn transform-expense [{:keys [when category description amount]}]
  {:when (to-local-date-time (parse date-formatter when))
   :category category
   :description description
   :amount (BigDecimal. amount)})

(defn add-expense [req]
  (let [params (:params req)
        expense (select-keys params [:when :category :description :amount])
        expense (transform-expense expense)]
    (log/info (str "Creating expense: " expense))
    (db/create-expense! expense)
    (response/redirect "/" :see-other)))

(defroutes routes
  (GET "/" [] (home))
  (POST "/add-expense" req (add-expense req))
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
