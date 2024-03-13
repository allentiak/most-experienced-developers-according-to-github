(ns allentiak.most-experienced-developers-according-to-github.data-fetching-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.data-fetching :as sut]
   [allentiak.most-experienced-developers-according-to-github.data-fetching.endpoints :as endpoints]
   [allentiak.most-experienced-developers-according-to-github.data-fetching.schemas :as schemas]
   [clojure.edn :as edn]
   [clojure.test :refer [use-fixtures]]
   [expectations.clojure.test :refer [defexpect expect expecting]]
   [malli.core :as m]))

(defn with-defs
  [test-fn]
  (def test-resources-root-dir "resources/test/data_fetching/")
  (def ^:const org-name "codecentric")
  (test-fn)
  (ns-unmap *ns* 'org-name)
  (ns-unmap *ns* 'test-resources-root-dir))

(use-fixtures :once with-defs)

(defexpect org-members-fetching-should
  (expecting "get members list"
    #_(expecting "actual GitHub REST API JSON response"
        (expect (m/validate schemas/members-response (sut/org-members org-name))))
    (expecting "manually downloaded minimized JSON files"
      (let [mocked-members-response (edn/read-string
                                      (slurp (str test-resources-root-dir "members-response--full.edn")))]
        (expect (m/validate schemas/members-response mocked-members-response))))))

(comment
  (let [test-resources-root-dir "resources/test/data_fetching/"]
    (slurp (str test-resources-root-dir "members--minimized.json")))
  ;; works

  (require '[clj-http.client :as http])
  (->
   (http/get (endpoints/get-members-url {:accept :json}))
   :body))


(defexpect users-data-fetching-should
  (expecting "get data from a set of users"
    #_(expecting "actual GitHub REST API JSON response"
        (let [users #{"allentiak" "puredanger"}]
          (expect (m/validate schemas/user-login-responses-seq (sut/users-data users)))))
    (expecting "manually downloaded minimized JSON files"
      (let [mocked-user-login-responses-seq (edn/read-string (slurp (str test-resources-root-dir "users-data-response--full.edn")))]
        (expect (m/validate schemas/user-login-responses-seq mocked-user-login-responses-seq))))))
