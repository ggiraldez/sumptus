(ns sumptus.model
  (:require [clj-time.format :refer [formatter parse]]
            [clj-time.local :refer [to-local-date-time]]))

(def date-formatter (formatter "yyyy-MM-dd"))

(defn transform-expense [{:keys [when category description amount]}]
  {:when (to-local-date-time (parse date-formatter when))
   :category category
   :description description
   :amount (BigDecimal. amount)})
