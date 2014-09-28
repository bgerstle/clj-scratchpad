(ns scratchpad.seqlist)

(defn seqlist [x d n]
  (take n (iterate (partial + d) x)))
