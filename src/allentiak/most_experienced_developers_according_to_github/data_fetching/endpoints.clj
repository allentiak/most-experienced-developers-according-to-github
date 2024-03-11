(ns allentiak.most-experienced-developers-according-to-github.data-fetching.endpoints)

(def ^:const root-url "https://api.github.com")


(defn get-members-url
  [org-name]
  (str root-url "/orgs/" org-name "/members"))


(defn get-user-login-url
  [login]
  (str root-url "/users/" login))
