(ns calcorias.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [cheshire.core :as json]))

(defn json [data]
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string data)})

(defroutes app-routes

  ;; "Cadastrar dados pessoais (altura, peso, idade e sexo);"
  (POST "/api" [] "Cadastrar/consultar dados pessoais (altura, peso, idade e sexo);")

  ;; "Registrar consumo de alimento (ganho de caloria);"
  (POST "/api/food" [] "Registrar consumo de alimento (ganho de caloria);")

  ;; "Registrar realização de atividade física (perda de caloria);"
  (POST "/api/burned" [] "Registrar realização de atividade física (perda de caloria);")

  ;; Consultar dados pessoais (altura, peso, idade e sexo);
  (GET "/api" [] "Consultar dados pessoais (altura, peso, idade e sexo);")

  ;; "Consultar extrato de transações (por período);"
  (GET "/api/food" [] "Consultar extrato de transações (por período);")

  ;; Consultar saldo de calorias (por período).
  (GET "/api/calories" [] (json {:mensage "Consultar saldo de calorias (por período)."}))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)))