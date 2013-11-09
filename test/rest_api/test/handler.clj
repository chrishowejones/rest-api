(ns rest-api.test.handler
  (:use clojure.test
        ring.mock.request
        rest-api.handler
        midje.sweet))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Use /test or /people to retrieve resources"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest test-test-routes
  ; test the root api
  (testing "test routes"
    (let [response (app (request :get "/test"))]
      (is (= (:status response) 200))
      (is (re-find #"/secret\?word=guess.*/choice" (:body response)))))
   ; test the guess the secret api
  (testing "test the secret word api"
    (let [response (app (request :get "/test/secret?word=tiger"))]
      (is (= (:status response) 200))
      (is (= (:body response) "You found the secret word! It was tiger")))))

(facts
  (fact "test main-routes for /"
        (let [response (main-routes (request :get "/"))]
          (:status response) => 200
          (:body response) => "Use /test or /people to retrieve resources"))
  (fact "test main-routes for /test"
        (let [response (main-routes (request :get "/test"))]
          (:status response) => 200
          (:body response) => "/secret?word=guess to guess the secret word or /choice?choice=?? where ?? is a number to choose a result.")))
