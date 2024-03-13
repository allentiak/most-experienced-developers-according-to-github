(ns allentiak.most-experienced-developers-according-to-github.database-creation.commands
  (:require
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]))

(def ^:private members
  "members (
  login varchar(32) primary key,
  name varchar(32)
  )")

(def create-table-members
  (str "create table" " " members))

(def create-table-members-if-not-exists
  (str "create table if not exists" " " members))

(def drop-table-members
  "drop table members")

(def drop-table-members-if-exists
  "drop table if exists members")

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
