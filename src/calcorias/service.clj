(ns calcorias.service
  (:require [calcorias.use-case.add-exercise :as add_exercise]
            [calcorias.use-case.add-food :as add_food]
            [calcorias.use-case.add-user :as add_user]
            [calcorias.use-case.find-calories :as find_calories]
            [calcorias.use-case.find-user :as find_user]))

(defn add_user [req]
  (add_user/execute req))

(defn add_food [req]
  (add_food/execute req))

(defn add_exercise [req]
  (add_exercise/execute req))

(defn find_calories []
  (find_calories/execute ))

(defn find_user []
  (find_user/execute ))