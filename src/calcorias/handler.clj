(ns calcorias.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [cheshire.core :as json]
            [calcorias.service :as service]
            [shared.validations :as validation]))

(defn json [data status]
  {:headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string data)
   :status status})

(defroutes app-routes
  ;; "Cadastrar dados pessoais (altura, peso, idade e sexo);"
  (POST "/api/user" req
    (if (validation/user-validations (req :body))
      (json (service/add_user (req :body)) 200)
      (json {:message "Request body invalid"} 500)))

  ;; "Registrar consumo de alimento (ganho de caloria);"
  (POST "/api/food" req 
    (if (validation/food-validations (req :body)) 
      (json (service/add_food (req :body)) 200)
      (json {:message "Request body invalid"} 500)))

  ;; "Registrar realização de atividade física (perda de caloria);"
  (POST "/api/burned" req     
    (if (validation/burned-validations (req :body))
      (json (service/add_exercise) 200)
      (json {:message "Request body invalid"} 500)))

  ;; Consultar dados pessoais (altura, peso, idade e sexo);
  (GET "/api/user" [] (json (service/find_user) 201)) 

  ;; "Consultar extrato de transações (por período);"
  (GET "/api/????" [] (json {:mensage "NÃO ENTENDI :)"} 201))   

  ;; Consultar saldo de calorias (por período).
  (GET "/api/calories" [] (json (service/find_calories) 201)) 

  (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-json-body {:keywords? true :bigdecimals? true})))