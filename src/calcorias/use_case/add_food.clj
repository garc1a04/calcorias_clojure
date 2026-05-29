(ns calcorias.use-case.add-food
  (:require [calcorias.database.db :as db]
            [food.core :as food]))


(defn save-db [foods]
  (println foods)
  (db/add_calories foods)
  {:message "sucess"
   :calories foods})

(defn right? [foods name]
  (= (.size (filter #(= (:description %) name) foods)) 1))

(defn execute [req]
  (let [foods (food/find_kcal req)
        foods_db (map (fn [item]
                        {:type "intake"
                         :name (:description item)
                         :kcal (:foodNutrients (:value item))
                         :grams (:grams req)})
                      foods)]

    (if (or (= (.size foods) 1) (right? foods (:name req)))
      (save-db (first foods_db))
      {:message "Many results"
       :foods (map (fn [item]
                     {:name (:description item)}) foods)})))