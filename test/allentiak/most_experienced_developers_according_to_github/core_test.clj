(ns allentiak.most-experienced-developers-according-to-github.core-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.core :as sut]
   [clj-http.client :as http]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest github-members-rest-api
  (testing "actual GitHub REST API JSON response..."
    (let [response-body (:body (http/get sut/members-endpoint-url))
          json-response (json/read-str response-body :key-fn keyword)]
      (expect (m/validate sut/members-json-schema json-response))))
  (testing "manually downloaded minimized JSON files..."
    (let [mocked-json-response (json/read-str
                                (slurp "resources/members--minimized.json")
                                :key-fn keyword)]
      (expect (m/validate sut/members-json-schema mocked-json-response)))))

(comment
  (->
   (http/get (sut/members-endpoint-url {:accept :json}))
   :body))
