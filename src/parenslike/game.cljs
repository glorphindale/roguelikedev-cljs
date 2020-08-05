(ns parenslike.game
  (:require [goog.events :as g-events]
            [parenslike.gfx :as gfx]  
            [parenslike.utils :as utils]  
            [parenslike.world :as world]))

(def height (* gfx/tile-size world/world-size))
(def width (* gfx/tile-size world/world-size))

(defonce state (atom {:init false}))

(defonce spritesheet
  (js/Image.))


(def directions
  {"w" [0 -1]
   "s" [0 1]
   "a" [-1 0]
   "d" [1 0]})

(defn tile-empty? [world tile]
  (let [tile-type (world tile)]
    (= tile-type :floor)))

(defn get-empty-tile [world]
  (let [[position _] (first (filter #(= (second %) :floor) world))]
    position))

(defn new-game []
  (let [world (world/gen-world)
        position (get-empty-tile world)]
  {:game {
          :world world
          :player position}}
  ))

(defn can-move? [world new-pos]
  (tile-empty? world new-pos))

(defn try-move [game direction]
  (let [new-pos (utils/pos+ (game :player) (directions direction))]
    (if (tile-empty? (:world game) new-pos)
      (assoc-in game [:player] new-pos)
      game)))

(defn keypress-handler [event]
  (case (.-key event)
    "w"  (swap! state update-in [:game] #(try-move % "w"))
    "s"  (swap! state update-in [:game] #(try-move % "s"))
    "a"  (swap! state update-in [:game] #(try-move % "a"))
    "d"  (swap! state update-in [:game] #(try-move % "d"))
    "r"  (swap! state merge (new-game))
    0
    ))

(defn keypress [event]
  (keypress-handler event))

(defn draw []
  (let [canvas (. js/document querySelector "canvas")
        ctx (. canvas getContext "2d")
        spritesheet (get-in @state [:spritesheet])
        position (get-in @state [:game :player])
        world (get-in @state [:game :world])]
    (. ctx clearRect 0 0 width height)
    (doseq [[[x y] tile] world]
      (gfx/draw-image spritesheet tile x y))
    (gfx/draw-image spritesheet :player (first position) (second position))
    ))


(defn init []
  (let [canvas (. js/document querySelector "canvas")]
    (set! (.-width canvas) width)
    (set! (.-height canvas) height)
    #_(set! (-> canvas .-width .-style) (str width "px"))
    #_(set! (-> canvas .-height .-style) (str height "px"))
    (. js/window setInterval draw 30)
    (.addEventListener js/document "keypress" keypress)
    (set! (.-src spritesheet) "spritesheet.png")
    (set! (.-imageSmoothingEnabled (. canvas getContext "2d")) false)
    (swap! state assoc-in [:init] true)
    (swap! state assoc-in [:spritesheet] spritesheet)
    (swap! state merge (new-game))
    ))

(js/console.log "Loading code")

(when (not (@state :init))
  (do
    (js/console.log "Initial load")
    (init)))
