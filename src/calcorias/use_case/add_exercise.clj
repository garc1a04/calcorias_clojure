(ns calcorias.use-case.add-exercise
  (:require [calcorias.database.db :as db]
            [calcorias.use-case.find-user :as user]
            [exercise.core :as ex]
            [shared.validations :as validation]
            [clojure.string :as str]
            [shared.error.app-error :refer [error error-not-found]]))

(defn- json-format [message body]
  {:message message
   :exercise body})

(defn- json-format-db [item]
  {:type "burned"
   :name (:name item)
   :minutes (:duration_minutes item)
   :kcal (:total_calories item)
   :date (str (java.time.LocalDate/now))})

(defn- json-format-many-results [item]
  {:name (:name item)})

(defn- extrair-valores [function data]
  (map function data))

(defn- right? [exercise name]
  (let [data (filter #(= (str/lower-case (:name  %))
                         (str/lower-case name))
                     exercise)]
    (= (.size data) 1)))

(defn- save-db [data]
  (db/add_calories data)
  (json-format "sucess" data))

(defn exercise-save [name data]
  (cond
    (<= (.size data) 0) (error-not-found "Not Found" "Exercise Not Found" 404)
    (right? data name) (->> data
                            (extrair-valores json-format-db)
                            (first)
                            (save-db))
    (> (.size data) 1) (->> data
                            (extrair-valores json-format-many-results)
                            (json-format "Many Results"))
    :else (->> data
               (extrair-valores json-format-db)
               (first)
               (save-db))))


(defn execute [req]
  (if (validation/exists-user?)
    (let [user (user/execute)
          data (ex/burned-calories req user)
          name (:name req)]
      (exercise-save name data))

    (error-not-found "Not Found" "User Not Found" 404)))