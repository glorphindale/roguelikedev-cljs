(ns parenslike.gfx)

(def tile-size 64)

(def sprite-mapping
  {
   :player 0

   :floor 2
   :wall 3
   })

(defn draw-image [spritesheet sprite x y]
  (let [canvas (. js/document querySelector "canvas")
        ctx (. canvas getContext "2d")]
    (. ctx drawImage
       spritesheet
       (* 16 (sprite-mapping sprite))
       0
       16
       16
       (* tile-size x)
       (* tile-size y)
       tile-size
       tile-size)))
