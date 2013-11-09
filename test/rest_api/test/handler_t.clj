(ns rest_api.test.handler_t
  (:use midje.sweet
        ring.mock.request
        rest-api.handler))

(facts
  (fact "test main-routes for /"
        (let [response (main-routes (request :get "/"))]
          (:status response) => 200
          (:body response) => "Use /test or /people to retrieve resources"))
  (fact "test main-routes for /test"
        (let [response (main-routes (request :get "/test"))]
          (:status response) => 200
          (:body response) => "/secret?word=guess to guess the secret word or /choice?choice=?? where ?? is a number to choose a result.")))
