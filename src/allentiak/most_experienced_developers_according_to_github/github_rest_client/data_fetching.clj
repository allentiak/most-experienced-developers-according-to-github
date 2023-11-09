(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.endpoints :as endpoints]
   [clj-http.client :as http]
   [clojure.data.json :as json]))

(defn org-members-response-map
  ([root-endpoint-url org-name]
   (let [members-endpoint-url (endpoints/get-members-url root-endpoint-url org-name)
         members-response-body (:body (http/get members-endpoint-url))
         members-response-map (json/read-str members-response-body
                                             :key-fn keyword)]
     members-response-map))
  ([org-name]
   (org-members-response-map endpoints/root-url org-name)))
org-members-response-map

(comment
  (org-members-response-map "codecentric")
  ;; it works - the response is just too big to be shown here :)
  ,)

(defn login-response-map
  ([root-endpoint-url login]
   (let [user-endpoint-url (endpoints/get-user-url root-endpoint-url login)
         login-response-body (:body (http/get user-endpoint-url))
         login-response-map (json/read-str login-response-body
                                           :key-fn keyword)]
     login-response-map))
  ([login]
   (login-response-map endpoints/root-url login)))

(comment
  (login-response-map "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)

(defn user-repos-response-map
  ([root-endpoint-url login]
   (let [repos-per-login-url (endpoints/get-repos-per-login-url root-endpoint-url login)
         repos-per-login-response-body (:body (http/get repos-per-login-url))
         repos-per-login-response-map (json/read-str repos-per-login-response-body
                                                     :key-fn keyword)]
     repos-per-login-response-map))
  ([login]
   (user-repos-response-map endpoints/root-url login)))

(comment
  (user-repos-response-map "allentiak")
 ;; it works - the response is just too big to be shown here :)
 ,)
