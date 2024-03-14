(ns allentiak.most-experienced-developers-according-to-github.database-creation
  (:require
   [next.jdbc :as jdbc]))

(def db
  {:dbtype "h2"
   :dbname "local-db"
   :database_to_upper false})

(def ds
  (jdbc/get-datasource db))

(defn persist-members-table!
  "take data and store it into the database"
  [conectable data]
  (whaetever))
