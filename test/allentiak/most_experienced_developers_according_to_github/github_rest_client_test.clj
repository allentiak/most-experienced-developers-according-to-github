(ns allentiak.most-experienced-developers-according-to-github.github-rest-client-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client :as sut]
   [clj-http.client :as http]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest github-org-members-rest-api-test
  (testing "actual GitHub REST API JSON response"
    (let [members-response-body-json (:body (http/get sut/members-endpoint-url))
          members-response-body-map (json/read-str members-response-body-json :key-fn keyword)]
      (expect (m/validate sut/members-response-schema members-response-body-map))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-members-response-body-map (json/read-str
                                            (slurp "resources/members--minimized.json")
                                            :key-fn keyword)]
      (expect (m/validate sut/members-response-schema mocked-members-response-body-map)))))

(comment
  (->
   (http/get (sut/members-endpoint-url {:accept :json}))
   :body))

(deftest github-login-rest-api-test
  (testing "actual GitHub REST API JSON response"
    (let [sample-login "allentiak"
          sample-login-endpoint-url (sut/get-user-endpoint-url sut/root-endpoint-url sample-login)
          login-response-body-json (:body (http/get sample-login-endpoint-url))
          login-response-body-map (json/read-str login-response-body-json
                                                 :key-fn keyword)]
      (expect (m/validate sut/login-response-schema login-response-body-map))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-login-response-body-map (json/read-str
                                          (slurp "resources/user--minimized.json")
                                          :key-fn keyword)]
      (expect (m/validate sut/login-response-schema mocked-login-response-body-map)))))

(deftest github-repos-rest-api-test
  (testing "actual GitHub REST API JSON response"
    (let [sample-login "allentiak"
          repos-per-sample-login-endpoint-url (sut/get-repos-per-login-endpoint-url sut/root-endpoint-url sample-login)
          repos-response-body-json (:body (http/get repos-per-sample-login-endpoint-url))
          repos-response-doby-map (json/read-str repos-response-body-json
                                                 :key-fn keyword)]
      (expect (m/validate sut/repos-response-schema repos-response-doby-map))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-repos-response-body-map (json/read-str
                                          (slurp "resources/repos--allentiak--minimized.json")
                                          :key-fn keyword)]
      (expect (m/validate sut/repos-response-schema mocked-repos-response-body-map)))))
