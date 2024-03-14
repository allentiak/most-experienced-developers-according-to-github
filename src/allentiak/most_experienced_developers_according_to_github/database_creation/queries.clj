(ns allentiak.most-experienced-developers-according-to-github.database-creation.queries
  (:require
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]))

(defn get-members-by-login
  "Given a member login, return the member record."
  [connectable login]
  (sql/get-by-id (connectable) :members login))
