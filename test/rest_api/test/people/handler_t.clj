;; Midje tests for the people handler
(ns rest-api.test.people.handler
  (:use rest-api.people.handler
        ring.mock.request
        midje.sweet))

(facts
 (fact "Test GET method for /person/:id returns the required person details."
       (let [response (people-routes (request :get "/person/1"))]
         (:status response) => 200
         (:body response) => "This is mapped to /people/person for id 1 for a GET HTTP request"))
  (fact "Test POST method for /person/:id creates the required person details."
       (let [response (people-routes (request :post "/person/999" {:body {:name "Fred Bloggs" :age 33}}))]
         (:status response) => 201
         (get (re-find #":name (\".*\")"
                        (:body response)) 1) => "\"Fred Bloggs\"" ))
)
