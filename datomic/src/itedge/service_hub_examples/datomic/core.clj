(ns itedge.service-hub-examples.datomic.core
  (:require [clojure.string :as string]
            [clojure.tools.reader.edn :as edn]
            [ring.adapter.jetty :as jetty]
            [itedge.service-hub.core.handlers :refer :all]
            [itedge.service-hub-examples.datomic.handlers :as handlers]
            [itedge.service-hub-examples.datomic.db :as db]
            [itedge.service-hub-examples.datomic.services :as services]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [itedge.service-hub.core.security :as security]
            [itedge.service-hub.core.util :as util]
            [itedge.service-hub.http-ring.routes-util :as routes-util]
            [itedge.service-hub.http-ring.content-util :as content-util]
            [itedge.service-hub.http-ring.middleware.translate-params :refer :all]
            [itedge.service-hub.http-ring.middleware.create-authentication :refer :all]
            [ring.middleware.params :refer :all]
            [ring.middleware.keyword-params :refer :all]
            [ring.middleware.nested-params :refer :all]
            [ring.middleware.content-type :refer :all]
            [ring.middleware.not-modified :refer :all]
            [ring.middleware.resource :refer :all]))

(defn user-func
  "Uses user service to retrieve user login information and transforms assigned roles into keyword set.
   If provided username is not in database, returns nil."
  [username]
  (let [db-value (db/get-db-value)]
    (-> (first (handle-list-entities handlers/user-handler {:user/username username} nil nil nil db-value))
        ((fn [result]
           (when result
             (update-in result [:user/roles] (fn [roles]
                                               (into #{} (map (fn [role-id]
                                                                (keyword (:role/rolename (handle-find-entity handlers/role-handler role-id db-value)))) roles)))))))
        ((fn [result]
           (when result
             {:id (:db/id result) :username (:user/username result)
              :password (:user/password result) :roles (:user/roles result)}))))))

(defroutes app-routes
  (GET "/login" [:as request]
       (routes-util/check-auth request content-util/craft-edn-response))
  (routes-util/scaffold-crud-routes "/users" services/user-service :db/id edn/read-string content-util/craft-edn-response true)
  (routes-util/scaffold-crud-routes "/roles" services/role-service :db/id edn/read-string content-util/craft-edn-response true)
  (routes-util/scaffold-crud-history-routes "/products" services/product-service :db/id edn/read-string content-util/craft-edn-response true))

(def credentials-fn (partial security/bcrypt-credential-fn user-func))

(def app
  (-> app-routes
      (wrap-create-authentication credentials-fn)
      (wrap-translate-params)
      (wrap-keyword-params)
      (wrap-nested-params)
      (wrap-params)
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))

(defonce database (db/setup-db))
(defonce server (jetty/run-jetty app {:port 3000 :join? false}))
