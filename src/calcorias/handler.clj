(ns calcorias.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [shared.wrapper.wrap-filter :refer [wrapper-filter]]
            [calcorias.controller :as controller]))

(defroutes app-routes
  (POST "/api/user" req
    (controller/add_user req))

  (POST "/api/food" req
    (controller/add_food req))

  (POST "/api/user/burned" req
    (controller/add_exercise req))

  (GET "/api/calories" [period]
    (controller/find_calories period))

  (GET "/api/calories/saldo" [period]
    (controller/get_calories_saldo period))

  (GET "/api/user" []
    (controller/find_user))

  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrapper-filter)))