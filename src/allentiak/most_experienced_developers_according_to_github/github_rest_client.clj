(ns allentiak.most-experienced-developers-according-to-github.github-rest-client
  (:require
   [malli.core :as m]
   [allentiak.most-experienced-developers-according-to-github.github-rest-client.schemas :as schemas]))

(def ^:const root-endpoint-url "https://api.github.com")

(def ^:const org-name "codecentric")

(defn get-members-endpoint-url [root-endpoint-url org-name]
  (str root-endpoint-url "/orgs/" org-name "/members"))

(def ^:const members-endpoint-url
  (get-members-endpoint-url root-endpoint-url org-name))

(defn get-user-endpoint-url [root-endpoint-url login]
  (str root-endpoint-url "/users/" login))

(defn get-repos-per-login-endpoint-url [root-endpoint-url login]
  (str (get-user-endpoint-url root-endpoint-url login) "/repos"))

(comment
  (require '[malli.experimental.describe :as med])
  (require '[clojure.data.json :as json])
  (require '[clojure.pprint :as pprint])
  (require '[allentiak.most-experienced-developers-according-to-github.github-rest-client.schemas :as schemas])
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

  (m/validate schemas/members-response (json/read-str (slurp "resources/members--minimized.json")))
  (m/explain schemas/members-response (json-sample-file))
  (m/validate schemas/members-response (json-sample-file))
  (m/explain schemas/members-response (json-sample-file)))
