(ns email-classifier.core
  (:require [email-classifier.connection :as conn])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
;;  (println (conn/getStore)))
 (println (conn/connect "server" "uname" "pass")))



;;wishful thinking :
(write-to-db
  (convert-to-json
    (get-from-server (get-last-message-written-in-db))))

;;get-last-message