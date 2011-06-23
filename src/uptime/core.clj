(ns uptime.core
  (:use somnium.congomongo
        compojure.core
        clojure.contrib.json)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(mongo! :db "uptime")

(defn list-services []
  (fetch :services))

(defn- uuid []
  (.toString (java.util.UUID/randomUUID)))

(defn add-service [name url]
  (insert! :services {:_id (uuid)
                     :name name
                     :url url
                     :votes []})) 

(defn get-service [id]
  (fetch-one :services :where {:_id id}))

(defn vote-service [id state date comment]
  (when-let [s (get-service id)]
    (let [v {:state state :date date :comment comment}
          vs (conj (:votes s) v)]
      (update! :services s (assoc s :votes vs)))))

(defn remove-service [id]
  (when-let [s (get-service id)]
    (destroy! :services s)))

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
