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

(defn- login-response-map
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

(defn login-set-response-maps
  ([root-endpoint login-set]
   (map #(login-response-map root-endpoint %) login-set))
  ([login-set]
   (login-set-response-maps endpoints/root-url login-set)))

(comment
  (login-set-response-maps #{"allentiak" "puredanger"})
;; => ({:html_url "https://github.com/allentiak", :gravatar_id "", :followers_url "https://api.github.com/users/allentiak/followers", :subscriptions_url "https://api.github.com/users/allentiak/subscriptions", :site_admin false, :email nil, :following_url "https://api.github.com/users/allentiak/following{/other_user}", :hireable true, :name "Leandro Doctors", :node_id "MDQ6VXNlcjE5MjIyOTc=", :type "User", :twitter_username nil, :received_events_url "https://api.github.com/users/allentiak/received_events", :login "allentiak", :following 14, :updated_at "2023-10-09T12:42:25Z", :bio nil, :organizations_url "https://api.github.com/users/allentiak/orgs", :id 1922297, :events_url "https://api.github.com/users/allentiak/events{/privacy}", :url "https://api.github.com/users/allentiak", :public_gists 0, :repos_url "https://api.github.com/users/allentiak/repos", :public_repos 56, :starred_url "https://api.github.com/users/allentiak/starred{/owner}{/repo}", :location "The world's capital of Tango ;-)", :blog "", :followers 34, :company nil, :gists_url "https://api.github.com/users/allentiak/gists{/gist_id}", :created_at "2012-07-04T14:13:22Z", :avatar_url "https://avatars.githubusercontent.com/u/1922297?v=4"} {:html_url "https://github.com/puredanger", :gravatar_id "", :followers_url "https://api.github.com/users/puredanger/followers", :subscriptions_url "https://api.github.com/users/puredanger/subscriptions", :site_admin false, :email nil, :following_url "https://api.github.com/users/puredanger/following{/other_user}", :hireable nil, :name "Alex Miller", :node_id "MDQ6VXNlcjE3MTEyOQ==", :type "User", :twitter_username nil, :received_events_url "https://api.github.com/users/puredanger/received_events", :login "puredanger", :following 4, :updated_at "2023-11-09T00:58:01Z", :bio "Clojure maintainer at Nubank. Creator of Strange Loop, Clojure/west, and Lambda Jam conferences. Co-author of Programming Clojure and Clojure Applied.", :organizations_url "https://api.github.com/users/puredanger/orgs", :id 171129, :events_url "https://api.github.com/users/puredanger/events{/privacy}", :url "https://api.github.com/users/puredanger", :public_gists 29, :repos_url "https://api.github.com/users/puredanger/repos", :public_repos 113, :starred_url "https://api.github.com/users/puredanger/starred{/owner}{/repo}", :location "St. Louis, MO", :blog "https://insideclojure.org/", :followers 839, :company "@nubank, @strangeloop ", :gists_url "https://api.github.com/users/puredanger/gists{/gist_id}", :created_at "2009-12-22T21:04:26Z", :avatar_url "https://avatars.githubusercontent.com/u/171129?v=4"})
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
