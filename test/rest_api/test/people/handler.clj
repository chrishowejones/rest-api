;; Midje tests for the people handler
(ns rest-api.test.people.handler
  (:use rest-api.people.handler
        compojure.core
        compojure.route
        compojure.handler
        midje.sweet)
  (:require [ring.middleware.json :as middleware]
            [compojure.handler :as handler]
            [ring.mock.request :as mock]))

;; create json middleware wrapped people-routes
(def test-people-routes
  (-> (handler/site people-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (middleware/wrap-json-params)))


(facts
 (with-redefs [rest-api.model.people/find-person (fn [id] "This is mapped to /people/person for id 1 for a GET HTTP request")] ;; Mock find-person using with-redefs
   (fact "Test GET method for /person/:id returns the required person details."
         (let [response (people-routes (mock/request :get "/person/1"))]
           (:status response) => 200
           (:body response) => "This is mapped to /people/person for id 1 for a GET HTTP request")))
 (with-redefs [rest-api.model.people/create-person (fn [m] m)]
   (fact "Test POST method for /person/:id creates the required person details."
         (let [response
               (test-people-routes
                (mock/header
                 (mock/content-type
                   (mock/request :post "/person" "{ \"person\" : { \"id\" : 999, \"name\" : \"Fred Bloggs\", \"age\" : 33}}")
                 "application/json")
                "accept" "application/json"))]
           (:status response) => 201
           (get (re-find #"id\":([0-9]*)"
                         (:body response)) 1) => "999"
                         (get (re-find #"name\":(\".*\"),"
                                       (:body response)) 1) => "\"Fred Bloggs\""
                                       (get (re-find #"age\":([0-9]*)}"
                                                     (:body response)) 1) => "33" )))

 )
