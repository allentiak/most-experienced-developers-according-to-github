(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.endpoints :as endpoints]
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.schemas :as schemas]
   [clj-http.client :as http]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest org-members-fetching-test
  (testing "actual GitHub REST API JSON response"
    (let [members-response-body-json (:body (http/get endpoints/members-url))
          members-response-body-map (json/read-str members-response-body-json :key-fn keyword)]
      (expect (m/validate schemas/members-response members-response-body-map))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-members-response-body-map (json/read-str
                                            (slurp "resources/members--minimized.json")
                                            :key-fn keyword)]
      (expect (m/validate schemas/members-response mocked-members-response-body-map)))))

(comment
  (->
   (http/get (endpoints/members-url {:accept :json}))
   :body))

(deftest login-fetching-test
  (testing "actual GitHub REST API JSON response"
    (let [sample-login "allentiak"
          sample-login-endpoint-url (endpoints/get-user-url endpoints/root-url sample-login)
          login-response-body-json (:body (http/get sample-login-endpoint-url))
          login-response-body-map (json/read-str login-response-body-json
                                                 :key-fn keyword)]
      (expect (m/validate schemas/login-response login-response-body-map))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-login-response-body-map (json/read-str
                                          (slurp "resources/user--minimized.json")
                                          :key-fn keyword)]
      (expect (m/validate schemas/login-response mocked-login-response-body-map)))))

(deftest repos-fetching-test
  (testing "actual GitHub REST API JSON response"
    (let [sample-login "allentiak"
          repos-per-sample-login-endpoint-url (endpoints/get-repos-per-login-url endpoints/root-url sample-login)
          repos-response-body-json (:body (http/get repos-per-sample-login-endpoint-url))
          repos-response-doby-map (json/read-str repos-response-body-json
                                                 :key-fn keyword)]
      (expect (m/validate schemas/repos-response repos-response-doby-map))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-repos-response-body-map (json/read-str
                                          (slurp "resources/repos--allentiak--minimized.json")
                                          :key-fn keyword)]
      (expect (m/validate schemas/repos-response mocked-repos-response-body-map)))))
