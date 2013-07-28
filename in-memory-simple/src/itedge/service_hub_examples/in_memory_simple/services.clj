(ns itedge.service-hub-examples.in-memory-simple.services
  (:use itedge.service-hub.core.handlers
        itedge.service-hub.core.validators
        itedge.service-hub.core.services)
  (:require [itedge.service-hub-examples.in-memory-simple.handlers :as handlers]
            [itedge.service-hub-examples.in-memory-simple.validators :as validators]
            [itedge.service-hub.core.services-util :as services-util]))

(def product-validator (validators/->ProductValidator handlers/product-handler))

(deftype ProductService []
  PEntityService
  (find-entity [_ id _]
        (validate-find-entity product-validator id)
	      (services-util/get-success-response
		      (handle-find-entity handlers/product-handler id)))
  (delete-entity [this id _]
        (validate-delete-entity product-validator id)
	      (services-util/get-success-delete-response 
	        (handle-delete-entity handlers/product-handler id)))
  (update-entity [this attributes _]
        (validate-update-entity product-validator attributes)
	      (services-util/get-success-response 
	        (handle-update-entity handlers/product-handler attributes)))
  (add-entity [this attributes _]
        (validate-add-entity product-validator attributes)
		      (services-util/get-success-response
		        (handle-add-entity handlers/product-handler attributes)))
  (list-entities [_ criteria sort-attrs from to _]
        (validate-list-entities product-validator criteria from to)
        (let [criteria ;restrict-list-call product-authorizator 
                        criteria ;auth
                        ]
			    (-> (services-util/get-success-response 
	              (handle-list-entities handlers/product-handler criteria sort-attrs from to))
		        (services-util/assoc-range-info from to (handle-count-entities handlers/product-handler criteria))))))


