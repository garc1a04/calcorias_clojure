(ns calcorias.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [cheshire.core :as json]
            [calcorias.service :as service]))

(defn json [data]
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string data)})

(defroutes app-routes
  (POST "/api/user" [] (json (service/add_user))) ;; "Cadastrar dados pessoais (altura, peso, idade e sexo);"

  (POST "/api/food" [] (json (service/add_food))) ;; "Registrar consumo de alimento (ganho de caloria);"

  (POST "/api/burned" [] (json (service/add_exercise))) ;; "Registrar realização de atividade física (perda de caloria);"

  (GET "/api/user" [] (json (service/find_user))) ;; Consultar dados pessoais (altura, peso, idade e sexo);

  (GET "/api/food" [] (json {:mensage "NÃO ENTENDI :)"}))   ;; "Consultar extrato de transações (por período);"

  (GET "/api/calories" [] (json (service/find_user))) ;; Consultar saldo de calorias (por período).

  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)))