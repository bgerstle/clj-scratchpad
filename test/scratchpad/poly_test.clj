(ns scratchpad.poly-test
  (:require [scratchpad.poly :as poly]
            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :as ct :refer [defspec]]))

(defn gen-sequential
  "Returns either a vector or list (thus satisfying  `sequential?`)
  with elements from `g`."
  [g]
  (gen/one-of [(gen/vector g) (gen/list g)]))

(defspec single-arity 100
  (prop/for-all [x gen/nat]
    (let [dbl-x (* 2 x)]
      (= (poly/double x) dbl-x))))

(defspec >=two-arity 100
  (prop/for-all [xs (gen/such-that #(> (count %) 1) (gen-sequential gen/nat))]
    (let [dbl-xs (map #(* 2 %) xs)]
      (= (poly/double xs) (apply poly/double xs) dbl-xs))))

