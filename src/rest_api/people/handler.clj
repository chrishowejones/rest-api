;; This handler is mapped to /people and will route any subsequent URI to the
;; appropriate resources.

(ns rest-api.people.handler
  (:use compojure.core
        rest-api.model.people)
  (:require [liberator.core :refer [resource defresource]]))

(defn wrap-person [pm]
  "Wrap the person details returned from the model in an outer map keyed by :person"
  (assoc {} :person pm))

(defresource create-person-resource [person]
  :allowed-methods [:post]
  :available-media-types ["text/html" "application/json"]
  :post! (let [person (wrap-person (create-person person))] {:created person})
  :handle-created :created)

(defresource person-resource [id]
  :allowed-methods [:get]
  :available-media-types ["text/html" "application/json"]
  :handle-ok (find-person id)) ; map to /people/person/:id




;; The following defroutes map resources that are nested under /people


(defroutes people-routes
  "Maps resources that are nested under /people. This is currently:
<ul><li>GET method on /people/person/:id
    <li>ANY method on /people/</ul>"
  (ANY "/" [] "/people/person/:id will return a person.") ; map to /people/
  (GET "/person/:id" [id] (person-resource id
                           )) ; map to /people/person/:id
  (POST "/person" [person] (create-person-resource person)))
