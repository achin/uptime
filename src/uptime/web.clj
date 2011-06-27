(ns uptime.web
  (:use compojure.core
        clojure.contrib.json
        uptime.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(defroutes main-routes
  (GET "/" [] "<h1>Uptime!</h1>")
  (GET "/service" []
       (json-str (list-services)))
  (GET "/service/:id" [id]
       (if-let [s (get-service id)]
         (json-str s)
         {:status 404}))
  (route/resources "/")
  (route/not-found "Page not found."))

(def app
  (handler/site main-routes))
