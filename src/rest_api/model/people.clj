;; Responsible for generating the data model for people
(ns rest-api.model.people
  (:use [clojure.data.json :only [read-str write-str]]
        hyperion.api))

;; Set up database
;;

(binding [*ds* (new-datastore :implementation :mysql :connection-url "jdbc:mysql://localhost:3306/restapi?user=restapi&password=restapi" :database "restapi")]

  ;; Persistence for person

  (defn create-person [m]
    "Takes a map of name-value pairs and creates a new person."
    (if-let [id (:id m)]
      (save {:kind :person} m)))

  ;; Finders for persom

  (defn find-person [id]
    "Find a person using the provided unique id. Returns a matching person."
    (let [ident (if (string? id) (Integer/valueOf id) id)]
      (first (find-by-kind :person :filters [:= :id ident])))
    )

  (defn find-person-by-name [name]
    "Find a person using the provided name. Returns a seq of matching persons."
    (find-by-kind :person :filters [:= :name name]))

  ;; Utilities for JSON
  (defn json-to-person [s]
    "Convert json string to person"
    (:person (read-str s :key-fn keyword)))

  (defn person-to-json [p]
    "Convert person to json string."
    (let [pers (assoc-in {} [:person] p)] (write-str pers :key-fn name)))
)
