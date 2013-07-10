(ns itedge.service-hub-examples.in-memory.services
  (:use itedge.service-hub.core.handlers
        itedge.service-hub.core.validators
        itedge.service-hub.core.authorizators
        itedge.service-hub.core.services)
  (:require [itedge.service-hub-examples.in-memory.handlers :as handlers]
            [itedge.service-hub-examples.in-memory.validators :as validators]
            [itedge.service-hub-examples.in-memory.authorizators :as authorizators]
            [itedge.service-hub.core.services-util :as services-util]))

(def user-authorizator (authorizators/->UserAuthorizator))
(def role-authorizator (authorizators/->RoleAuthorizator))
(def product-authorizator (authorizators/->ProductAuthorizator))

(def user-validator (validators/->UserValidator handlers/user-handler handlers/role-handler))
(def role-validator (validators/->RoleValidator handlers/role-handler handlers/user-handler))
(def product-validator (validators/->ProductValidator handlers/product-handler))

(deftype UserService []
  PEntityService
  (find-entity [_ id auth]
    (services-util/authorize-service-call
      (authorize-find-call user-authorizator id auth)
	    (services-util/get-service-result
        (validate-find-entity user-validator id)
		    (services-util/get-success-response
          (handle-find-entity handlers/user-handler id)))))
  (delete-entity [_ id auth]
    (services-util/authorize-service-call
      (authorize-delete-call user-authorizator id auth)
	    (services-util/get-service-result
        (validate-delete-entity user-validator id)
	      (services-util/get-success-delete-response 
	        (handle-delete-entity handlers/user-handler id)))))
  (update-entity [_ attributes auth]
    (services-util/authorize-service-call
      (authorize-update-call user-authorizator attributes auth)
	    (services-util/get-service-result
        (validate-update-entity user-validator attributes)
	      (services-util/get-success-response 
	        (handle-update-entity handlers/user-handler attributes)))))
  (add-entity [_ attributes auth]
    (services-util/authorize-service-call
      (authorize-add-call user-authorizator attributes auth)
	    (services-util/get-service-result
        (validate-add-entity user-validator attributes)
	      (services-util/get-success-response 
	        (handle-add-entity handlers/user-handler attributes)))))
  (list-entities [_ criteria sort-attrs from to auth]
    (services-util/authorize-service-call
      (authorize-list-call user-authorizator criteria auth)
	    (services-util/get-service-result
        (validate-list-entities user-validator criteria from to)
        (let [criteria (restrict-list-call user-authorizator criteria auth)]
			    (-> (services-util/get-success-response 
	              (handle-list-entities handlers/user-handler criteria sort-attrs from to))
		        (services-util/assoc-range-info from to (handle-count-entities handlers/user-handler criteria))))))))

(deftype RoleService []
  PEntityService
  (find-entity [_ id auth]
    (services-util/authorize-service-call
      (authorize-find-call role-authorizator id auth)
	    (services-util/get-service-result
        (validate-find-entity role-validator id)
		    (services-util/get-success-response
          (handle-find-entity handlers/role-handler id)))))
  (delete-entity [this id auth]
    (services-util/authorize-service-call
      (authorize-delete-call role-authorizator id auth)
	    (services-util/get-service-result
        (validate-delete-entity role-validator id)
		    (services-util/get-success-delete-response 
	        (handle-delete-entity handlers/role-handler id)))))
  (update-entity [this attributes auth]
    (services-util/authorize-service-call
      (authorize-update-call role-authorizator attributes auth)
	    (services-util/get-service-result
        (validate-update-entity role-validator attributes)
		    (services-util/get-success-response 
	        (handle-update-entity handlers/role-handler attributes)))))
  (add-entity [this attributes auth]
    (services-util/authorize-service-call
      (authorize-add-call role-authorizator attributes auth)
	    (services-util/get-service-result
        (validate-add-entity role-validator attributes)
		    (services-util/get-success-response 
	        (handle-add-entity handlers/role-handler attributes)))))
  (list-entities [_ criteria sort-attrs from to auth]
    (services-util/authorize-service-call
      (authorize-list-call role-authorizator criteria auth)
	    (services-util/get-service-result
        (validate-list-entities role-validator criteria from to)
        (let [criteria (restrict-list-call role-authorizator criteria auth)]
		      (-> (services-util/get-success-response
			          (handle-list-entities handlers/role-handler criteria sort-attrs from to))
		        (services-util/assoc-range-info from to (handle-count-entities handlers/role-handler criteria))))))))

(deftype ProductService []
  PEntityService
  (find-entity [_ id auth]
    (services-util/authorize-service-call
      (authorize-find-call product-authorizator id auth)
	    (services-util/get-service-result
        (validate-find-entity product-validator id)
	      (services-util/get-success-response
		      (handle-find-entity handlers/product-handler id)))))
  (delete-entity [this id auth]
    (services-util/authorize-service-call
      (authorize-delete-call product-authorizator id auth)
	    (services-util/get-service-result
        (validate-delete-entity product-validator id)
	      (services-util/get-success-delete-response 
	        (handle-delete-entity handlers/product-handler id)))))
  (update-entity [this attributes auth]
    (services-util/authorize-service-call
      (authorize-update-call product-authorizator attributes auth)
	    (services-util/get-service-result
        (validate-update-entity product-validator attributes)
	      (services-util/get-success-response 
	        (handle-update-entity handlers/product-handler attributes)))))
  (add-entity [this attributes auth]
    (services-util/authorize-service-call
      (authorize-add-call product-authorizator attributes auth)
	    (services-util/get-service-result
        (validate-add-entity product-validator attributes)
		      (services-util/get-success-response
		        (handle-add-entity handlers/product-handler attributes)))))
  (list-entities [_ criteria sort-attrs from to auth]
    (services-util/authorize-service-call
      (authorize-list-call product-authorizator criteria auth)
	    (services-util/get-service-result
        (validate-list-entities product-validator criteria from to)
        (let [criteria (restrict-list-call product-authorizator criteria auth)]
			    (-> (services-util/get-success-response 
	              (handle-list-entities handlers/product-handler criteria sort-attrs from to))
		        (services-util/assoc-range-info from to (handle-count-entities handlers/product-handler criteria))))))))


