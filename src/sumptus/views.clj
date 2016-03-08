(ns sumptus.views
  (:require [ring.util.anti-forgery :as anti-forgery]
            [hiccup.def :refer [defhtml]]
            [hiccup.page :refer [html5]]
            [hiccup.form :as form]))

(defhtml new-expense-form []
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
     (form/submit-button "Add expense")]]))

(defhtml expense-table-row [{:keys [id when category description amount]}]
  [:tr {:data-expense-id id}
   [:td when]
   [:td category]
   [:td description]
   [:td (format "$%.2f" amount)]])

(defhtml recent-expenses-list [expense-list]
  [:h2 "Recent expenses"]
  [:table
   [:thead
    [:tr
     [:th "When"]
     [:th "Category"]
     [:th "Description"]
     [:th "Amount"]]]
   [:tbody
    (for [expense expense-list] (expense-table-row expense))]])

(defn home [recent-expenses]
  (html5
   [:h1 "Sumptus"]
   (new-expense-form)
   (recent-expenses-list recent-expenses)))
