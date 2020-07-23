(ns parenslike.game)

(let [width 600
      height 600
      canvas (. js/document querySelector "canvas")]
  (do (set! (.-width canvas) width)
      (set! ( .-height canvas) height)
      (set! (-> canvas .-width .-style) (str width "px"))
      (set! (-> canvas .-height .-style) (str height "px"))
      ))
