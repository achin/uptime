(ns uptime.core)

(defn add-service [name url]
  {:name name
   :url url
   :votes []}) 

(defn vote-service [s state date comment]
  (let [v {:state state :date date :comment comment}]
    (assoc s :votes (conj (:votes s) v))))
