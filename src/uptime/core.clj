(ns uptime.core
  (:use somnium.congomongo))

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

(defn vote-service [s state date comment]
  (let [v {:state state :date date :comment comment}]
    (assoc s :votes (conj (:votes s) v))))
