(defproject rest-api "0.1.0-SNAPSHOT"
  :description "This is an example REST api built using compojure and liberator"
  :url "https://github.com/chrishowejones/rest-api"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [org.clojure/data.json "0.2.3"]
                 [ring/ring-json "0.2.0"]
                 [hyperion/hyperion-mysql "3.7.1"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [liberator "0.9.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler rest-api.handler/app :auto-reload true}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [ring-server "0.3.1"]
                        [midje "1.5.0"]]}})
