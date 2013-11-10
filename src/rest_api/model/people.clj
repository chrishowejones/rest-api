;; Responsible for generating the data model for people
(ns rest-api.model.people
  (:use [clojure.data.json :only [read-str write-str]]))

(defn create-person [m]
  "Takes a map of name-value pairs and creates a new person."
  (if-let [id (:id m)]
    m)); Need to refactor to store person in database

(defn json-to-person [s]
  "Convert json string to person"
  (:person (read-str s :key-fn keyword)))

(defn person-to-json [p]
  "Convert person to json string."
  (let [pers (assoc-in {} [:person] p)] (write-str pers :key-fn name)))
