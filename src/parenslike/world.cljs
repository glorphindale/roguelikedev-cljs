(ns parenslike.world
  (:require [parenslike.gfx :as gfx]))

(def world-size 9)

(defn get-tile []
  (if (> (rand) 0.3)
    :floor
    :wall))

(defn gen-world []
  (let [empty-field (zipmap (for [x (range 0 world-size) y (range 0 world-size)] [x y])
                            (repeatedly get-tile))
        walls (merge (zipmap (for [x (range 0 world-size) y [0 (dec world-size)]] [x y]) (repeat :wall))
                     (zipmap (for [y (range 0 world-size) x [0 (dec world-size)]] [x y]) (repeat :wall)))]
    (merge empty-field walls)))
