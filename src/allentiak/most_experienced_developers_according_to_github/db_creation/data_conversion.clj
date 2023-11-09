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

(defn- generate-repo-per-login
  [repo-from-repos-response]
  {:owner (:login (:owner repo-from-repos-response))
   :name (:name repo-from-repos-response)
   :main-language (:language repo-from-repos-response)})

(comment
  (generate-repo-per-login {:owner {:login "allentiak"}, :name "my-repo", :language "my-language"})
;; => {:name "my-repo", :main-language "my-language", :owner "allentiak"}
  (generate-repo-per-login {:owner {:login "allentiak"}, :name "my-other-repo", :language nil})
  ;; => {:name "my-other-repo", :main-language nil, :owner "allentiak"}
  ,)

(defn- generate-repos-per-login
  [login]
  (let [user-repos-response-map (fetch/user-repos-response-map login)]
    (map generate-repo-per-login user-repos-response-map)))

(comment
  (let [login "allentiak"]
    (take 3 (generate-repos-per-login login)))
;; => ({:name ".clojure", :main-language nil, :owner "allentiak"} {:name ".spacemacs.d", :main-language "Emacs Lisp", :owner "allentiak"} {:name "allentiak.github.io", :main-language "HTML", :owner "allentiak"})
  ,)

(defn generate-repos-map
  [login-set]
  (set (flatten (map generate-repos-per-login login-set))))

(comment
  (take 2 (shuffle (generate-repos-map #{"allentiak", "puredanger"})))
;; => ({:owner "puredanger", :name "clojure-1", :main-language nil} {:owner "allentiak", :name "mage", :main-language "Java"})
  ,)
