(ns allentiak.most-experienced-developers-according-to-github.db-creation.schemas
  (:require
    [malli.core :as m]))


(def member-map
  (m/schema
    [:map
     {:closed true}
     [:login
      [:string {min 1}]]
     [:name
      [:string {min 1}]]]))


(def members-set
  (m/schema
    [:set
     member-map]))


(def repos-map
  (m/schema
    [:map
     {:closed true}
     [:owner
      [:string {min 1}]]
     [:name
      [:string {min 1}]]
     [:main-language
      [:maybe [:string {min 1}]]]]))


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
      [:string {min 1}]]]))


(def languages-set
  (m/schema
    [:set
     languages-map]))
