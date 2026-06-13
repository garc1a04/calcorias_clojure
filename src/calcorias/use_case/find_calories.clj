(ns calcorias.use-case.find-calories
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

(defn execute [period]
  (let [result (db/result)
        filter_results (filter-period (or period "month")
                                      (:calories result))]
    filter_results))