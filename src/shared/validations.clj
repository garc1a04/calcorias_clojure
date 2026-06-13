(ns shared.validations
  (:require [calcorias.use-case.find-user :as user]
            [shared.error.app-error :refer [error error-body-controller]]
            [clojure.string :as str]))

(defn- user_contains [req]
  (let [data (str
              (if (contains? req :name) "" "name ")
              (if (contains? req :weight) "" "weight "))]
    (if (empty? data)
      true
      (error (error-body-controller
              "Bad Request"
              "Missing required fields in JSON payload."
              (str/split data #" ")
              400)))))
            
(defn- user_type [req]
  (let [data (str
              (if (string? (req :name)) "" "name is string,")
              (if (string? (req :weight)) "" "weight is string,"))]
    (if (empty? data)
      true
      (error (error-body-controller
              "Unprocessable Entity"
              "Invalid data types provided in the payload."
              (str/split data #",")
              422)))))

(defn user-validations [req]
  (and
   (user_contains req)
   (user_type req)))


(defn- food_contains [req]
  (let [data (str
              (if (contains? req :name) "" "name ")
              (if (contains? req :grams) "" "grams "))]
    (if (empty? data)
      true
      (error (error-body-controller
              "Bad Request"
              "Missing required fields in JSON payload."
              (str/split data #" ")
              400)))))

(defn- food_type [req]
  (let [data (str
              (if (string? (req :name)) "" "name is string,")
              (if (int? (req :grams)) "" "grams is integer"))]
    (if (empty? data)
      true
      (error (error-body-controller
              "Unprocessable Entity"
              "Invalid data types provided in the payload."
              (str/split data #",")
              422)))))

(defn food-validations [req]
  (and
   (food_contains req)
   (food_type req)))

(defn- burned_contains [req]
  (let [data (str
              (if (contains? req :name) "" "name ")
              (if (contains? req :minutes) "" "minutes "))]
    (if (empty? data)
      true
      (error (error-body-controller
              "Bad Request"
              "Missing required fields in JSON payload."
              (str/split data #" ")
              400)))))

(defn- burned_type [req]
  (let [data (str
              (if (string? (req :name)) "" "name is string,")
              (if (int? (req :minutes)) "" "minutes is integer"))]
    (if (empty? data)
      true
      (error (error-body-controller
              "Unprocessable Entity"
              "Invalid data types provided in the payload."
              (str/split data #",")
              422)))))

(defn burned-validations [req]
  (println req)
  (and
   (burned_contains req)
   (burned_type req)))

(defn exists-user? []
  (not (empty? (user/execute))))