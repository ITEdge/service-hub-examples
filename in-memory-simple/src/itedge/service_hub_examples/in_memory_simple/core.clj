(ns itedge.service-hub-examples.in-memory-simple.core
  (:require [clojure.tools.reader.edn :as edn]
            [ring.adapter.jetty :as jetty]           
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [itedge.service-hub.core.handlers-memory :as handlers-memory]
            [itedge.service-hub.core.services-util :as services-util]
            [itedge.service-hub.core.validators :refer :all]
            [itedge.service-hub.core.util :as util]
            [itedge.service-hub.core.validators-util :as validators-util]
            [itedge.service-hub.http-ring.routes-util :as routes-util]
            [itedge.service-hub.http-ring.content-util :as content-util]
            [itedge.service-hub.http-ring.middleware.translate-params :refer :all]
            [ring.middleware.params :refer :all]
            [ring.middleware.keyword-params :refer :all]
            [ring.middleware.nested-params :refer :all]
            [ring.middleware.file-info :refer :all]
            [ring.middleware.resource :refer :all]))

(def product-handler (handlers-memory/create-handler [{:product_name "product-1" :price 10 :order_number "P1"}
                                                      {:product_name "product-2" :price 20 :order_number "P2"}
                                                      {:product_name "product-3" :price 40 :order_number "P3"}] :id))

(deftype ProductValidator [product-handler]
  PEntityServiceValidator
  (validate-find-entity [_ id datasource]
    (util/pipeline-statements
     (validators-util/validate-entity-present id product-handler datasource)))
  (validate-add-entity [_ attributes datasource]
    (util/pipeline-statements
     (validators-util/validate-insert-fields attributes #{:product_name :price :order_number})
     (validators-util/validate-unique-fields attributes product-handler datasource #{:product_name :order_number})))
  (validate-update-entity [_ attributes datasource]
    (util/pipeline-statements
     (validators-util/validate-entity-still-there attributes product-handler datasource)
     (validators-util/validate-update-fields attributes #{:product_name :price :order_number})
     (validators-util/validate-unique-fields attributes product-handler datasource #{:product_name :order_number})))
  (validate-delete-entity [_ id datasource]
    nil)
  (validate-list-entities [_ criteria sort-attrs from to datasource]
    (util/pipeline-statements
     (validators-util/validate-list-range from to criteria product-handler datasource))))

(def product-validator (->ProductValidator product-handler))

(def product-service (services-util/scaffold-service product-handler product-validator (fn [] nil)))

(defroutes app-routes
  (routes-util/scaffold-crud-routes "/products" product-service :id edn/read-string content-util/craft-edn-response true))

(def app
  (-> app-routes
      (wrap-translate-params)
      (wrap-keyword-params)
      (wrap-nested-params)
      (wrap-params)
      (wrap-resource "public")
      (wrap-file-info)))

(defonce server (jetty/run-jetty app {:port 3000 :join? false}))
