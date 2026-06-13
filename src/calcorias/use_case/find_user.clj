(ns calcorias.use-case.find-user
  (:require [calcorias.database.db :as db]))

(defn execute []
  (let [result (db/result)]
    (:user result)))