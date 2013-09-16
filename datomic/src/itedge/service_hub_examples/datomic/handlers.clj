(ns itedge.service-hub-examples.datomic.handlers
  (:require [itedge.service-hub.persistence-datomic.handlers-datomic :as handlers-datomic]))

(def user-handler (handlers-datomic/create-handler #{:user/username :user/password :user/roles}))
(def role-handler (handlers-datomic/create-handler #{:role/rolename}))
(def product-handler (handlers-datomic/create-handler #{:product/name :product/orderCode :product/price}))
