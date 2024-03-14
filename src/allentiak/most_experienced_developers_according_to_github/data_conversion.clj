(ns allentiak.most-experienced-developers-according-to-github.data-conversion)

(defn members-response->user-logins-set
  [members-response]
  (into #{} (map :login (set members-response))))

(comment
  (members-response->user-logins-set [{:login "user-1" :some-other-key "whatever"}
                                      {:login "user-2" :another-irrelevant-key nil}]))
  ;; => #{"user-1" "user-2"}

(defn- user-login-response->members-table-row
  "cleans up the user-login response into a member table row"
  [user-login-response]
  {:login (:login user-login-response)
   :name (:name user-login-response)})

(comment
  (require '[clojure.edn :as edn])
  (def mocked-members-data (edn/read-string
                            (slurp "resources/test/data_conversion/user-data-response--allentiak--full.edn")))

  (user-login-response->members-table-row mocked-members-data))
  ;; => {:login "allentiak", :name "Leandro Doctors"}

(defn user-login-responses-set->members-table-data-set
  [user-logins-set]
  (into #{} (map user-login-response->members-table-row user-logins-set)))

(comment
  (require '[clojure.edn :as edn])
  (defn mocked-users-data [] (edn/read-string
                              (slurp "resources/test/data_conversion/users-data-response--full.edn")))
  (mocked-users-data)
  (user-login-responses-set->members-table-data-set #{"allentiak" "puredanger"})
  ;; => #{{:login "puredanger", :name "Alex Miller"} {:login "allentiak", :name "Leandro Doctors"}}
  (user-login-responses-set->members-table-data-set (set (mocked-users-data))))
  ;; => #{{:login "puredanger", :name "Alex Miller"} {:login "allentiak", :name "Leandro Doctors"}}
