(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching
  (:require
    [allentiak.most-experienced-developers-according-to-github.github-rest-client.endpoints :as endpoints]
    [clj-http.client :as http]
    [clojure.data.json :as json]))


(defn org-members
  ([root-endpoint-url org-name]
   (let [members-endpoint-url (endpoints/get-members-url root-endpoint-url org-name)
         members-response-body (:body (http/get members-endpoint-url))
         members-response-vector (json/read-str members-response-body
                                                :key-fn keyword)]
     members-response-vector))
  ([org-name]
   (org-members endpoints/root-url org-name)))


(comment
  (org-members "codecentric")
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn user-data-by-login
  ([root-endpoint-url login]
   (let [user-endpoint-url (endpoints/get-user-url root-endpoint-url login)
         login-response-body (:body (http/get user-endpoint-url))
         login-response-map (json/read-str login-response-body
                                           :key-fn keyword)]
     login-response-map))
  ([login]
   (user-data-by-login endpoints/root-url login)))


(comment
  (user-data-by-login "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn users-data
  ([root-endpoint login-set]
   (map #(user-data-by-login root-endpoint %) login-set))
  ([login-set]
   (users-data endpoints/root-url login-set)))


(comment
  (users-data #{"allentiak" "puredanger"})
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn repos-by-login
  ([root-endpoint-url login]
   (let [repos-per-login-url (endpoints/get-repos-per-login-url root-endpoint-url login)
         repos-per-login-response-body (:body (http/get repos-per-login-url))
         repos-per-login-response-vector (json/read-str repos-per-login-response-body
                                                        :key-fn keyword)]
     repos-per-login-response-vector))
  ([login]
   (repos-by-login endpoints/root-url login)))


(comment
  (repos-by-login "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)
