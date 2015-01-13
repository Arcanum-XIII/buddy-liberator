(defproject buddy-liberator "0.1.0-SNAPSHOT"
  :description "Buddy/Liberator integration"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [liberator "0.12.2"]
                 [buddy "0.3.0-SNAPSHOT"]
                 [cheshire "5.4.0"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler buddy-liberator.core.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
