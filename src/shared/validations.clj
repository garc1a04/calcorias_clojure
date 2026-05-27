(ns shared.validations)

(defn user_contains [req]
  (and
   (contains? req :name)
   (contains? req :weight)
   (contains? req :age)
   (contains? req :sex)))

(defn user_type [req]
  (and
   (string? (req :name))
   (string? (req :weight))
   (string? (req :age))
   (string? (req :sex))))

(defn user-validations [req]
  (and 
   (user_contains req)
   (user_type req)))

(defn food_contains [req]
  (and
   (contains? req :name)
   (contains? req :grams)))

(defn food_type [req]
  (and
   (string? (req :name))
   (string? (req :grams))))

(defn food-validations [req]
  (and
   (food_contains req)
   (food_type req)))

(defn burned-validations [req]
  (println req)
  false)