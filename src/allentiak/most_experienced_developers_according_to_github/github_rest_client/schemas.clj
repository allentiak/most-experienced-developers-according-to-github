(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.schemas
  (:require
    [allentiak.most-experienced-developers-according-to-github.db-creation.schemas :as db-schemas]
    [malli.core :as m]
    [malli.util :as mu]))


(def members-response-vector
  (m/schema
    [:vector
     [:map
      [:login
       [:string {:min 1}]]]]))


(def user-response-map
  (mu/open-schema
    db-schemas/member-map))


(def user-response-maps-seq
  (m/schema
    [:sequential
     user-response-map]))


(def repos-response-vector
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
