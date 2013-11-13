;; Midje tests for people model.
(ns rest-api.test.model.people
  (:use midje.sweet
        rest-api.model.people
        hyperion.api)
  (:require [hyperion.log :as log]))

(fact "Test converting json representation of person to a map"
      (json-to-person "{\"person\": {\"personid\": 123 \"name\": \"Fred\"}}") => {:personid 123 :name "Fred"})

(fact "Test converting person (map) to a json string."
      (person-to-json {:personid 123 :name "Fred"}) => "{\"person\":{\"name\":\"Fred\",\"personid\":123}}")

;; Set up memory ds
(def mem-ds (new-datastore :implementation :memory))
(def mysql-ds (new-datastore :implementation :mysql :connection-url "jdbc:mysql://localhost:3306/restapi?user=restapi&password=restapi" :username "restapi" :password "restapi" :database "restapi"))

(binding [*ds* mem-ds]

  ;; Tests for fetching people
  (do
      (log/debug!)
      (save {:kind :person} {:personid 111 :name "Test"})
      (fact "Test to fetch a person using their personid."
            (let [found (find-person 111)]
              (:personid found) => 111
              (:name  found) => "Test"))
      (fact "Test to fetch a person using their name."
             (let [found (find-person-by-name "Test")]
               (:name (first found)) => "Test"
               (:personid (first found)) => 111))


      ;; Tests for creating people
      (fact "Test creating a person using personid as key"
            (create-person {:personid 999 :name "Bill"})  => (contains {:personid 999 :name "Bill" :kind (name :person)}))


      (fact "Test to create a person."
            (let [created (create-person {:personid 333 :name "number 333"})]
              (:personid created) => 333
              (:name created) => "number 333"
              (let [fetched (find-by-key (:key created))]
                fetched => created))))
  )
