(ns diagf.dynamic
  (:use quil.core)
  (:use clojure.pprint)
  (:use clojure.reflect))

(defn setup []
;  (smooth)
;  (map #(set-pixel % (height) (color (random 255) (random 255) (random 255))) (range 0 (width)) )

;  (def first? (atom true))
  (color-mode :hsb 360 1.0 1.0)
  (frame-rate 10)
  (background 0 0 0))


;(defn seed-bottom-row []
;  (dorun (map #(set-pixel % (- (height) 1) (random-color)) (range 0 (width)))))


(defn random-color []
  (color 4 0.82 (rand 1)))

(defn with-brightness [b]
  (color 4 0.82 b))

(defn xy-to-index [x y width]
  (+ (* y width) x))

(defn index-to-xy [idx width]
  [(mod idx width) (quot idx width)])

(defn setpix [a x y width c]
  (aset a (xy-to-index x y width) c))

(defn neighbors [x y width height]
  (let [y1 (cond (= y (- height 1)) 0
                 :else (+ y 1))
        y2 (cond (= y1 (- height 1)) 0
                 :else (+ y1 1))
        xright (cond (= x (- width 1)) 0
                     :else (+ x 1))
        xleft (cond (= x 0) (- width 1)
                    :else (- x 1))]
    [[xleft y1] [x y1] [xright y1] [x y2]]))


(defn total-brightness-of [colors]
  (reduce + (map brightness colors)))

(defn colors-from-neighbors [a width pixels]
    (map (fn [[x y]] (aget a (xy-to-index x y width))) pixels))

(defn burn [a idx width height]
  (let [[x y] (index-to-xy idx width)
        neighbors (neighbors x y width height)
        nc (colors-from-neighbors a width neighbors)
        nbright (total-brightness-of nc)
        newb (/ nbright 4.2)]
;    (if (>= y (- height 2))
;      (println "x: " x " y: " y " neighbors: " neighbors))
;      (println nc)
;      )
    (setpix a x y width (with-brightness newb))))

(defn draw []
  (stroke (random 255) (random 255) (random 255))
  (stroke-weight (random 10))
  (load-pixels)


  (let [pa (pixels)
        w (width)
        h (height)]
    ; seed bottom row
    (dorun (map #(setpix pa % (- h 1) w (random-color)) (range w)))

;    (println (brightness (aget pa (- (* w h) 1))))
;    (println (colors-from-neighbors pa w [[0 14] [1 14] [2 14]]))
    ; mutate each pixel
    (dorun (map #(burn pa % w h) (range (* w (- h 1))))))

  (update-pixels)

  )

