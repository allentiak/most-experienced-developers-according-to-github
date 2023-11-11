(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching :as fetch]
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.endpoints :as endpoints]
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.schemas :as schemas]
   [clj-http.client :as http]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest org-members-fetching-test
  (testing "actual GitHub REST API JSON response"
    (expect (m/validate schemas/members-response (fetch/org-members-response-map endpoints/root-url endpoints/org-name))))
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
    (let [sample-user "allentiak"]
      (expect (m/validate schemas/user-response-map (fetch/user-response-map endpoints/root-url sample-user)))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-user-response-body-map (json/read-str
                                         (slurp "resources/user--minimized.json")
                                         :key-fn keyword)]
      (expect (m/validate schemas/user-response-map mocked-user-response-body-map)))))

(deftest user-repos-fetching-test
  (testing "actual GitHub REST API JSON response"
    (let [sample-user "allentiak"]
      (expect (m/validate schemas/repos-response (fetch/user-repos-response-map sample-user)))))
  (testing "manually downloaded minimized JSON files"
    (let [mocked-repos-response-body-map (json/read-str
                                          (slurp "resources/repos--allentiak--minimized.json")
                                          :key-fn keyword)]
      (expect (m/validate schemas/repos-response mocked-repos-response-body-map)))))
