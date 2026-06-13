(ns calcorias.use-case.get-calories-saldo
  (:require [calcorias.database.db :as db])
  (:import [java.time.format DateTimeFormatter]
           [java.time LocalDate]))


(defn is-date-valid? [date_string period formatador]
  (let [date (LocalDate/parse date_string formatador)
        now (LocalDate/now)]
    (if (= period "day")
      (.isAfter date (.minusDays now 1))
      (.isAfter date (.minusMonths now 1)))))

(defn- filter-period [period results]
  (let [formatador (DateTimeFormatter/ofPattern "yyyy-MM-dd")]
    (filter #(is-date-valid? (:date %) period formatador) results)))


(defn kcal_list [item]
  (if (= (:type item) "intake")
    (:kcal item)
    (* (:kcal item) -1)))

(defn- json-format [balance]
  {:energy_balance balance})

(defn execute [period]
  (let [valores (filter-period period (:calories (db/result)))
        kcal (map kcal_list valores)
        balance (reduce + kcal)]
    (json-format balance)))