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

HTTP/1.1 201 Created
Date: Tue, 13 Jan 2015 12:13:51 GMT
Vary: Accept
Content-Type: application/json;charset=UTF-8
Content-Length: 222
Server: Jetty(7.6.8.v20121106)

{"token":"VGxCWkNBQUFBRVh3Tm1sRGV5SjFjMlZ5Ym1GdFpTSTZJbVp5YVdWdVpDSXNJbkp2YkdWeklqcGJJbUoxWkdSNUxXeHBZbVZ5WVhSdmNpNWpiM0psTG1oaGJtUnNaWEl2ZFhObGNpSmRmUQ:lOnrn1rGpbMRDai5dK9BoJZH0N-2TT33McdU8rgrE_w:zV_S4ADRuHw:AAABSuM23nI"}

$ curl -i 'http://localhost:3000/test-url' -H "Accept: application/json" -H "authorization:Token VGxCWkNBQUFBRVh3Tm1sRGV5SjFjMlZ5Ym1GdFpTSTZJbVp5YVdWdVpDSXNJbkp2YkdWeklqcGJJbUoxWkdSNUxXeHBZbVZ5WVhSdmNpNWpiM0psTG1oaGJtUnNaWEl2ZFhObGNpSmRmUQ:lOnrn1rGpbMRDai5dK9BoJZH0N-2TT33McdU8rgrE_w:zV_S4ADRuHw:AAABSuM23nI"

HTTP/1.1 200 OK
Date: Tue, 13 Jan 2015 12:37:15 GMT
Vary: Accept
Content-Type: application/json;charset=UTF-8
Content-Length: 4
Server: Jetty(7.6.8.v20121106)

"OK"
```
