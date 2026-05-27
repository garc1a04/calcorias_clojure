(ns calcorias.use-case.add-food
  (:require [calcorias.database.db :as db]
            [food.core :as food]))

(defn execute [req]
  (println (food/find_kcal req))
  (db/add_calories req)
  {:message "sucess"
   :calories req})