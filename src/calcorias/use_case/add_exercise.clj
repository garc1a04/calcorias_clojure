(ns calcorias.use-case.add-exercise
  (:require [calcorias.database.db :as db]))

(defn execute [req]
  (db/add_calories req)
  {:message "sucess"
   :calories req})