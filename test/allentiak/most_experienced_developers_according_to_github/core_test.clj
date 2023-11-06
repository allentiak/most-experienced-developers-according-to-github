(ns allentiak.most-experienced-developers-according-to-github.core-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.core :as sut]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]))

(deftest smoke-test
  (testing "this should fail"
    (expect (= (sut/foo) 2))))
