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
 ;;  --> food :type :grams :kcal :energy_balance
 ;;  --> workout :type :hours :kcal :energy_balance
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
 )

(def db
  (atom {:user
         :calories}))

(defn result []
  @db)

(defn add_user [req]
  (swap! db assoc :user req))

(defn add_calories [req]
  (swap! db assoc :calories req))

;; Terá paginação?
(defn find_calories []
  )