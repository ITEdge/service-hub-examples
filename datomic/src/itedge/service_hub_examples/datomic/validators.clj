(ns itedge.service-hub-examples.datomic.validators
  (:use itedge.service-hub.core.validators)
  (:require [itedge.service-hub.core.util :as util]
            [itedge.service-hub.core.validators-util :as validators-util]))

(deftype UserValidator [user-handler role-key role-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id]
    (util/pipeline-statements
     (validators-util/validate-entity-present id user-handler)))
  (validate-add-entity [_ attributes]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:user/username :user/password})
     (validators-util/validate-unique-fields attributes user-handler #{:user/username})
     (validators-util/validate-insert-update-relations attributes role-key role-handler)))
  (validate-update-entity [_ attributes]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes user-handler)
     (validators-util/validate-update-fields attributes #{:user/username :user/password})
     (validators-util/validate-unique-fields attributes user-handler #{:user/username})
     (validators-util/validate-insert-update-relations attributes role-key role-handler)))
  (validate-delete-entity [_ id]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria user-handler))))

(deftype RoleValidator [user-handler role-key role-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id]
    (util/pipeline-statements
     (validators-util/validate-entity-present id role-handler)))
  (validate-add-entity [_ attributes]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:role/rolename})
     (validators-util/validate-unique-fields attributes role-handler #{:role/rolename})))
  (validate-update-entity [_ attributes]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes role-handler)
     (validators-util/validate-update-fields attributes #{:role/rolename})
     (validators-util/validate-unique-fields attributes role-handler #{:role/rolename})))
  (validate-delete-entity [_ id]
    (util/pipeline-statements
     (validators-util/validate-delete-relations id role-key user-handler)))
  (validate-list-entities [_ criteria sort-attrs from to]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria role-handler))))

(deftype ProductValidator [product-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id]
    (util/pipeline-statements
     (validators-util/validate-entity-present id product-handler)))
  (validate-add-entity [_ attributes]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:product/name :product/orderCode :product/price})
     (validators-util/validate-unique-fields attributes product-handler #{:product/name :product/orderCode})))
  (validate-update-entity [_ attributes]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes product-handler)
     (validators-util/validate-update-fields attributes #{:product/name :product/orderCode :product/price})
     (validators-util/validate-unique-fields attributes product-handler #{:product/name :product/orderCode})))
  (validate-delete-entity [_ id]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria product-handler))))
