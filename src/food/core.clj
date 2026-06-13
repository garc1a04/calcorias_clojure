(ns food.core
  (:require [clj-http.client :as http-client]))

(def api-url (System/getenv "URL_FOOD"))
(def chave (System/getenv "API_KEY_FOOD"))

;; valor da kcal é medido em gramas
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
  (let [datas (api api-url (req :name) chave)]
    (extrair-resumo-alimentos datas)))