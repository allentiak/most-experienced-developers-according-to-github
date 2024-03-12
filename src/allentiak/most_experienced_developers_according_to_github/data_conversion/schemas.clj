(ns allentiak.most-experienced-developers-according-to-github.data-conversion.schemas
  (:require
    [malli.core :as m]))


(def user-logins-set
  (m/schema
    [:set
     [:string {:min 1}]]))


(def members-table-row
  (m/schema
    [:map
     {:closed true}
     [:login
      [:string {:min 1}]]
     [:name
      [:maybe
       [:string {:min 1}]]]]))

(def members-table-data
  (m/schema
    [:set
     members-table-row]))


(def members-set
  (m/schema
    [:set
     members-table-row]))


(def repos-map
  (m/schema
    [:map
     {:closed true}
     [:owner
      [:string {:min 1}]]
     [:name
      [:string {:min 1}]]
     [:main-language
      [:maybe [:string {:min 1}]]]]))


(def repos-set
  (m/schema
    [:set
     repos-map]))


(def languages-map
  (m/schema
    [:map
     {:closed true}
     [:id
      [:int]]
     [:name
      [:string {:min 1}]]]))


(def languages-set
  (m/schema
    [:set
     languages-map]))
