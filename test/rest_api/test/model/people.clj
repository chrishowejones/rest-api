;; Midje tests for people model.
(ns rest-api.test.model.people
  (:use midje.sweet
        rest-api.model.people)
  )

;; Tests for creating people
(fact "Test creating a person using id as key"
      (create-person {:id 999 :name "Bill"}) => {:id 999 :name "Bill" })

(fact "Test converting json representation of person to a map"
      (json-to-person "{\"person\": {\"id\": 123 \"name\": \"Fred\"}}") => {:id 123 :name "Fred"})

(fact "Test converting person (map) to a json string."
      (person-to-json {:id 123 :name "Fred"}) => "{\"person\":{\"name\":\"Fred\",\"id\":123}}")
