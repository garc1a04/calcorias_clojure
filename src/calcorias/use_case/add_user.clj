(ns calcorias.use-case.add-user
  (:require [calcorias.database.db :as db]))

(defn- json-format [message req]
  {:message message
   :data req})

(defn execute [req]
  (db/add_user req)
  (json-format "User successfully created." req))