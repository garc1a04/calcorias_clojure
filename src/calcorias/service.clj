(ns calcorias.service
  (:require [calcorias.use-case.add-exercise :as add_exercise]
            [calcorias.use-case.add-food :as add_food]
            [calcorias.use-case.add-user :as add_user]
            [calcorias.use-case.find-calories :as find_calories]
            [calcorias.use-case.find-user :as find_user]))

(defn add_user []
  (add_user/execute))

(defn add_food []
  (add_food/execute))

(defn add_exercise []
  (add_exercise/execute))

(defn find_calories []
  (find_calories/execute))

(defn find_user []
  (find_user/execute))