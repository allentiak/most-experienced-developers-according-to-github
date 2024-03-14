(ns allentiak.most-experienced-developers-according-to-github.core-test
  (:require
   [allentiak.most-experienced-developers-according-to-github.core :as sut]
   [allentiak.most-experienced-developers-according-to-github.data-conversion.schemas :as schemas]
   [clojure.edn :as edn]
   [clojure.test :refer [use-fixtures]]
   [expectations.clojure.test :refer [defexpect expect expecting]]
   [malli.core :as m]))

(defn with-defs
  [test-fn]
  (def test-resources-root-dir "resources/test/core/")
  (def ^:const org-name "codecentric")
  (test-fn)
  (ns-unmap *ns* 'org-name)
  (ns-unmap *ns* 'test-resources-root-dir))

(use-fixtures :once with-defs)

(defexpect generate-members-table-should
  (expecting "generate members table from org-name"
             (let [generated-members-table-data-set (sut/generate-members-table org-name)]
               (expect (m/validate schemas/members-table-data generated-members-table-data-set))))
  (expecting "persist generated data"))

(comment
  (m/validate schemas/members-table-data #{{:name "asv" :login "ass"}
                                           {:login "suss" :name "ff"}})
  ;; => true
  (m/validate (m/schema
               [:maybe
                [:string {:min 1}]]) nil)
  ;; => true

  (let [my-schema (m/schema
                   [:set
                    [:map
                     {:closed true}
                     [:login
                      [:string {:min 1}]]
                     [:name
                      [:string]]]])]

    (m/validate my-schema #{{:login "dickerpulli", :name "Thomas Bosch"}
                            {:login "denniseffing", :name "Dennis Effing"}
                            {:login "devshred", :name "Johannes Schmidt"}
                            {:login "mniehoff", :name "Matthias Niehoff"}
                            {:login "dvdgsng", :name nil}
                            {:login "mkresse", :name "Martin Kresse"}
                            {:login "danielkocot", :name "Daniel Kocot"}
                            {:login "rfalke", :name nil}
                            {:login "feltenj", :name nil}
                            {:login "robaca", :name "Carsten Rohrbach"}
                            {:login "renetalk", :name "René Bohrenfeldt"}
                            {:login "s7nio", :name "Sebastian Steiner"}
                            {:login "F43nd1r", :name nil}
                            {:login "ljouon", :name "Lars Jouon"}
                            {:login "rueckemann", :name "Lars Rückemann"}
                            {:login "goekhanm", :name "Gökhan Makinist"}
                            {:login "jonashackt", :name "Jonas Hecht"}
                            {:login "lukas-reining", :name "Lukas Reining"}
                            {:login "PSanetra", :name "Philip Sanetra"}
                            {:login "maiksensi", :name "maiksensi"}
                            {:login "erikpetzold", :name "Erik Petzold"}
                            {:login "hw-cc", :name nil}
                            {:login "rhoetsc", :name "Ronald Hötschl"}
                            {:login "romansey", :name "Roman Seyffarth"}
                            {:login "dirkheinkecc", :name "Dirk Heinke"}
                            {:login "danielschleindlsperger", :name "Daniel Schleindlsperger"}
                            {:login "danielbayerlein", :name "Daniel Bayerlein"}
                            {:login "reitzig", :name "Raphael"}
                            {:login "JonasGroeger", :name "Jonas Gröger"}
                            {:login "romanigk", :name "Robert Manigk"}})))
