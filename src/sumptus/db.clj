(ns sumptus.db
  (:require [yesql.core :refer [defquery]]
            [environ.core :refer [env]]
            clj-time.jdbc))

(def database-url (env :database-url))

(defquery recent-expenses "queries/recent_expenses.sql"
  {:connection database-url})

(defquery create-expense! "queries/create_expense.sql"
  {:connection database-url})

(defquery fetch-category-rows "queries/fetch_categories.sql"
  {:connection database-url})

(defn fetch-categories []
  (let [categories (fetch-category-rows)]
    (for [row categories] (:category row))))
