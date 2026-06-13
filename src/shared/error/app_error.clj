(ns shared.error.app-error)


(defn error [body]
  (throw (ex-info "" body)))

(defn error-body-controller [err message fields status]
  {:error err
   :message message
   :fields fields
   :status status})

(defn error-not-found [err message status]
  (error {:error err
          :message message
          :status status}))