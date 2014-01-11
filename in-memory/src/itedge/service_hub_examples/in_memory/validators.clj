(ns itedge.service-hub-examples.in-memory.validators
  (:require [itedge.service-hub.core.util :as util]
            [itedge.service-hub.core.validators :refer :all]
            [itedge.service-hub.core.validators-util :as validators-util]))

(deftype UserValidator [user-handler role-key role-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-present id user-handler db-value)))
  (validate-add-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:username :password})
     (validators-util/validate-unique-fields attributes user-handler db-value #{:username})
     (validators-util/validate-insert-update-relations attributes role-key role-handler db-value)))
  (validate-update-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes user-handler db-value)
     (validators-util/validate-update-fields attributes #{:username :password})
     (validators-util/validate-unique-fields attributes user-handler db-value #{:username})
     (validators-util/validate-insert-update-relations attributes role-key role-handler db-value)))
  (validate-delete-entity [_ id db-value]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to db-value]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria user-handler db-value))))

(deftype RoleValidator [role-key role-handler user-key user-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-present id role-handler db-value)))
  (validate-add-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:rolename})
     (validators-util/validate-unique-fields attributes role-handler db-value #{:rolename})
     (validators-util/validate-insert-update-relations attributes user-key user-handler db-value)))
  (validate-update-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes role-handler db-value)
     (validators-util/validate-update-fields attributes #{:rolename})
     (validators-util/validate-unique-fields attributes role-handler db-value #{:rolename})
     (validators-util/validate-insert-update-relations attributes user-key user-handler db-value)))
  (validate-delete-entity [_ id db-value]
    (util/pipeline-statements
     (validators-util/validate-delete-relations id role-key user-handler db-value)))
  (validate-list-entities [_ criteria sort-attrs from to db-value]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria role-handler db-value))))

(deftype ProductValidator [product-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-present id product-handler db-value)))
  (validate-add-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:product_name :price :order_number})
     (validators-util/validate-unique-fields attributes product-handler db-value #{:product_name :order_number})))
  (validate-update-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes product-handler db-value)
     (validators-util/validate-update-fields attributes #{:product_name :price :order_number})
     (validators-util/validate-unique-fields attributes product-handler db-value #{:product_name :order_number})))
  (validate-delete-entity [_ id db-value]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to db-value]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria product-handler db-value))))
