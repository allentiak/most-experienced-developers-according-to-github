(ns allentiak.most-experienced-developers-according-to-github.core-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.core :as sut]
   [clj-http.client :as http]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest github-rest-api
  (testing "querying GitHub REST API..."
    (let [response-body (:body (http/get "https://api.github.com/orgs/codecentric/members"))]
      (expect (m/validate sut/members-json-schema response-body)))))

(comment
  (->
    (http/get "https://api.github.com/orgs/codecentric/members" {:accept :json})
    :body))
