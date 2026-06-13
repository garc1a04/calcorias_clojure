(ns calcorias.service
  (:require [calcorias.use-case.add-exercise :as add_exercise]
            [calcorias.use-case.add-food :as add_food]
            [calcorias.use-case.add-user :as add_user]
            [calcorias.use-case.find-calories :as find_calories]
            [calcorias.use-case.find-user :as find_user]
            [calcorias.use-case.get-calories-saldo :as calories-saldo]))

(defn add_user [req]
  (let [data (add_user/execute req)]
    data))

(defn add_food [req]
  (let [data (add_food/execute req)]
    data))

(defn add_exercise [req]
  (let [data (add_exercise/execute req)]
    data))

(defn find_calories [period]
  (let [data (find_calories/execute period)]
    data))

(defn find_user []
  (let [data (find_user/execute)]
    data))

(defn get_calories_saldo [period]
  (let [data (calories-saldo/execute period)]
    data))