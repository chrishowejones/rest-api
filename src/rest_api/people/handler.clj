;; This handler is mapped to /people and will route any subsequent URI to the
;; appropriate resources.

(ns rest-api.people.handler
  (:use compojure.core)
  (:require [liberator.core :refer [resource defresource]]))

;; The following defroutes map resources that are nested under /people

(defroutes people-routes
  "Maps resources that are nested under /people. This is currently:
<ul><li>GET method on /people/person/:id
    <li>ANY method on /people/</ul>"
  (ANY "/" [] "/people/person/:id will return a person.") ; map to /people/
  (GET "/person/:id" [id] (resource
                           :available-media-types ["text/html" "application/json"]
                           :handle-ok (format "This is mapped to /people/person for id %s for a GET HTTP request" id))) ; map to /people/person/:id
  (POST "/person/:id" [id] (resource
                            :available-media-types ["text/html" "application/json"]
                            :allowed-methods [:post]
                            :post! (fn [_] [true {:person (str {:id id :name "Fred Bloggs" :age 33})}])
                            :handle-created :person))
  )
