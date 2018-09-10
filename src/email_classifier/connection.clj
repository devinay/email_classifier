(ns email-classifier.conn
  "utility for connecting to email server"
  (:import (microsoft.exchange.webservices.data.core ExchangeService)
           (microsoft.exchange.webservices.data.core.enumeration.misc ExchangeVersion)
           (microsoft.exchange.webservices.data.core.service.item EmailMessage)
           (microsoft.exchange.webservices.data.credential ExchangeCredentials)
           (microsoft.exchange.webservices.data.credential WebCredentials)
           (microsoft.exchange.webservices.data.property.complex MessageBody)
           (microsoft.exchange.webservices.data.autodiscover IAutodiscoverRedirectionUrl)))



  
(defn get-exch-service [email pass]
  (doto (new ExchangeService)
    (.setCredentials (new WebCredentials email pass));;    (.setUrl (new URI ews-url))
    (.autodiscoverUrl email 
      (reify IAutodiscoverRedirectionUrl
        (autodiscoverRedirectionUrlValidationCallback [this redirectionUrl]
          (.startsWith (.toLowerCase redirectionUrl) "https://"))))))
