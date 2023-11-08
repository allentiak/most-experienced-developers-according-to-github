(ns allentiak.most-experienced-developers-according-to-github.github-rest-client.endpoints)

(def ^:const root-url "https://api.github.com")
(def ^:const org-name "codecentric")

(defn get-members-url [root-endpoint-url org-name]
  (str root-endpoint-url "/orgs/" org-name "/members"))

(def ^:const members-url
  (get-members-url root-url org-name))

(defn get-user-url [root-endpoint-url login]
  (str root-endpoint-url "/users/" login))

(defn get-repos-per-login-url [root-endpoint-url login]
  (str (get-user-url root-endpoint-url login) "/repos"))
