(ns scratchpad.poly
  "An exercise in polymorphic functions."
  (:refer-clojure :exclude [double]))

(defn double
  "Doubles the input. When input is a single number, the function 
  returns a single number which is the doubled input. When the input 
  is a collection of numbers (or there are multiple inputs), the result 
  is a sequence of the doubling of the input values."
  [x & rest]
  (if (sequential? x)
    (map double x)
    (if (or (nil? rest) (empty? rest))
      (* 2 x)
      (double (cons x rest)))))
