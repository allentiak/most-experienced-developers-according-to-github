(ns allentiak.most-experienced-developers-according-to-github.core
  (:gen-class)
  (:require
   [allentiak.most-experienced-developers-according-to-github.data-fetching :as fetch]
   [allentiak.most-experienced-developers-according-to-github.data-conversion :as convert]
   [allentiak.most-experienced-developers-according-to-github.database-creation :as db]
   [allentiak.most-experienced-developers-according-to-github.database-creation.commands :as persistence]))

(defn generate-members-table
  [org-name]
  (->
   org-name
   fetch/org-members
   convert/members-response->user-logins-set
   fetch/users-data
   convert/user-login-responses-set->members-table-data-set))

(defn persist-members-table!
  [data]
  (persistence/load-data! db/ds data))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Howdy! Invocation args are: " args))
