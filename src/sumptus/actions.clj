(ns sumptus.actions
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :as response]
            [sumptus.db :as db]
            [sumptus.views :as views]
            [sumptus.model :as model]
            [clojure.tools.logging :as log]))

(defn add-expense [req]
  (let [params (:params req)
        expense (select-keys params [:when :category :description :amount])
        expense (model/transform-expense expense)]
    (log/info (str "Creating expense: " expense))
    (db/create-expense! expense)
    (response/redirect "/" :see-other)))

(defn expenses-index []
  (let [recent-expenses (db/recent-expenses {:count 10})]
    (views/home recent-expenses)))

(defroutes routes
  (GET "/" [] (expenses-index))
  (POST "/add-expense" req (add-expense req)))
