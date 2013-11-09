(ns rest-api.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [liberator.core :refer [resource defresource]]
            [rest-api.people.handler :refer [people-routes]]))

; This handler is the main entry point to the web application


(defroutes test-routes
  (ANY "/" [] "/secret?word=guess to guess the secret word or /choice?choice=?? where ?? is a number to choose a result.")
  (ANY "/secret" [] "This is a secret"
       (resource :available-media-types ["text/html"]
                 :exists? (fn [ctx]
                            (let [word (get-in ctx [:request :params :word])] (if (= "tiger" word) {:word word})))
                 :handle-ok (fn [ctx] (format "You found the secret word! It was %s" (get ctx :word)))
                 :handle-not-found (fn [ctx] (format "<html> Uh, %s that's the wrong word. Guess again!" (get-in ctx [:request :params :word])))))
  (ANY "/choice" []
       (resource :available-media-types ["text/html"]
                 :exists? (fn [ctx]
                            (if-let [choice
                                     (get {"1" "stone" "2" "paper" "3" "scissors"}
                                          (get-in ctx [:request :params :choice]))]
                              {:choice choice}))
                 :handle-ok (fn [ctx]
                              (format "<html>Your choice: &quot;%s&quot;."
                                      (get ctx :choice)))
                 :handle-not-found (fn [ctx]
                                     (format "<html>There is no value for the option &quot;%s&quot;"
                                             (get ctx :choice ""))))))

; Maps the routes for the application
(defroutes main-routes
  (ANY "/" [] "Use /test or /people to retrieve resources") ; maps root
  (context "/test" [] test-routes) ; maps a /test URI
  (context "/people" [] people-routes) ; maps the /people URI
  (route/not-found "Not Found") ; catch all for any other URI
  )

; App is the entry point to the application and creates a compojure site handler
; that calls through to the function that routes the URIs

(def app
  (handler/site main-routes))
