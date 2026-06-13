(ns exercise.core
  (:require [clj-http.client :as http-client]))

(def api-url (System/getenv "URL_EXERCISE"))
(def chave (System/getenv "API_KEY_EXERCISE"))

(defn- toPound [kilograms]
  (* (Integer/parseInt kilograms) 2.205))

(defn api [api-url req user chave]
  (let [req (http-client/get api-url
                             {:query-params {"activity" (:name req)
                                             "weight" (toPound (:weight user))
                                             "duration" (:minutes req)}
                              :headers {"X-Api-Key" chave}
                              :as :json})]
    (:body req)))

(defn burned-calories [req user]
  (let [data (api api-url req user chave)]
    data))