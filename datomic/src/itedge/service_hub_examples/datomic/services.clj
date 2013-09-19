(ns itedge.service-hub-examples.datomic.services
  (:require [itedge.service-hub-examples.datomic.db :as db]
            [itedge.service-hub-examples.datomic.handlers :as handlers]
            [itedge.service-hub-examples.datomic.validators :as validators]
            [itedge.service-hub-examples.datomic.authorizators :as authorizators]
            [itedge.service-hub.core.services-util :as services-util]))

(def user-authorizator (authorizators/->UserAuthorizator))
(def role-authorizator (authorizators/->RoleAuthorizator))
(def product-authorizator (authorizators/->ProductAuthorizator))

(def user-validator (validators/->UserValidator handlers/user-handler :user/roles handlers/role-handler))
(def role-validator (validators/->RoleValidator handlers/user-handler :user/roles handlers/role-handler))
(def product-validator (validators/->ProductValidator handlers/product-handler))

(def user-service (services-util/scaffold-service handlers/user-handler user-validator user-authorizator db/conn db/get-db-value))
(def role-service (services-util/scaffold-service handlers/role-handler role-validator role-authorizator db/conn db/get-db-value))
(def product-service (services-util/scaffold-history-enabled-service handlers/product-handler product-validator product-authorizator db/conn db/get-db-value))



