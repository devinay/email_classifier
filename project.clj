(defproject email_classifier "0.1.0-SNAPSHOT"
  :description "Connect to an email server, pull email from it and index it"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[cheshire "5.8.0"]
                 [commons-net "3.6"]
                 [org.clojure/clojure "1.8.0"]
                 [com.sun.mail/javax.mail "1.5.6"]
                 [com.microsoft.ews-java-api/ews-java-api "2.0"]]
  :plugins [[lein2-eclipse "2.0.0"]]
  :main ^:skip-aot email-classifier.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})