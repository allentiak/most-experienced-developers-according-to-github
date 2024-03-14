(ns allentiak.most-experienced-developers-according-to-github.database-creation.commands
  (:require
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]))

(def ^:private members
  "members (
  login varchar(32) primary key,
  name varchar(32)
  )")

(def ^:private create-table-members
  (str "create table" " " members))

(def ^:private drop-table-members
  "drop table members")

(defn create-db-tables
  [ds]
  (jdbc/execute! ds
                 [create-table-members]))

(defn load-data
  [ds data]
  (sql/insert-multi! ds
                     :members
                     (vec data)))

(defn drop-all-tables
  [ds]
  (jdbc/execute! ds
                 [drop-table-members]))
