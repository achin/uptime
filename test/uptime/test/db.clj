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

(remote-declare somnium.congomogo/fetch)

(fact
 (list-services) => :dummy-list
 (provided
  (somnium.congomongo/fetch :services) => :dummy-list))
