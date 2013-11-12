;; This handler is mapped to /people and will route any subsequent URI to the
;; appropriate resources.

(ns rest-api.people.handler
  (:use compojure.core
        rest-api.model.people)
  (:require [liberator.core :refer [resource defresource]]))

(defresource create-person-res [person]
  :allowed-methods [:post]
  :available-media-types ["application/json"]
  :post! (let [person (create-person person)] {:created person})
  :handle-created :created)


;; The following defroutes map resources that are nested under /people


(defroutes people-routes
  "Maps resources that are nested under /people. This is currently:
<ul><li>GET method on /people/person/:id
    <li>ANY method on /people/</ul>"
  (ANY "/" [] "/people/person/:id will return a person.") ; map to /people/
  (GET "/person/:id" [id] (resource
                           :available-media-types ["text/html" "application/json"]
                           :handle-ok  (find-person id))) ; map to /people/person/:id
  (POST "/person" [person] (create-person-res person))
  )
