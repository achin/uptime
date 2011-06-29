(ns uptime.web
  (:use compojure.core
        clojure.contrib.json)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [uptime.db :as db]))

(defn json-response [s & {status :status headers :headers}]
  (let [json (json-str s)]
    {:status (or status 200)
     :headers (merge {"Content-Type" "application/json"} headers)
     :body json}))

(defn root []
  "<h1>Uptime!</h1>")

(defn list-services []
  (json-response (db/list-services)))

(defn get-service [id]
  (if-let [s (db/get-service id)]
    (json-response s)
    {:status 404}))

(defn add-service [name url]
  (if-let [s (db/add-service name url)]
    (let [url (str "/service/" (:_id s))]
      (json-response s
                     :status 201
                     :headers {"Location" url}))
    {:status 500}))

(defn remove-service [id]
  (if (db/remove-service id)
    {:status 204}
    {:status 500}))

(defroutes main-routes
  (GET "/" []
       (root))
  
  (GET "/service" []
       (list-services))
  
  (GET "/service/:id" [id]
       (get-service id))
  
  (POST "/service" [name url]
        (add-service name url))
  
  (DELETE "/service/:id" [id]
          (remove-service id))
  
  (route/resources "/")
  (route/not-found "Page not found."))

(def app
  (handler/site main-routes))
