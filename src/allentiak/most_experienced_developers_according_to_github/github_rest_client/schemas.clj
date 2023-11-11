(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.schemas
  (:require
   [malli.core :as m]))

(def members-response
  (m/schema
   [:vector
    [:map
     [:login
      [:string {:min 1}]]]]))

(def user-response-map
  (m/schema
   [:map
    [:login
     [:string {min 1}]]
    [:name
     [:string {min 1}]]]))

(def repos-response
  (m/schema
   [:vector
    [:map
     [:owner
      [:map
       [:login
        [:string {min 1}]]]]
     [:name
      [:string {min 1}]]
     [:language
      [:maybe [:string {min 1}]]]]]))
