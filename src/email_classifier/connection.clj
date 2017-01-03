(ns email-classifier.connection
  "utility for connecting to email server"
  (:import (org.apache.commons.net PrintCommandListener)
           (org.apache.commons.net.imap IMAPClient)
           (javax.mail Session)))



(defn get-mail [server username password]
  (doto (IMAPClient.)
    (.setDefaultTimeout 60000)
    (.addProtocolCommandListener (PrintCommandListener. System/out true))
    (.connect server)
    (.capability)
    (.login username password)))

(comment    (.setSoTimeout 6000)
    (.capability)
    (.select "inbox")
    (.examine "inbox")
    (.status "inbox" (into-array String ["MESSAGES"]))
    (.logout)
    (.disconnect))

(defn getProps []
    (doto (new java.util.Properties) (.setProperty "mail.store.protocol" "imaps")))

(defn getStore [] 
  (. (Session/getInstance (getProps)) getStore))

(defn connect[server uname pass]
  (doto (getStore) (.connect server uname pass)))

(comment why is (.getDefaultInstance Session (getProps)) throwing a ClassNotFoundException ?)
  
    
