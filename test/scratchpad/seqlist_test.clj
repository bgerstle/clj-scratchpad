(ns scratchpad.seqlist-test
  (:require [clojure.test :refer :all]
            [scratchpad.seqlist :refer :all]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :as ct :refer [defspec]]))

(defn to-deltas [xs]
  (map #(* -1 (apply - %)) (partition 2 1 xs)))

(defspec seqlist-not-empty 100
  (prop/for-all [x gen/nat d gen/nat n gen/s-pos-int]
     (let [xs (seqlist x d n)]
       (= (count xs) n)
       (= (first xs) x)
       (= (last xs) (+ x (* d n)))
       (let [deltas (to-deltas xs)]
         (if (empty? xs)
           (= n 1)
           (apply = (conj deltas d)))))))

(defspec seqlist-empty 100
  (prop/for-all [x gen/nat d gen/nat]
     (empty? (seqlist x d 0))))

