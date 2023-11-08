(ns allentiak.most-experienced-developers-according-to-github.github-rest-client)

(comment
  (require '[malli.experimental.describe :as med])
  (require '[clojure.data.json :as json])
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
