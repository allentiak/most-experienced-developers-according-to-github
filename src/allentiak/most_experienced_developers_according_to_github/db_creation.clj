(ns allentiak.most-experienced-developers-according-to-github.db-creation
  (:require
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.data-fetching :as fetch]
   [clojure.data.json :as json]))

(defn generate-login-set
  [members-response-map]
  (set (map :login members-response-map)))

(comment
  (generate-login-set #{{:login "user-1"},{:login "user-2"}})
;; => #{"user-1" "user-2"}
  ,)

(defn generate-single-member-map
  [login]
  {:login login
   :name (fetch/user-name login)})

(comment
  (generate-single-member-map {:login "blah"})
  ,)

(defn generate-members-map
  [login-set]
  (map generate-single-member-map login-set))

(comment
  (require '[clojure.data.json :as json])
  (require '[clojure.pprint :as pprint])
  (def members-json-file
    (slurp "resources/members--minimized.json"))
  (let [members-json (json/read-str members-json-file
                                    :key-fn keyword)]
    (set (map :login members-json))))
