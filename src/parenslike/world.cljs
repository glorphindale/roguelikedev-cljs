(ns parenslike.world
  (:require [parenslike.gfx :as gfx]))

(def world-size 9)

(defn gen-world []
  (zipmap (for [x (range 0 world-size) y (range 0 world-size)] [x y])
          (repeat :floor)))
