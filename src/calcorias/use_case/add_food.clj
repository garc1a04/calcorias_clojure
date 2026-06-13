(ns calcorias.use-case.add-food
  (:require [calcorias.database.db :as db]
            [food.core :as food]
            [clojure.string :as str]))

(defn- calculo-kcal [kcal grams]
  (/ (* kcal grams) 100))

(defn- extrair-valor [foods req]
  (let [data (map (fn [item]
                    {:type "intake"
                     :name (:description item)
                     :kcal  (calculo-kcal (:value (first (:foodNutrients item)))
                                          (:grams req))
                     :grams (:grams req)
                     :date (str (java.time.LocalDate/now))})
                  foods)]
    (first data)))

(defn- right? [foods name]
  (let [data (filter #(= (str/lower-case (:description  %))
                         (str/lower-case name))
                     foods)]
    (= (.size data) 1)))

(defn- save-db [foods]
  (db/add_calories foods)
  {:message "sucess"
   :calories foods})

(defn execute [req]
  (let [foods (food/find_kcal req)]
    (if (or (= (.size foods) 1) (right? foods (:name req)))
      (save-db (extrair-valor foods req))
      {:message "Many results"
       :foods (map (fn [item]
                     {:name (:description item)}) foods)})))