(ns scratchpad.poly-test
  (:require [scratchpad.poly :as poly]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :as ct :refer [defspec]]))

(defn gen-sequential
  "Returns either a vector or list (thus satisfying `sequential?`)
  with elements from `g`."
  [g]
  (gen/one-of [(gen/vector g) (gen/list g)]))

(defspec single-arity 100
  (prop/for-all 
    [x gen/nat]
    (= (poly/double x) (* 2 x))))

(defspec apply-multiarg-equals-seq 100
  (prop/for-all 
    [xs (gen/such-that #(> (count %) 1) (gen-sequential gen/nat))]
    (let [dbl-xs (map #(* 2 %) xs)]
      (= (poly/double xs) (apply poly/double xs) dbl-xs))))

(defspec single-arity-equals-seq 100
  (prop/for-all 
    [xs (gen/bind (gen/not-empty (gen-sequential gen/nat)) #(gen/return (take 1 %)))]
    (let [dbl-xs (map #(* 2 %) xs)]
      (= (first (poly/double xs)) (apply poly/double xs) (first dbl-xs)))))

(deftest returns-empty
  (is (= [] (poly/double [])))
  (is (= '() (poly/double '()))))
