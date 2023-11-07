(ns allentiak.most-experienced-developers-according-to-github.core
  (:gen-class)
  (:require
   [malli.core :as m]))

(def ^:const root-endpoint-url "https://api.github.com")

(def ^:const org-name "codecentric")

(defn get-members-endpoint-url [root-endpoint-url org-name]
  (str root-endpoint-url "/orgs/" org-name "/members"))

(def ^:const members-endpoint-url
  (get-members-endpoint-url root-endpoint-url org-name))

(defn get-user-endpoint-url [root-endpoint-url login]
  (str root-endpoint-url "/users/" login))

(defn get-repos-per-login-endpoint-url [root-endpoint-url login]
  (str root-endpoint-url "/users/" login "/repos"))

(def members-json-schema
  (m/schema
   [:vector
    [:map
     [:login
      [:string {:min 1}]]]]))

(def user-json-schema
  (m/schema
   [:map
    [:login
     [:string {min 1}]]
    [:name
     [:string {min 1}]]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Howdy! Invocation args are: " args))

(comment
  (require '[malli.experimental.describe :as med])
  (require '[clojure.data.json :as json])
  (require '[clojure.pprint :as pprint])

  (med/describe [:map {:closed true}
                 [:x {:optional true} int?]
                 [:y :boolean]])

  (med/describe [:vector
                 [:map
                  [:login [:string {:min 1}]]]])

  (defn json-sample-file []
    (json/read-str
     (slurp "resources/members--codecentric--minimized.json")
     :key-fn keyword))

  (defn json-sample-file []
    (json/read-str
     (slurp "resources/members--minimized.json")
     :key-fn keyword))
  (json/read-str "[{\"login\": \"soitensa\"}, {\"login\":\"rtdud\"}]"
                 :key-fn keyword)

  (m/validate members-json-schema (json/read-str (slurp "resources/members--minimized.json")))
  (m/explain members-json-schema (j))
  (m/validate members-json-schema (json-sample-file))
  (m/explain members-json-schema (json-sample-file))
  ,)
