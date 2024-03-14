(ns allentiak.most-experienced-developers-according-to-github.core
  (:gen-class)
  (:require
   [allentiak.most-experienced-developers-according-to-github.data-fetching :as fetch]
   [allentiak.most-experienced-developers-according-to-github.data-conversion :as convert]))

(defn generate-members-table
  [org-name]
  (->
   (fetch/org-members org-name)
   (convert/members-response->user-logins-set)
   (fetch/users-data)
   (convert/user-login-responses-set->members-table-data-set)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Howdy! Invocation args are: " args))
