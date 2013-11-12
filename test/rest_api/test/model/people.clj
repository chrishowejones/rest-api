;; Midje tests for people model.
(ns rest-api.test.model.people
  (:use midje.sweet
        rest-api.model.people
        hyperion.api))

(fact "Test converting json representation of person to a map"
      (json-to-person "{\"person\": {\"id\": 123 \"name\": \"Fred\"}}") => {:id 123 :name "Fred"})

(fact "Test converting person (map) to a json string."
      (person-to-json {:id 123 :name "Fred"}) => "{\"person\":{\"name\":\"Fred\",\"id\":123}}")

;; Set up memory ds
(def mem-ds (new-datastore :implementation :memory))

(binding [*ds* mem-ds]

  ;; Tests for fetching people
  (do (save {:kind :person :id 111 :name "Test"})
      (fact "Test to fetch a person using their id."
            (let [found (find-person 111)]
              (:id  found) => 111
              (:name  found) => "Test"))
      (fact "Test to fetch a person using their name."
            (let [found (find-person-by-name "Test")]
              (count found) => 1
              (:name (first found)) => "Test"
              (:id (first found)) => 111)))

  ;; add additional tests here
;; Tests for creating people
(fact "Test creating a person using id as key"
      (dissoc (create-person {:id 999 :name "Bill"}) :key) => {:id 999 :name "Bill" :kind (name :person)})


(fact "Test to create a person."
      (let [created (create-person {:id 333 :name "number 333"})]
        (:id created) => 333
        (:name created) => "number 333"
        (let [fetched (find-by-key (:key created))]
          fetched => created)))
  )
