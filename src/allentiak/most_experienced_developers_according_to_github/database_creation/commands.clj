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

(defn create-db-tables!
  [connectable]
  (jdbc/execute! connectable
                 [create-table-members]))

(defn load-data!
  [connectable data]
  (sql/insert-multi! connectable
                     :members
                     (vec data)))

(defn destroy-data!
  [connectable]
  (jdbc/execute! connectable
                 ["delete from members"]))

(defn drop-all-tables!
  [connectable]
  (jdbc/execute! connectable
                 [drop-table-members]))

(defn insert-member!
  [connectable member]
  (sql/insert! (connectable) :members member))
