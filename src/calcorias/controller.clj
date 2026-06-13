(ns calcorias.controller
  (:require [calcorias.service :as service]
            [shared.validations :as validation]
            [shared.json :refer [json-ok json-create]]))

(defn add_user [req]
  (let [body (req :body)
        validation (validation/user-validations body)]
    (when validation
      (let [data (service/add_user body)]
        (json-create data)))))

(defn add_food [req]
  (let [body (req :body)
        validation (validation/food-validations body)]
    (when validation
      (let [values (service/add_food body)]
        (json-ok values)))))

(defn add_exercise [req]
  (let [body (req :body)
        validation (validation/burned-validations body)]
    (when validation
      (json-create (service/add_exercise body)))))

(defn find_calories [period]
  (let [data (service/find_calories period)]
    (json-ok {:data data})))

(defn find_user []
  (let [data (service/find_user)]
    (json-ok {:data data})))

(defn get_calories_saldo [period]
  (let [data (service/get_calories_saldo period)]
    (json-ok {:data data})))