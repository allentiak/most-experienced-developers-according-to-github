(ns allentiak.most-experienced-developers-according-to-github.data-fetching
  (:require
    [allentiak.most-experienced-developers-according-to-github.data-fetching.endpoints :as endpoints]
    [clj-http.client :as http]
    [clojure.data.json :as json]))


(defn org-members
  [org-name]
  (let [members-endpoint-url (endpoints/get-members-url org-name)
        members-response-body (:body (http/get members-endpoint-url))
        members-response-vector (json/read-str members-response-body
                                               :key-fn keyword)]
    members-response-vector))

(comment
  (org-members "codecentric")
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn user-data-by-login
  [login]
  (let [user-endpoint-url (endpoints/get-user-login-url login)
        login-response-body (:body (http/get user-endpoint-url))
        login-response-map (json/read-str login-response-body
                                          :key-fn keyword)]
    login-response-map))

(comment
  (user-data-by-login "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn users-data
  [login-set]
  (map user-data-by-login login-set))


(comment
  (users-data #{"allentiak" "puredanger"})
  ;; it works - the response is just too big to be shown here :)
  ,)
