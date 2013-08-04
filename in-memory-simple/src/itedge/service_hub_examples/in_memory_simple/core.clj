(ns itedge.service-hub-examples.in-memory-simple.core
  (:use itedge.service-hub.core.handlers
        itedge.service-hub.core.services
        compojure.core
        itedge.service-hub.http-ring.middleware.translate-params
        [ring.middleware params
                         keyword-params
                         nested-params
                         file-info
                         resource])
  (:require [clojure.string :as string]
            [ring.adapter.jetty :as jetty]
            [itedge.service-hub-examples.in-memory-simple.handlers :as handlers]
            [itedge.service-hub-examples.in-memory-simple.services :as services]
            [compojure.handler :as handler]
            [itedge.service-hub.core.util :as util]
            [itedge.service-hub.http-ring.routes-util :as routes-util]
            [itedge.service-hub.http-ring.content-util :as content-util]))

(def product-service (services/->ProductService))

(defroutes app-routes
  (routes-util/scaffold-crud-routes "/products" product-service :id read-string content-util/craft-edn-response))

(def app
  (-> app-routes
    (wrap-translate-params)
    (wrap-keyword-params)
    (wrap-nested-params)
    (wrap-params)
    (wrap-resource "public")
    (wrap-file-info)))

(defonce server (jetty/run-jetty app {:port 3000 :join? false}))
