(ns allentiak.most-experienced-developers-according-to-github.data-conversion-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.data-conversion :as sut]
   [allentiak.most-experienced-developers-according-to-github.data-conversion.schemas :as schemas]
   [clojure.data.json :as json]
   [clojure.test :refer [use-fixtures]]
   [expectations.clojure.test :refer [defexpect expect expecting]]
   [malli.core :as m]
   [clojure.edn :as edn]))

(defn with-files-path
  [test-fn]
  (def test-resources-root-dir "resources/test/data_conversion/")
  (test-fn)
  (ns-unmap *ns* 'test-resources-root-dir))

(use-fixtures :once with-files-path)

(defexpect data-conversion-should
  (expecting "convert members-response -> user-login-set"
             (let [mocked-members-response (edn/read-string
                                            (slurp (str test-resources-root-dir "members-response--full.edn")))
                   mocked-user-login-set (edn/read-string
                                          (slurp (str test-resources-root-dir "members-login-set--full.edn")))]
               (expect (m/validate schemas/user-logins-set (sut/members-response->user-logins-set mocked-members-response)))
               (expect (= (sut/members-response->user-logins-set mocked-members-response)
                          mocked-user-login-set))))
  (expecting "convert user-login-responses-seq -> member-table-data-set"
             (let [mocked-user-login-responses-seq (edn/read-string
                                                    (slurp (str test-resources-root-dir "users-data-response--full.edn")))
                   mocked-member-table-data-set (edn/read-string
                                                 (slurp (str test-resources-root-dir "members-table-data-set.edn")))
                   actual-member-table-data-set (sut/user-login-responses-set->members-table-data-set mocked-user-login-responses-seq)]
               (expect (m/validate schemas/members-table-data actual-member-table-data-set))
               (expect (= (sut/user-login-responses-set->members-table-data-set mocked-user-login-responses-seq)
                          mocked-member-table-data-set)))))
