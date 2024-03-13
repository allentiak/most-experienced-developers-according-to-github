(ns allentiak.most-experienced-developers-according-to-github.data-fetching.schemas
  (:require
   [malli.core :as m]))

(def members-response
  (m/schema
   [:vector
    [:map
     [:login
      [:string {:min 1}]]]]))

(def user-login-response
  (m/schema
   [:map
    [:login
     [:string {:min 1}]]
    [:name
     [:maybe
      [:string {:min 1}]]]]))

(def user-login-responses-seq
  (m/schema
   [:sequential
    user-login-response]))
