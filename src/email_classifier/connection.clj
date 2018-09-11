(ns email-classifier.conn
  "utility for connecting to email server"
  (:import (microsoft.exchange.webservices.data.core ExchangeService)
           (microsoft.exchange.webservices.data.core.enumeration.misc ExchangeVersion)
           (microsoft.exchange.webservices.data.core.service.item EmailMessage)
           (microsoft.exchange.webservices.data.credential ExchangeCredentials)
           (microsoft.exchange.webservices.data.credential WebCredentials)
           (microsoft.exchange.webservices.data.property.complex MessageBody)
           (microsoft.exchange.webservices.data.autodiscover IAutodiscoverRedirectionUrl)
           (microsoft.exchange.webservices.data.core.service.folder Folder)
           (microsoft.exchange.webservices.data.core.enumeration.property WellKnownFolderName)
           (microsoft.exchange.webservices.data.search ItemView)))

(defn get-exch-service [email pass]
  (doto (new ExchangeService)
    (.setCredentials (new WebCredentials email pass))
    (.autodiscoverUrl email 
      (reify IAutodiscoverRedirectionUrl
        (autodiscoverRedirectionUrlValidationCallback [this redirectionUrl]
          (.startsWith (.toLowerCase redirectionUrl) "https://"))))))

(def email
  (do (print "What's your email? ") (flush) (read-line)))

(def password
  (do (print "What's your password? ") (flush) (read-line)));;TBD!!

(def itemview-50 (new ItemView 50))

(def itemlist-from-inbox
  (.findItems (get-exch-service email password) WellKnownFolderName/Inbox itemview-50))

(def lazy-list-of-50-messages
  (repeatedly 
    (fn repeat-fn []
      (let [moreavailable? (:moreAvailable (bean itemlist-from-inbox))
            increment (if moreavailable? (.setOffset itemview-50 (+ 50 (.getOffset itemview-50))))]
        (if moreavailable? (iterator-seq (.iterator itemlist-from-inbox)) nil)))))
  
  

