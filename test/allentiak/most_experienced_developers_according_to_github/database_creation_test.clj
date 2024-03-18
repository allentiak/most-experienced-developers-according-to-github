(ns allentiak.most-experienced-developers-according-to-github.database-creation-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.database-creation
    :as sut]
   [allentiak.most-experienced-developers-according-to-github.database-creation.commands
    :as commands]
   [allentiak.most-experienced-developers-according-to-github.database-creation.schemas :as schemas]
   [clojure.test :refer [use-fixtures]]
   [expectations.clojure.test :refer [defexpect expect expecting]]
   [malli.core :as m]
   [next.jdbc :as jdbc]
   [allentiak.most-experienced-developers-according-to-github.database-creation.queries :as queries]))

(defn with-files-path
  [test-fn]
  (def test-resources-root-dir "resources/test/database_creation/")
  (test-fn)
  (ns-unmap *ns* 'test-resources-root-dir))

(def ^:private db-spec
  {:dbtype "h2"
   :dbname "local-db"
   :database_to_upper false})

(def ^:private ds
  (jdbc/get-datasource db-spec))

(def ^:private dummy-data
  #{{:name "name" :login "login"}
    {:name nil :login "abc"}})

(defn- with-test-db
  "A test fixture that sets up an in-memory H2 database for running tests."
  [test-fn]
  (try
    (commands/create-db-tables! ds)
    (commands/load-data! ds dummy-data)
    (catch Exception _))
  (test-fn)
  (commands/destroy-data! ds)
  (commands/drop-all-tables! ds))

(use-fixtures
  :once with-files-path
  :each with-test-db)

(comment
  (commands/create-db-tables! ds)
  ;; => [#:next.jdbc{:update-count 0}]
  (commands/load-data! ds dummy-data)
  ;; => [#:members{:login "abc"} #:members{:login "login"}]
  (jdbc/execute! ds
                 ["select * from members"])
  ;; => [#:members{:login "abc", :name nil} #:members{:login "login", :name "name"}]
  (jdbc/execute! ds
                 ["select * from members where login = 'abc'"])
  ;; => [#:members{:login "abc", :name nil}]
  (queries/get-member-by-login ds "abc")
  ;; => [#:members{:login "abc", :name nil}]
  (queries/get-member-by-login ds "whatever")
  ;; => []
  (commands/destroy-data! ds)
  ;; => [#:next.jdbc{:update-count 2}]
  (commands/drop-all-tables! ds))
  ;; => [#:next.jdbc{:update-count 0}]

(defexpect database-creation-should
  (expecting "correctly persist dummy data"
             (expect (= (queries/get-member-by-login ds "abc")
                        [#:members{:login "abc" :name nil}]))
             (expect (= (queries/get-member-by-login ds "login")
                        [#:members{:login "login" :name "name"}]))
             (expect (= (queries/get-member-by-login ds "whatever")
                        [])))

  (expecting "correctly persist additional data"
             (let [_ (commands/insert-member! {:login "new-login" :name "New Name"})]
               (expect (= (queries/get-member-by-login ds "new-login")
                          [#:members{:login "new-login" :name "New Name"}]))))
  (expecting "respect persistence schema"
             (expect (m/validate schemas/members-table (queries/get-all-members ds)))))
