(ns diagf.dynamic-test
  (:use clojure.test
        diagf.dynamic))

(deftest xy-to-index-test
  (testing "200, 100 to index is 32200"
    (is (= (xy-to-index 200 100 320) 32200))))

(deftest index-to-xy-test
  (testing "32200 to xy is 200, 100"
    (is (= (index-to-xy 32200 320) [200 100]))))

(deftest neighbors-test
  (testing "within screen borders returns 3 pixels directly beneath, and 1 pixel 2 rows south"
    (is (= (neighbors 5 5 320 200) [[4, 6] [5, 6] [6, 6] [5, 7]])))
  (testing "on bottom rows, grabs pixels from the top"
           (is (= (neighbors 100 199 320 200) [[99 0] [100 0] [101 0] [100 1]])))
  (testing "on leftmost column, grabs a pixel from the right side"
    (is (= (neighbors 0 100 320 200) [[319 101] [0 101] [1 101] [0 102]])))
  (testing "on rightmost column, grabs a pixel from the left side"
    (is (= (neighbors 319 100 320 200) [[318 101] [319 101] [0 101] [319 102]]))))
