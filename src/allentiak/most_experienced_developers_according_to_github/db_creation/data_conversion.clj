(ns allentiak.most-experienced-developers-according-to-github.db-creation.data-conversion
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching :as fetch]))

(defn generate-members-login-set
  [members-response-map]
  (set (map :login members-response-map)))

(comment
  (generate-members-login-set #{{:login "user-1" :some-other-key "whatever"},
                                {:login "user-2" :another-irrelevant-key nil}})
;; => #{"user-1" "user-2"}
  ,)

(defn- generate-single-member-map
  [login-response-map]
  {:login (:login login-response-map)
   :name (:name login-response-map)})

(comment
  (fetch/user-response-map "allentiak")
  ;; the answer here is the user with a lot of keywords
  (generate-single-member-map (fetch/user-response-map "allentiak"))
  ;; => {:login "allentiak", :name "Leandro Doctors"}
  ,)

(defn generate-members-map
  [login-response-maps]
  (map generate-single-member-map login-response-maps))

(comment
  (fetch/login-set-responses-seq #{"allentiak" "puredanger"})
  ;; the answer here is the user seq of maps with a lot of keywords
  (generate-members-map (fetch/login-set-responses-seq #{"allentiak", "puredanger"}))
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
  (let [user-repos-response-map (fetch/user-repos-response-vector login)]
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

(defn generate-languages-map
  [repos-map]
  (let [languages-set (set (map :main-language repos-map))]
    (into #{} (map #(hash-map :name %) (filter some? languages-set)))))

(comment
  (into #{} (map #(hash-map :name %) (filter some? #{"one" nil})))
;; => #{{:name "one"}}
  (take 5 (generate-languages-map (generate-repos-map #{"allentiak", "puredanger"})))
;; => ({:name "CSS"} {:name "TypeScript"} {:name "Clojure"} {:name "Java"} {:name "Shell"})
  ,)
