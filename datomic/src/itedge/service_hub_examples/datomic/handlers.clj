(ns itedge.service-hub-examples.datomic.handlers
  (:require [datomic.api :as d :refer [q db entity create-database connect transact shutdown]]
            [itedge.service-hub.core.handlers :refer :all]
            [itedge.service-hub.persistence-datomic.handlers-datomic :as handlers-datomic]))

(def uri "datomic:mem://sample-datomic")
(create-database uri)
(def conn (connect uri))
(def schema-tx (read-string (slurp "resources/schema.edn")))
@(d/transact conn schema-tx)
(def data-tx (read-string (slurp "resources/data.edn")))
@(d/transact conn data-tx)

(def user-handler (handlers-datomic/create-handler conn #{:user/username :user/password :user/roles}))
(def role-handler (handlers-datomic/create-handler conn #{:role/users :role/rolename}))
(def product-handler (handlers-datomic/create-handler conn #{:product/name :product/orderCode :product/price}))
