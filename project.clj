(defproject rest-api "0.1.0-SNAPSHOT"
  :description "This is an example REST api built using compojure and liberator"
  :url "https://github.com/chrishowejones/rest-api"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [liberator "0.9.0"]
                 [compojure "1.1.6"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler rest-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})