(ns allentiak.most-experienced-developers-according-to-github.data-fetching-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.data-fetching :as sut]
   [allentiak.most-experienced-developers-according-to-github.data-fetching.endpoints :as endpoints]
   [allentiak.most-experienced-developers-according-to-github.data-fetching.schemas :as schemas]
   [clojure.data.json :as json]
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
    (expecting "actual GitHub REST API JSON response"
      (expect (m/validate schemas/members-response (sut/org-members org-name))))
    (expecting "manually downloaded minimized JSON files"
      (let [mocked-members-response-body-map (json/read-str
                                              (slurp (str test-resources-root-dir "members--minimized.json"))
                                              :key-fn keyword)]
        (expect (m/validate schemas/members-response mocked-members-response-body-map))))))

(comment
  (let [test-resources-root-dir "resources/test/data_fetching/"]
    (slurp (str test-resources-root-dir "members--minimized.json")))
  ;; works

  (require '[clj-http.client :as http])
  (->
   (http/get (endpoints/get-members-url {:accept :json}))
   :body))

(defexpect login-fetching-should
  (expecting "get data from a user"
    (expecting "actual GitHub REST API JSON response"
      (let [sample-user "allentiak"]
        (expect (m/validate schemas/user-login-response (sut/user-data-by-login sample-user)))))
    (expecting "manually downloaded minimized JSON files"
      (let [mocked-allentiak-user-response-body-map (json/read-str
                                                     (slurp (str test-resources-root-dir "user--allentiak--minimized.json"))
                                                     :key-fn keyword)
            mocked-puredanger-user-response-body-map (json/read-str
                                                      (slurp (str test-resources-root-dir "user--puredanger--minimized.json"))
                                                      :key-fn keyword)]
        (expect (m/validate schemas/user-login-response mocked-allentiak-user-response-body-map))
        (expect (m/validate schemas/user-login-response mocked-puredanger-user-response-body-map))))))

(defexpect users-data-fetching-should
  (expecting "get data from a set of users"
    (expecting "actual GitHub REST API JSON response"
      (let [users #{"allentiak" "puredanger"}]
        (expect (m/validate schemas/user-logins-seq (sut/users-data users)))))
    (expecting "manually downloaded minimized JSON files"
      (let [mocked-user-response-maps-set (read-string (slurp (str test-resources-root-dir "users-data--minimized.edn")))]
        (expect (m/validate schemas/user-logins-seq mocked-user-response-maps-set))))))
