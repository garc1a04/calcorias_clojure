(ns shared.json
  (:require [cheshire.core :as json]))

(defn json [data status]
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string data)
   :status status})

(defn json-ok [data]
  (json data 200))

(defn json-create [data]
  (json data 201))
