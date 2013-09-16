(ns itedge.service-hub-examples.datomic.validators
  (:use itedge.service-hub.core.validators)
  (:require [itedge.service-hub.core.util :as util]
            [itedge.service-hub.core.validators-util :as validators-util]))

(deftype UserValidator [user-handler role-key role-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-present id user-handler db-value)))
  (validate-add-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:user/username :user/password})
     (validators-util/validate-unique-fields attributes user-handler db-value #{:user/username})
     (validators-util/validate-insert-update-relations attributes role-key role-handler db-value)))
  (validate-update-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes user-handler db-value)
     (validators-util/validate-update-fields attributes #{:user/username :user/password})
     (validators-util/validate-unique-fields attributes user-handler db-value #{:user/username})
     (validators-util/validate-insert-update-relations attributes role-key role-handler db-value)))
  (validate-delete-entity [_ id db-value]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to db-value]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria user-handler db-value))))

(deftype RoleValidator [user-handler role-key role-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-present id role-handler db-value)))
  (validate-add-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:role/rolename})
     (validators-util/validate-unique-fields attributes role-handler db-value #{:role/rolename})))
  (validate-update-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes role-handler db-value)
     (validators-util/validate-update-fields attributes #{:role/rolename})
     (validators-util/validate-unique-fields attributes role-handler db-value #{:role/rolename})))
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
     (validators-util/validate-insert-fields attributes #{:product/name :product/orderCode :product/price})
     (validators-util/validate-unique-fields attributes product-handler db-value #{:product/name :product/orderCode})))
  (validate-update-entity [_ attributes db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes product-handler db-value)
     (validators-util/validate-update-fields attributes #{:product/name :product/orderCode :product/price})
     (validators-util/validate-unique-fields attributes product-handler db-value #{:product/name :product/orderCode})))
  (validate-delete-entity [_ id db-value]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to db-value]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria product-handler db-value)))
  PEntityHistoryServiceValidator
  (validate-find-entity-history [_ entity-id history-id db-value]
    (util/pipeline-statements
     (validators-util/validate-entity-present entity-id product-handler db-value)))
  (validate-list-entity-history [_ id criteria sort-attrs from to db-value]
    nil))
