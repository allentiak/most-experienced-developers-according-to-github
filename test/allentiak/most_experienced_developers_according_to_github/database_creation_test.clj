(ns allentiak.most-experienced-developers-according-to-github.database-creation-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.database-creation :as sut]
   [allentiak.most-experienced-developers-according-to-github.database-creation.schemas :as schemas]
   [clojure.test :refer [use-fixtures]]
   [expectations.clojure.test :refer [defexpect expect expecting]]
   [malli.core :as m]
   [clojure.edn :as edn]))

(defn with-files-path
  [test-fn]
  (def test-resources-root-dir "resources/test/data_conversion/")
  (test-fn)
  (ns-unmap *ns* 'test-resources-root-dir)

 (use-fixtures :once with-files-path))

#_(defexpect persist-should
    (expecting "persist data"
               (expect)))
