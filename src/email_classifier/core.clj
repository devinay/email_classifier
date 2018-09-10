(ns email-classifier.core
  (:require [email-classifier.conn :as conn])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args])
;;  (println (conn/getStore)))
;; (println (conn/connect "server" "uname" "pass")))



;;wishful thinking :

(comment write-to-db
  (map convert-to-json
    (get-from-email-server (get-last-message-written-in-db-with-foldername))))

;;get-last-message