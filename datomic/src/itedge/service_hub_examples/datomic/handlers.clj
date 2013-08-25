(ns itedge.service-hub-examples.datomic.handlers
  (:require [itedge.service-hub.core.handlers :refer :all]
            [itedge.service-hub.persistence-datomic.util :as datomic-util]))

(def user-handler (datomic-util/create-handler nil #{:username :password :roles}))
