(ns allentiak.most-experienced-developers-according-to-github.db-creation.database-creation-test
  (:require
    [allentiak.most-experienced-developers-according-to-github.db-creation.database-creation :as sut]
    [clojure.test :refer [deftest testing use-fixtures]]
    [expectations.clojure.test :refer [expect]]
    [next.jdbc :as jdbc]))


(defn create-db-conn
  [])


(defn load-test-data
  [conn])


(defn add-user
  [conn user])


(defn check-user
  [conn user])


(defn destroy-test-data
  [conn])


(defn close-db-conn
  [conn])


(def ^:dynamic *conn*)


(defn with-test-db
  [test-fn]
  (binding [*conn* (create-db-conn)]
    (load-test-data *conn*)
    (test-fn)
    (destroy-test-data *conn*)
    (close-db-conn *conn*)))


(use-fixtures :each with-test-db)


(deftest add-user-to-database-test
  (testing "adding user to the database"
    (expect (= (add-user *conn* "user") "whatever"))
    (expect (= (check-user *conn* "user") "whatever"))))


(comment
  (require '[next.jdbc :as jdbc])
  (def db {:dbtype "h2:mem" :dbname "example"})
  (def ds (jdbc/get-datasource db))
  (jdbc/execute! ds ["drop table if exists members"])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["create table if not exists
                      members (
                              login varchar(50) primary key,
                              name varchar(100)
                              )
                     "])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["
                     insert into members(login,name)
                     values('allentiak','Leandro Doctors'),
                           ('puredanger','Alex Miller')
"])
  ;; => [#:next.jdbc{:update-count 2}]
  (jdbc/execute! ds ["select * from members"])
  ;; => [#:MEMBERS{:LOGIN "allentiak", :NAME "Leandro Doctors"} #:MEMBERS{:LOGIN "puredanger", :NAME "Alex Miller"}]
  (jdbc/execute! ds ["drop table if exists languages"])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["create table if not exists
                      languages (
                                id int auto_increment primary key,
                                name varchar(50)
                                )
                     "])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["insert into languages(name)
                     values('clojure')"])
  ;; => [#:next.jdbc{:update-count 1}]
  (jdbc/execute! ds ["insert into languages(name)
                      values('python'),
                            ('rust')"])
  ;; => [#:next.jdbc{:update-count 2}]
  (jdbc/execute! ds ["select * from languages"])
  ;; => [#:LANGUAGES{:ID 1, :NAME "clojure"} #:LANGUAGES{:ID 2, :NAME "python"} #:LANGUAGES{:ID 3, :NAME "rust"}]
  (jdbc/execute! ds ["drop table if exists languages"])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["drop table if exists repos"])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["create table if not exists
                      repos (
                             owner varchar(50),
                             name varchar(50),
                             main_language int,
                             primary key(owner,name),
                             foreign key(owner) references members(login),
                             foreign key(main_language) references languages(id)
)"])
  ;; => [#:next.jdbc{:update-count 0}]
  (jdbc/execute! ds ["select * from repos"])
  ;; => []
  (jdbc/execute! ds ["insert into repos(owner,name,main_language)
                      values('allentiak','resume', null),
                      ('puredanger', 'buildtest',1)"])
  ;; => [#:next.jdbc{:update-count 2}]
  (jdbc/execute! ds ["select * from repos"])
  ;; => [#:REPOS{:OWNER "allentiak", :NAME "resume", :MAIN_LANGUAGE nil} #:REPOS{:OWNER "puredanger", :NAME "buildtest", :MAIN_LANGUAGE 1}]
  ,)
