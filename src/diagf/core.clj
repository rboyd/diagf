(ns diagf.core
  (:use quil.core)
  (:require [diagf.dynamic :as dynamic]))

(defsketch example
  :title "Oh so many grey circles"
  :setup dynamic/setup
  :draw dynamic/draw
  :renderer :p2d
  :size [323 200])
