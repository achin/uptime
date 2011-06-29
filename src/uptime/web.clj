(ns uptime.web
  (:use compojure.core
        clojure.contrib.json)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [uptime.db :as db]))

(defn- json-response [s]
  (let [json (json-str s)]
    {:headers {"Content-Type" "application/json"}
     :body s}))

(defn- root []
  "<h1>Uptime!</h1>")

(defn- list-services []
  (json-response (db/list-services)))

(defn- get-service [id]
  (if-let [s (db/get-service id)]
    (json-response s)
    {:status 404}))

(defroutes main-routes
  (GET "/" []
       (root))
  (GET "/service" []
       (list-services))
  (GET "/service/:id" [id]
       (get-service id))
  (route/resources "/")
  (route/not-found "Page not found."))

(def app
  (handler/site main-routes))
