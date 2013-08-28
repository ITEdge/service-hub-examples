(ns itedge.service-hub-examples.in-memory.handlers
  (:require [itedge.service-hub.core.handlers :refer :all]
            [itedge.service-hub.core.handlers-memory :as handlers-memory]))

(def role-handler (handlers-memory/create-memory-handler [{:rolename :admin :users #{1}}
                                                          {:rolename :user :users #{1}}] :id))

(def user-handler (handlers-memory/create-memory-handler [{:username "admin" 
                                                           :password "$2a$10$K83DHEqRZ7ZCpq0HK49lj.4XW/EvMw4XPQPSrIxWNVPsOhwFk8bHe"
                                                           :roles #{1 2}}] :id))

(def product-handler (handlers-memory/create-memory-handler [{:product_name "product-1" :price 10 :order_number "P1"}
                                                             {:product_name "product-2" :price 20 :order_number "P2"}] :id))
