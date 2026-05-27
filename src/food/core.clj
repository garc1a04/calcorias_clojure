(ns food.core
  (:require [clj-http.client :as http-client]))

;; https://api-ninjas.com/api/nutrition#nutrition-endpoint
(def api-url "https://api.api-ninjas.com/v1/nutrition") ;; Colocar um dotenv
(def chave "jCYLib1SgzRBxfxKuLJfoKXS1nO0koCvuQ89lTNz")

(defn api [api-url texto-busca chave]
  ((http-client/get api-url
                      {:headers {"X-Api-Key" chave}
                       :query-params {"query" texto-busca}
                       :as :json})))

(defn find_kcal [req]
  (println req)
  (api api-url (str (req :name) (req :grams) " lb") chave))