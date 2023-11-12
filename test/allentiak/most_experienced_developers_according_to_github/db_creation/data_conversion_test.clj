(ns allentiak.most-experienced-developers-according-to-github.db-creation.data-conversion-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.db-creation.data-conversion :as sut]
   [allentiak.most-experienced-developers-according-to-github.db-creation.schemas :as schemas]
   [clojure.data.json :as json]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [malli.core :as m]))

(deftest table-maps-creation-test
  (testing "repos table map creation"
    (let [mocked-members-login-set #{"allentiak" "puredanger"}
          generated-repos-set (sut/generate-repos-set mocked-members-login-set)]
      (expect (m/validate schemas/repos-set generated-repos-set))))
  (testing "languages table map creation"
    (let [mocked-repos-set (slurp "resources/sample-repos-set.edn")
          generated-languages-set (sut/generate-languages-set mocked-repos-set)]
      (expect (m/validate schemas/languages-set generated-languages-set))))
  (testing "members table map creation"
    (let [mocked-members-response-set (set
                                       (json/read-str
                                        (slurp "resources/members--minimized.json")
                                        :key-fn keyword))
          members-login-set (sut/generate-members-login-set mocked-members-response-set)
          generated-members-set (sut/generate-members-set members-login-set)]
      (expect (m/validate schemas/members-set generated-members-set)))))

(comment
  (require '[allentiak.most-experienced-developers-according-to-github.db-creation.data-conversion :as sut]
           '[clojure.data.json :as json])
  (def members-response-set (set (json/read-str
                                  (slurp "resources/members--minimized.json")
                                  :key-fn keyword)))
  members-response-set
  ;; => #{{:login "puredanger"} {:login "allentiak"}}
  (def members-login-set
    (sut/generate-members-login-set members-response-set))
  members-login-set
  ;; => #{"allentiak" "puredanger"}
  (sut/generate-members-set members-login-set)
  ;; => #{{:login "puredanger", :name "Alex Miller"} {:login "allentiak", :name "Leandro Doctors"}}
  ,)
