(ns itedge.service-hub-examples.in-memory.validators
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
      (validators-util/validate-insert-fields attributes #{:username :password})
      (validators-util/validate-unique-fields attributes user-handler #{:username})
      (validators-util/validate-insert-update-relations attributes role-key role-handler)))
  (validate-update-entity [_ attributes]
    (util/pipeline-statements
      (validators-util/validate-entity-still-there attributes user-handler)
      (validators-util/validate-update-fields attributes #{:username :password})
      (validators-util/validate-unique-fields attributes user-handler #{:username})
      (validators-util/validate-insert-update-relations attributes role-key role-handler)))
  (validate-delete-entity [_ id]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to]
    (util/pipeline-statements
      (validators-util/validate-list-range from to criteria user-handler))))

(deftype RoleValidator [role-key role-handler user-key user-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id]
    (util/pipeline-statements
      (validators-util/validate-entity-present id role-handler)))
  (validate-add-entity [_ attributes]
    (util/pipeline-statements
      (validators-util/validate-insert-fields attributes #{:rolename})
      (validators-util/validate-unique-fields attributes role-handler #{:rolename})
      (validators-util/validate-insert-update-relations attributes user-key user-handler)))
  (validate-update-entity [_ attributes]
    (util/pipeline-statements
      (validators-util/validate-entity-still-there attributes role-handler)
      (validators-util/validate-update-fields attributes #{:rolename})
      (validators-util/validate-unique-fields attributes role-handler #{:rolename})
      (validators-util/validate-insert-update-relations attributes user-key user-handler)))
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
      (validators-util/validate-insert-fields attributes #{:product_name :price :order_number})
      (validators-util/validate-unique-fields attributes product-handler #{:product_name :order_number})))
  (validate-update-entity [_ attributes]
    (util/pipeline-statements
      (validators-util/validate-entity-still-there attributes product-handler)
      (validators-util/validate-update-fields attributes #{:product_name :price :order_number})
      (validators-util/validate-unique-fields attributes product-handler #{:product_name :order_number})))
  (validate-delete-entity [_ id]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to]
    (util/pipeline-statements
      (validators-util/validate-list-range from to criteria product-handler))))
