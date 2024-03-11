(ns allentiak.most-experienced-developers-according-to-github.data-fetching
  (:require
    [allentiak.most-experienced-developers-according-to-github.data-fetching.endpoints :as endpoints]
    [clj-http.client :as http]
    [clojure.data.json :as json]))


(defn org-members
  [org-name]
  (let [endpoint-url (endpoints/get-members-url org-name)
        response-body (:body (http/get endpoint-url))
        members-response (json/read-str response-body
                                        :key-fn keyword)]
    members-response))

(comment
  (org-members "codecentric")
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn- user-data-by-login
  [login]
  (let [endpoint-url (endpoints/get-user-login-url login)
        response-body (:body (http/get endpoint-url))
        user-login-response (json/read-str response-body
                                           :key-fn keyword)]
    user-login-response))

(comment
  (user-data-by-login "allentiak")
  ;; it works - the response is just too big to be shown here :)
  ,)


(defn users-data
  [login-set]
  (let [user-logins-seq (map user-data-by-login login-set)]
    user-logins-seq))


(comment
  (users-data #{"allentiak" "puredanger"})
  ;; it works - the response is just too big to be shown here :)
  ,)
