(ns allentiak.most-experienced-developers-according-to-github.database-creation.schemas
  (:require
   [malli.core :as m]))

(def members-table-row
  (m/schema
    [:map
     [:login
      [:string {:min 1}]]
     [:name
      [:maybe
       [:string {:min 1}]]]]))

(def members-table
  (m/schema
    [:vector
     members-table-row]))
