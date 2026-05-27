(ns calcorias.use-case.add-user
  (:require [calcorias.database.db :as db]))

(defn execute [req] 
  (db/add_user req)
  {:message "sucess" 
   :user req})