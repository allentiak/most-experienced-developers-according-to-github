(ns allentiak.most-experienced-developers-according-to-github.db-creation-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.db-creation.data-conversion :as sut]
   [allentiak.most-experienced-developers-according-to-github.db-creation.schemas :as schemas]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest table-maps-creation-test
  (testing "user table map creation"
    (let [members-response-body-map (json/read-str
                                     (slurp "resources/members--minimized.json")
                                     :key-fn keyword)
          generated-members-map (sut/generate-members-map members-response-body-map)]

      (expect (m/validate schemas/members-map generated-members-map)))))
