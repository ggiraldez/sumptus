(ns sumptus.views
  (:require [ring.util.anti-forgery :as anti-forgery]
            [hiccup.def :refer [defhtml]]
            [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.form :as form]))

(defhtml new-expense-form [expense]
  (form/form-to
   {:class "mui-panel expense-form"}
   [:post "/add-expense"]
   (anti-forgery/anti-forgery-field)
   [:div.mui-textfield
    (form/text-field {:type :date
                      :required true
                      :autofocus true}
                     :when
                     (:when expense))
    (form/label :when "When")]
   [:div.mui-textfield
    (form/text-field {:required true
                      :placeholder "Choose a category or enter a new one"
                      :autocomplete "off"
                      :list "categories"}
                     :category
                     (:category expense))
    (form/label :category "Category")]
   [:div.mui-textfield
    (form/text-field {:required true
                      :placeholder "Brief description of the expense"}
                     :description
                     (:description expense))
    (form/label :description "Description")]
   [:div.mui-textfield
    (form/text-field {:type :number
                      :step 0.01
                      :required true
                      :placeholder "How much did you spend"}
                     :amount (:amount expense))
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

(defhtml category-datalist [categories]
  [:datalist#categories
   (for [category categories] [:option {:value category}])])

(defn home [{:keys [expense-list expense-template category-list]}]
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
     (category-datalist category-list)
     (new-expense-form expense-template)
     (recent-expenses-list expense-list)
     (include-js "js/mui.min.js")
     (include-js "js/sumptus.js")]]))
