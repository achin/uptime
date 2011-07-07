(ns uptime.test.db
  (:use uptime.db)
  (:use clojure.test)
  (:use midje.sweet))

(defmacro remote-declare [n]
  (let [ns (namespace n) 
        v (name n) 
        orig-ns (str *ns*)] 
    `(do (in-ns '~(symbol ns)) 
         (declare ~(symbol v)) 
         (in-ns '~(symbol orig-ns)))))

;;;

(remote-declare somnium.congomogo/fetch)

(fact
 (list-services) => truthy
 (provided
  (somnium.congomongo/fetch :services) => true))

;;;

(remote-declare somnium.congomongo/insert)

(fact
 (add-service ...name... ...url...) => truthy
 (provided
  (uuid) => ...uuid...
  (somnium.congomongo/insert! :services {:_id ...uuid...
                                         :name ...name...
                                         :url ...url...
                                         :votes []}) => true))
