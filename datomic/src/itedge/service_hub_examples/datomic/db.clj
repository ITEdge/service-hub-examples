(ns itedge.service-hub-examples.datomic.db
  (:require [datomic.api :as d :refer [connect create-database transact db shutdown]]))

(def uri "datomic:mem://sample-datomic")
(create-database uri)
(def conn (connect uri))

(defn setup-db []
  (let [schema-tx (read-string (slurp "resources/schema.edn"))
        data-tx (read-string (slurp "resources/data.edn"))]
    @(transact conn schema-tx) 
    @(transact conn data-tx)))

(defn get-db-value []
  (db conn))

(defn shutdown-db []
  (shutdown false))
