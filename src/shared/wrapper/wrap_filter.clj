(ns shared.wrapper.wrap-filter
  (:require [cheshire.core :as json]))

(defn- error-body-validations [data]
  (json/generate-string {:error (:error data)
                         :message (:message data)
                         :fields (:fields data)}))

(defn- error-body-not-found [data]
  (json/generate-string {:error (:error data)
                         :message (:message data)}))

(defn wrapper-filter [handler]
  (fn [request]
    (try
      (handler request)

      (catch clojure.lang.ExceptionInfo e
        (let [data (ex-data e)
              status (:status data 400)]

          (cond
            (or (= status 400) (= status 422)) {:status status
                                                :headers {"Content-Type" "application/json"}
                                                :body (error-body-validations data)}
            (= status 404)  {:status status
                             :headers {"Content-Type" "application/json"}
                             :body (error-body-not-found data)})))
      (catch Exception e
        {:status 500
         :headers {"Content-Type" "application/json"}
         :body {:message (.getMessage e)}}))))