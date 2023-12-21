(ns allentiak.most-experienced-developers-according-to-github.db-creation.schemas
  (:require
    [malli.core :as m]))

(def members-set
  (m/schema
   [:set
    [:map
     [:login
      [:string {min 1}]]
     [:name
      [:string {min 1}]]]]))

(def repos-set
  (m/schema
    [:set
     [:map
      [:owner
       [:string {min 1}]]
      [:name
       [:string {min 1}]]
      [:main-language
       [:maybe [:string {min 1}]]]]]))


(def languages-set
  (m/schema
    [:set
     [:map
      [:id
       [:int]]
      [:name
       [:string {min 1}]]]]))
