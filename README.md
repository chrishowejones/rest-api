## rest-api


Experiment in creating a REST API using Compojure and Liberator.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

Once running you can use curl or a REST client to send requests. For example:

    curl -X POST -H "Content-Type: application/json"  -d '{"person: { "id" : 999 }}' http://localhost:3000/people/person
    curl -X GET -H "Content-Type: application/json"  http://localhost:3000/people/person/1

## License
None at moment
