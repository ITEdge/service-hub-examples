(ns itedge.service-hub-examples.in-memory-simple.validators
  (:use itedge.service-hub.core.validators)
  (:require [itedge.service-hub.core.util :as util]
            [itedge.service-hub.core.validators-util :as validators-util]))

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
  (validate-list-entities [_ criteria from to]
    (util/pipeline-statements
      (validators-util/validate-list-range from to criteria product-handler))))
