(ns itedge.service-hub-examples.in-memory.services
  (:require [itedge.service-hub-examples.in-memory.handlers :as handlers]
            [itedge.service-hub-examples.in-memory.validators :as validators]
            [itedge.service-hub-examples.in-memory.authorizators :as authorizators]
            [itedge.service-hub.core.services-util :as services-util]))

(def user-authorizator (authorizators/->UserAuthorizator))
(def role-authorizator (authorizators/->RoleAuthorizator))
(def product-authorizator (authorizators/->ProductAuthorizator))

(def user-validator (validators/->UserValidator handlers/user-handler :roles handlers/role-handler))
(def role-validator (validators/->RoleValidator :users handlers/role-handler :roles handlers/user-handler))
(def product-validator (validators/->ProductValidator handlers/product-handler))

(def user-service (services-util/scaffold-service handlers/user-handler user-validator user-authorizator))
(def role-service (services-util/scaffold-service handlers/role-handler role-validator role-authorizator))
(def product-service (services-util/scaffold-service handlers/product-handler product-validator product-authorizator))



