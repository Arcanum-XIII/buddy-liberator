(ns buddy-liberator.core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response]]
            [buddy.auth.backends.token :refer [signed-token-backend]]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.sign.generic :as jws]
            [buddy.hashers.bcrypt :as hs]
            [cheshire.core :refer :all]
            [liberator.core :refer [resource defresource]])
  (:import [java.security SecureRandom]))

(def users {"friend" {:username "friend"
                      :password (hs/make-password "clojure")
                      :roles #{::user}}})

(defn random-bytes
  [length]
  (let [gen (new SecureRandom) key (byte-array length)]
    (.nextBytes gen key)
    key))

(defn generate-key
  ([] (generate-key 128))
  ([size] (random-bytes size)))

(defonce secret-key (generate-key))

;; Header needed to auth : curl -i 'http://localhost:3000/' -H "Accept: application/json" -H "authorization:Token eyJ0eXAiOiJKV1MiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6ImNsb2p1cmUiLCJ1c2VybmFtZSI6ImZyaWVuZCJ9.0lP7PcWmlbghsNfTlis1iQ4NwlqLklEtmWfMSYP2A6E"
(def signed-backend (signed-token-backend {:privkey secret-key :max-age (* 60 15)}))

(defn test-auth
  [request]
  (if (authenticated? request)
    (response (format "Hello %s" (:identity request)))
    (response "Hello Anonymous")))

(defn body-as-string [ctx]
  (if-let [body (get-in ctx [:request :body])]
    (condp instance? body
      java.lang.String body
      (slurp body))))

(defn create-token [username pass]
  (if-let [data (get users username)]
    (when (hs/check-password pass (:password data))
      {:token (jws/dumps (generate-string {:username username :roles (:roles data)}) secret-key)})
    {:token nil}))

(defresource get-token []
  :available-media-types ["application/json"]
  :allowed-methods [:post]
  :post! (fn [ctx] (let [data (parse-string (body-as-string ctx))] (create-token (get data "username") (get data "password"))))
  :handle-created (fn [ctx] (generate-string {:token (:token ctx)})))

(defresource test-resources []
  :available-media-types ["application/json"]
  :allowed-methods [:get]
  :authorized? (fn [ctx] (:identity (:request ctx)))
  :handle-ok (generate-string "OK"))

(defroutes simple-routes
  (GET "/" [] test-auth)
  (ANY "/test-url" [] (test-resources))
  (ANY "/login" [] (get-token))
  (route/not-found "Not Found"))

(defroutes anon-routes*
  (ANY "/login" [] (get-token)))

(def anon-routes
     (-> #'anon-routes*
         wrap-params))

(defroutes loggedin-routes*
  (ANY "/test-url" [] (test-resources))
  (GET "/" [] test-auth))


(def loggedin-routes
     (-> #'loggedin-routes*
         wrap-params
         (wrap-authentication signed-backend)))

(defroutes app
  (ANY "*" [] anon-routes)
  (ANY "*" [] loggedin-routes))
