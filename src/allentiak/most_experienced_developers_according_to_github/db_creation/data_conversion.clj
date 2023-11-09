(ns allentiak.most-experienced-developers-according-to-github.db-creation.data-conversion
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching :as fetch]
   [clojure.data.json :as json]))

(defn- generate-login-set
  [members-response-map]
  (set (map :login members-response-map)))

(comment
  (generate-login-set #{{:login "user-1"},{:login "user-2"}})
;; => #{"user-1" "user-2"}
  ,)

(defn- generate-single-member-map
  [login]
  (let [login-response-map (fetch/login-response-map login)]
    {:login login
     :name (:name login-response-map)}))

(comment
  (let [login "allentiak"]
    (fetch/login-response-map login)
    (generate-single-member-map login))
  ,)

(defn generate-members-map
  [login-set]
  (map generate-single-member-map login-set))

(comment
  (generate-members-map #{"allentiak", "puredanger"})
;; => ({:login "allentiak", :name "Leandro Doctors"} {:login "puredanger", :name "Alex Miller"})
  ,)
