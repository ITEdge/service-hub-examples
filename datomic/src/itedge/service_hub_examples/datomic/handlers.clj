(ns itedge.service-hub-examples.datomic.handlers
  (:require [itedge.service-hub.core.handlers :refer :all]
            [itedge.service-hub.persistence-datomic.handlers-datomic :as handlers-datomic]))

(def user-handler (handlers-datomic/create-handler nil #{:user/username :user/password :user/roles}))
(def role-handler (handlers-datomic/create-handler nil #{:role/users :role/rolename}))
(def product-handler (handlers-datomic/create-handler nil #{:product/name :product/order-code :product/price}))
