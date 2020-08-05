(ns parenslike.utils)

(def directions
  [[0 1]
   [0 -1]
   [1 0]
   [-1 0]])

(defn pos+ [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])
