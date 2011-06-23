(defproject uptime "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [congomongo "0.1.4-SNAPSHOT"]
		 [compojure "0.6.3"]]
  :dev-dependencies [[lein-ring "0.4.3"]]
  :ring {:handler uptime.core/app})
