# buddy-liberator

Sample repository to show one way to integrate [Buddy][https://github.com/funcool/buddy] and [Liberator][clojure-liberator.github.io/liberator/]

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server-headless

## Testing 
```
$ curl -X POST  -H "Content-type: application/json" -d '{"username": "friend", "password": "clojure"}' http://localhost:3000/login -i


$ curl -i 'http://localhost:3000/test-url' -H "Accept: application/json" -H "authorization:Token TokenObtainedWithThePreviousCommand"
```
