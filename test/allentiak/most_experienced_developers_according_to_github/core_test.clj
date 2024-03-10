(ns allentiak.most-experienced-developers-according-to-github.core-test
  (:require
    [allentiak.most-experienced-developers-according-to-github.core :as sut]
    [expectations.clojure.test :refer [defexpect expect expecting]]))


(defexpect core-should
  (expecting "put 1 and 1 together :)"
    (expect (= (+ 1 1) 2))))
