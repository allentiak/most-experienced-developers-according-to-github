(ns allentiak.most-experienced-developers-according-to-github.core-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.core :as sut]
   [clj-http.client :as http]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest github-orgs-members-rest-api-test
  (testing "actual GitHub REST API JSON response"
    (let [response-body (:body (http/get sut/members-endpoint-url))
          json-response (json/read-str response-body :key-fn keyword)]
      (expect (m/validate sut/members-json-schema json-response))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-json-response (json/read-str
                                (slurp "resources/members--minimized.json")
                                :key-fn keyword)]
      (expect (m/validate sut/members-json-schema mocked-json-response)))))

(comment
  (->
   (http/get (sut/members-endpoint-url {:accept :json}))
   :body))

(deftest github-user-rest-api-test
  (testing "actual GitHub REST API JSON response"
    (let [sample-login "allentiak"
          sample-login-endpoint-url (sut/get-user-endpoint-url sut/root-endpoint-url sample-login)
          response-body (:body (http/get sample-login-endpoint-url))
          json-response (json/read-str response-body
                                       :key-fn keyword)]
      (expect (m/validate sut/user-json-schema json-response))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-json-response (json/read-str
                                (slurp "resources/user--minimized.json")
                                :key-fn keyword)]
      (expect (m/validate sut/user-json-schema mocked-json-response)))))
