(ns allentiak.most-experienced-developers-according-to-github.database-creation
  (:require
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]))

(def db
  {:dbtype "h2"
   :dbname "local-db"
   :database_to_upper false})

(def ds
  (jdbc/get-datasource db))

(comment
  (def my-data
    #{{:name "sss" :login "abc"}
      {:name nil :login "bcs"}})

  (jdbc/execute! ds
                 [create-table-members])
  ;; => [#:next.jdbc{:update-count 0}]

  (jdbc/execute! ds
                 [create-table-members-if-not-exists])
  ;; => [#:next.jdbc{:update-count 0}]
  ;; => [#:next.jdbc{:update-count 0}]

  (jdbc/execute! ds
                 [drop-table-members])
  ;; => [#:next.jdbc{:update-count 0}]

  (jdbc/execute! ds
                 [drop-table-members-if-exists])
  ;; => [#:next.jdbc{:update-count 0}]
  ;; => [#:next.jdbc{:update-count 0}]

  (vec my-data)
  ;; => [{:name nil, :login "bcs"} {:name "sss", :login "abc"}]

  (vector my-data)
  ;; => [#{{:name nil, :login "bcs"} {:name "sss", :login "abc"}}]

  (sql/insert! ds :members
               {:name "someone" :login "ccc"})
;; => #:MEMBERS{:LOGIN "ccc"}

  (sql/insert-multi! ds :members
                     [{:login "aaa" :name "sss"}
                      {:login "uuu" :name "sfaru"}])
  ;; => [#:MEMBERS{:LOGIN "aaa"} #:MEMBERS{:LOGIN "uuu"}]

  (sql/insert-multi! ds :members
                     (vec my-data))
  ;; => [#:MEMBERS{:LOGIN "bcs"} #:MEMBERS{:LOGIN "abc"}]
  ,)
