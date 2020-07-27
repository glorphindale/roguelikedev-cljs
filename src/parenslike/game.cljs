(ns parenslike.game
  (:require [goog.events :as g-events]))

(def height 600)
(def width 600)
(def tile-size 64)

(def sprites
  {:player 0
   })

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

(defn draw-image [sprite x y]
  (let [canvas (. js/document querySelector "canvas")
        ctx (. canvas getContext "2d")]
    (. ctx drawImage
       spritesheet
       (* 16 (sprites sprite))
       0
       16
       16
       (* tile-size x)
       (* tile-size y)
       tile-size
       tile-size)))

(defn draw []
  (let [canvas (. js/document querySelector "canvas")
        ctx (. canvas getContext "2d")
        position (get-in @state [:position])]
    (. ctx clearRect 0 0 width height)
    (draw-image :player (:x position) (:y position))))


(defn init []
  (let [canvas (. js/document querySelector "canvas")]
    (do (set! (.-width canvas) width)
        (set! (.-height canvas) height)
        (set! (-> canvas .-width .-style) (str width "px"))
        (set! (-> canvas .-height .-style) (str height "px"))
        (. js/window setInterval draw 15)
        (.addEventListener js/document "keypress" keypress)
        (swap! state assoc-in [:init] true)
        (set! (.-src spritesheet) "spritesheet.png")
        (set! (.-imageSmoothingEnabled (. canvas getContext "2d")) false)
        )))

(js/console.log "Loading code")

(when (not (@state :init))
  (do
    (js/console.log "Initial load")
    (init)))
