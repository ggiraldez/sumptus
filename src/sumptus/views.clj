(ns sumptus.views
  (:require [ring.util.anti-forgery :as anti-forgery]
            [hiccup.def :refer [defhtml]]
            [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.form :as form]))

(defhtml new-expense-form []
  (form/form-to
   {:class "mui-panel expense-form"}
   [:post "/add-expense"]
   (anti-forgery/anti-forgery-field)
   [:div.mui-textfield
    (form/text-field {:type :date :required true} :when)
    (form/label :when "When")]
   [:div.mui-textfield
    (form/text-field {:required true} :category)
    (form/label :category "Category")]
   [:div.mui-textfield
    (form/text-field {:required true} :description)
    (form/label :description "Description")]
   [:div.mui-textfield
    (form/text-field {:type :number :step 0.01 :required true} :amount)
    (form/label :amount "Amount")]
   [:div
    (form/submit-button {:class "mui-btn mui-btn--primary"} "Add expense")]))

(defhtml expense-table-row [{:keys [id when category description amount]}]
  [:tr {:data-expense-id id}
   [:td when]
   [:td category]
   [:td description]
   [:td (format "$%.2f" amount)]])

(defhtml recent-expenses-list [expense-list]
  [:table.mui-table
   [:thead
    [:tr
     [:th {:data-field "when"}        "When"]
     [:th {:data-field "category"}    "Category"]
     [:th {:data-field "description"} "Description"]
     [:th {:data-field "amount"}      "Amount"]]]
   [:tbody
    (for [expense expense-list] (expense-table-row expense))]])

(defn home [recent-expenses]
  (html5
   [:head
    [:title "Sumptus"]
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]]
    (include-css "css/mui.min.css")
    (include-css "css/sumptus.css")
   [:body
    [:div.mui-appbar
     [:div.mui--text-title.mui--appbar-height.title "Sumptus"]]
    [:div.mui-container
     (new-expense-form)
     (recent-expenses-list recent-expenses)
     (include-js "js/mui.min.js")
     (include-js "js/sumptus.js")]]))
