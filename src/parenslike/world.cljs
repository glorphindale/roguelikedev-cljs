(ns parenslike.world
  (:require [parenslike.gfx :as  gfx]
            [parenslike.utils :as utils]))

(def world-size 9)
(def max-walls (int (* 0.3 (* (- world-size 2) (- world-size 2)))))

(defn pick-random-floor [world]
  (ffirst (shuffle (filter (fn [[_ t]] (= t :floor)) world))))

(defn adjacent [pos]
  (map #(utils/pos+ pos %) utils/directions))

(defn get-tile-type [world pos]
  (get-in world pos :wall))

(defn connected? [world]
  "Wave propagation algorightm to check there are no unreacable spots"
  (let [all-floors (set (keys (filter (fn [[_ t]] (= t :floor)) world)))]
    (loop [wavefront #{(pick-random-floor world)}
           visited #{}
           ;; Just in case there are bugs - prevent infinute loops
           max-count (* world-size world-size)]
      (if (or (empty? wavefront) (< max-count 0))
        (= visited all-floors)
        (let [next-tile (first wavefront)
              neighbors (set (for [t (adjacent next-tile) :when (contains? all-floors t)] t)) 
              tiles-to-add (clojure.set/difference neighbors visited)
              new-wavefront (clojure.set/union tiles-to-add (disj wavefront next-tile))
              new-visited (conj visited next-tile)]
          #_(js/console.log (str
                               "max-count: " max-count
                               ", next: " next-tile 
                               ", neighbors: " neighbors 
                               ", tiles-to-add: " tiles-to-add
                               ", Wavefront: " wavefront
                               ", new-wavefront: " new-wavefront
                               ", visited: " visited 
                               ", new-visited " new-visited))
          (recur new-wavefront new-visited (dec max-count)))))))

(defn try-place-tile [world]
  (let [new-tile (pick-random-floor world)
        next-world (assoc world new-tile :wall)]
    (if (connected? next-world)
      next-world
      world))) 

(defn place-walls [world]
  (nth (iterate try-place-tile world) max-walls))

(defn gen-world []
  (let [empty-field (zipmap (for [x (range 0 world-size) y (range 0 world-size)] [x y])
                            (repeat :floor))
        borders (merge (zipmap (for [x (range 0 world-size) y [0 (dec world-size)]] [x y]) (repeat :wall))
                     (zipmap (for [y (range 0 world-size) x [0 (dec world-size)]] [x y]) (repeat :wall)))
        template (merge empty-field borders)]
    (place-walls template)))
