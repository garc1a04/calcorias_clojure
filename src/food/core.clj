(ns food.core
  (:require [clj-http.client :as http-client]))

;; https://fdc.nal.usda.gov/api-guide


(defn api [api-url texto-busca chave]
  (let [req (http-client/get api-url
                             {:query-params {"api_key" chave
                                             "query" texto-busca
                                             "dataType" ["Foundation",
                                                         "SR Legacy"]
                                             "requireAllWords" true
                                             "pageSize" 5}
                              :as :json})]
    (:body req)))

(defn extrair-resumo-alimentos [dados-api]
  (let [alimentos (:foods dados-api)]
    (map (fn [item]
           {:fdc_id       (:fdcId item)
            :description       (:description item)
            :foodNutrients  (filter #(= (:unitName %) "KCAL") (:foodNutrients item))})
         alimentos)))

(defn find_kcal [req]
  (extrair-resumo-alimentos (api api-url (req :name) chave)))