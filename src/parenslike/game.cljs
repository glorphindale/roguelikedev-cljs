(ns parenslike.game
  (:require [goog.events :as g-events]
            [parenslike.gfx :as gfx]  
            [parenslike.world :as world]))

(def height 600)
(def width 600)

(defonce state (atom {:init false :position {:x 0 :y 0}}))

(defonce spritesheet
  (js/Image.))

(defn keypress [event]
  (do
    (case (.-key event)
      "w"  (swap! state update-in [:position :y] dec)
      "s"  (swap! state update-in [:position :y] inc)
      "a"  (swap! state update-in [:position :x] dec)
      "d"  (swap! state update-in [:position :x] inc)
      0
      )
))

(defn draw []
  (let [canvas (. js/document querySelector "canvas")
        ctx (. canvas getContext "2d")
        spritesheet (get-in @state [:spritesheet])
        position (get-in @state [:position])
        world (get-in @state [:world])]
    (. ctx clearRect 0 0 width height)
    (doseq [[[x y] tile] world]
      (gfx/draw-image spritesheet tile x y))
    (gfx/draw-image spritesheet :player (:x position) (:y position))
    ))


(defn init []
  (let [canvas (. js/document querySelector "canvas")]
    (do (set! (.-width canvas) width)
        (set! (.-height canvas) height)
        (set! (-> canvas .-width .-style) (str width "px"))
        (set! (-> canvas .-height .-style) (str height "px"))
        (. js/window setInterval draw 15)
        (.addEventListener js/document "keypress" keypress)
        (set! (.-src spritesheet) "spritesheet.png")
        (set! (.-imageSmoothingEnabled (. canvas getContext "2d")) false)
        (swap! state assoc-in [:init] true)
        (swap! state assoc-in [:spritesheet] spritesheet)
        (swap! state assoc-in [:world] (world/gen-world))
        )))

(js/console.log "Loading code")

(when (not (@state :init))
  (do
    (js/console.log "Initial load")
    (init)))
