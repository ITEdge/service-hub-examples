(ns itedge.service-hub-examples.in-memory-simple.handlers
  (:use itedge.service-hub.core.handlers)
  (:require [itedge.service-hub.core.handlers-memory :as handlers-memory]))

(def product-handler (handlers-memory/create-memory-handler [{:product_name "product-1" :price 10 :order_number "P1"}
                                                             {:product_name "product-2" :price 20 :order_number "P2"}
							     {:product_name "product-3" :price 40 :order_number "P3"}] :id))
