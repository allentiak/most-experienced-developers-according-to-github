(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.endpoints :as endpoints]
   [clj-http.client :as http]
   [clojure.data.json :as json]))

(defn org-members-response-vector
  ([root-endpoint-url org-name]
   (let [members-endpoint-url (endpoints/get-members-url root-endpoint-url org-name)
         members-response-body (:body (http/get members-endpoint-url))
         members-response-vector (json/read-str members-response-body
                                                :key-fn keyword)]
     members-response-vector))
  ([org-name]
   (org-members-response-vector endpoints/root-url org-name)))

(comment
  (org-members-response-vector "codecentric")
  ;; it works - the response is just too big to be shown here :)
  ,)

(defn user-response-map
  ([root-endpoint-url login]
   (let [user-endpoint-url (endpoints/get-user-url root-endpoint-url login)
         login-response-body (:body (http/get user-endpoint-url))
         login-response-map (json/read-str login-response-body
                                           :key-fn keyword)]
     login-response-map))
  ([login]
   (user-response-map endpoints/root-url login)))

(comment
  (user-response-map "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)

(defn login-set-responses-seq
  ([root-endpoint login-set]
   (map #(user-response-map root-endpoint %) login-set))
  ([login-set]
   (login-set-responses-seq endpoints/root-url login-set)))

(comment
  (login-set-responses-seq #{"allentiak" "puredanger"})
  ;; it works - the response is just too big to be shown here :)
  ,)

(defn user-repos-response-vector
  ([root-endpoint-url login]
   (let [repos-per-login-url (endpoints/get-repos-per-login-url root-endpoint-url login)
         repos-per-login-response-body (:body (http/get repos-per-login-url))
         repos-per-login-response-vector (json/read-str repos-per-login-response-body
                                                        :key-fn keyword)]
     repos-per-login-response-vector))
  ([login]
   (user-repos-response-vector endpoints/root-url login)))

(comment
  (user-repos-response-vector "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)
