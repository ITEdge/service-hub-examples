(defproject itedge/service-hub-examples.datomic "1.3.2"
  :description "Example project with datomic handlers"
  :min-lein-version "2.0.0"
  :url "https://github.com/ITEdge/service-hub-examples"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.reader "0.7.6"]
                 [itedge/service-hub.core "1.3.2"]
                 [itedge/service-hub.http-ring "1.3.2"]
                 [itedge/service-hub.persistence-datomic "1.3.2"]])
