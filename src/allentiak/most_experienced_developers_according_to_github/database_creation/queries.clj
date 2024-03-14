(ns allentiak.most-experienced-developers-according-to-github.database-creation.queries
  (:require
   [next.jdbc :as jdbc]))

(defn get-members-by-login
  "Given a member login, return the member record."
  [connectable login]
  (jdbc/execute! connectable
                 [(str "select * from members where login = '" login "'")]))

(defn get-all-members
  [connectable]
  (jdbc/execute! connectable
                 ["select * from members"]))
