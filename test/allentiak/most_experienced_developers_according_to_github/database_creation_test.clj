(ns allentiak.most-experienced-developers-according-to-github.database-creation-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.database-creation
    :as sut]
   [allentiak.most-experienced-developers-according-to-github.database-creation.commands
    :as commands]
   [allentiak.most-experienced-developers-according-to-github.data-conversion.schemas :as schemas]
   [clojure.test :refer [use-fixtures]]
   [malli.core :as m]
   [next.jdbc :as jdbc]))

(defn with-files-path
  [test-fn]
  (def test-resources-root-dir "resources/test/data_conversion/")
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
    (commands/create-db-tables ds)
    (commands/load-data ds dummy-data)
    (catch Exception _))
  (test-fn)
  (commands/drop-all-tables ds))

(use-fixtures
  :once with-files-path
  :each with-test-db)

(defexpect persist-should
  (expecting "persist data"
             (expect (m/validate))))
