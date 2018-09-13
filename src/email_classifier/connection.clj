(ns email-classifier.conn
  "utility for connecting to email server"
  (:require [cheshire.core :refer :all])
  (:import (microsoft.exchange.webservices.data.core ExchangeService)
           (microsoft.exchange.webservices.data.core.enumeration.misc ExchangeVersion)
           (microsoft.exchange.webservices.data.core.service.item EmailMessage)
           (microsoft.exchange.webservices.data.credential ExchangeCredentials)
           (microsoft.exchange.webservices.data.credential WebCredentials)
           (microsoft.exchange.webservices.data.property.complex MessageBody)
           (microsoft.exchange.webservices.data.autodiscover IAutodiscoverRedirectionUrl)
           (microsoft.exchange.webservices.data.core.service.folder Folder)
           (microsoft.exchange.webservices.data.core.enumeration.property WellKnownFolderName)
           (microsoft.exchange.webservices.data.search ItemView)
           (microsoft.exchange.webservices.data.core PropertySet)
           (microsoft.exchange.webservices.data.core.enumeration.property BasePropertySet BodyType)))

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

(def propSet 
  (let [prop (new PropertySet BasePropertySet/FirstClassProperties)]
    (do 
      (.setRequestedBodyType prop BodyType/Text)
      (identity prop))))

;; Note that the PropertySet has to be used for both 
;; service.FindItems() and item.Load() for this to work properly.
(def itemview-50 
  (let [item (new ItemView 50)]
    (do 
      (.setPropertySet item propSet)
      (identity item))))

(def itemlist-from-inbox
  (.findItems (get-exch-service email password) WellKnownFolderName/Inbox itemview-50))

(defn loadFn [ews-message]
  (do (.load ews-message propSet) (identity ews-message)))

;;(first (lazy-list-batch-of-50-messages)) => gives 50 messages
;; (take 2 lazy-list-batch-of-50-messages)) => gives 100 messages
(def lazy-list-batch-of-50-messages
  (repeatedly 
    (fn repeat-fn []
      (let [moreavailable? (:moreAvailable (bean itemlist-from-inbox))
            increment (if moreavailable? (.setOffset itemview-50 (+ 50 (.getOffset itemview-50))))]
        (if moreavailable? (map loadFn (iterator-seq (.iterator itemlist-from-inbox))) nil)))))



(defn str-of[prop]
  (if (nil? prop) nil
    (if
      (instance? java.lang.Iterable prop) 
      (map #(.toString %) (iterator-seq (.iterator prop)))
      (.trim (.toString prop)))))


;;The list of properties from 
;; https://docs.microsoft.com/en-us/exchange/client-developer/exchange-web-services/email-properties-and-elements-in-ews-in-exchange
;; Troublesome ones are commented.
(def prop-list ["Id",
"ParentFolderId",
"ItemClass",
"Subject",
"Sensitivity",
"Body",
"Attachments",
"DateTimeReceived",
"Size",
"Importance",
"InReplyTo",
"IsSubmitted",
"IsDraft",
"IsFromMe",
"IsResend",
;;"IsUnmodified ",
"DateTimeSent",
"DateTimeCreated",
;;"ReminderDueBy",
;;"IsReminderSet",
;;"ReminderMinutesBeforeStart",
"DisplayCc",
"DisplayTo",
"HasAttachments",
"Culture",
"EffectiveRights",
"LastModifiedName",
"LastModifiedTime",
"IsAssociated",
;;"WebClientReadFormQueryStrin",
;;"WebClientEditFormQueryString",
"ConversationId",
;;"Flag",
;;"InstanceKey",
"Sender",
"ToRecipients",
"CcRecipients",
"BccRecipients",
"IsReadReceiptRequested",
;;"IsDeliveryReceiptRequested",
"ConversationIndex",
"ConversationTopic",
"From",
"InternetMessageId",
"IsRead",
"IsResponseRequested",
"ReplyTo",
"References",
"ReceivedBy",
"ReceivedRepresenting"])

(defn getproperty[property message] 
  (str-of (clojure.lang.Reflector/invokeInstanceMethod message (str "get" property) (to-array []))))

(defn jsonify [msg] (generate-string (zipmap prop-list (map #(getproperty % msg) prop-list))))
