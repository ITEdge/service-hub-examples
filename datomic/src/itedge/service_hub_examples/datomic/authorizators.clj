(ns itedge.service-hub-examples.datomic.authorizators
  (:require [itedge.service-hub.core.authorizators :refer :all]
            [itedge.service-hub.core.util :as util]
            [itedge.service-hub.core.authorizators-util :as auth-util]))

(deftype UserAuthorizator []
  PEntityServiceAuthorizator
  (authorize-find-call [_ id auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user :admin})))
  (authorize-delete-call [_ id auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:admin})))
  (authorize-update-call [_ attributes auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:admin})))
  (authorize-add-call [_ attributes auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:admin})))
  (authorize-list-call [_ criteria auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user :admin})))
  (restrict-list-call [_ criteria auth]
    criteria))

(deftype RoleAuthorizator []
  PEntityServiceAuthorizator
  (authorize-find-call [_ id auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user :admin})))
  (authorize-delete-call [_ id auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:admin})))
  (authorize-update-call [_ attributes auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:admin})))
  (authorize-add-call [_ attributes auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:admin})))
  (authorize-list-call [_ criteria auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user :admin})))
  (restrict-list-call [_ criteria auth]
    criteria))

(deftype ProductAuthorizator []
  PEntityServiceAuthorizator
  (authorize-find-call [_ id auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user})))
  (authorize-delete-call [_ id auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user})))
  (authorize-update-call [_ attributes auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user})))
  (authorize-add-call [_ attributes auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user})))
  (authorize-list-call [_ criteria auth]
    (util/pipeline-statements
     (auth-util/authenticated? auth)
     (auth-util/authorized? auth #{:user})))
  (restrict-list-call [_ criteria auth]
    criteria))
