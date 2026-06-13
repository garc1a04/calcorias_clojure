(ns calcorias.database.db)

(;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
 ;; PADRÃO DEFINIDO PARA A CRIAÇÃO DO BANCO DE DADOS DE FORMA ATOMICA.
 ;;
 ;; :user -> terá todas as informações do usuário (altura, peso, idade e sexo)
 ;; :calories -> terá todas as informações voltados para o peso
 ;;
 ;; user-schema
 ;; :name :weight :age :sex
 ;;
 ;; calories-schema
 ;;  --> food :type "intake" :name :kcal :grams :date
 ;;  --> workout :type :name :kcal :date
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
 )

(def db
  (atom {:user {}
         :calories []}))

(defn result []
  @db)

(defn add_user [req]
  (swap! db assoc :user req))

(defn add_calories [req]
  (let [values 
        (conj (:calories (result)) req)]
    (swap! db assoc :calories values)))