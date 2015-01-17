(ns clojurefx.clojurefx-test
  (:refer-clojure :exclude [compile meta with-meta])
  (:require [clojurefx.factory :as factory]
            [clojurefx.protocols :refer :all])
  (:use midje.sweet
        clojurefx.clojurefx))

(import (javafx.scene.layout VBox)
        (javafx.scene.control ScrollPane Button Label))

;;## Element testing

;;## IdMapper
(def example-graph
  (factory/compile
   [VBox {:id "topBox"
          :children [Button {:id "button"
                             :text "Close"}
                     ScrollPane {:content [Label {:id "label"
                                                  :text "This rocks."}]}]}]))

(facts "Id mapper"
       (fact "Getting a top-level entry"
             (type (get-node-by-id example-graph "topBox")) => javafx.scene.layout.VBox)
       (fact "Getting an entry in an FXParent"
             (type (get-node-by-id example-graph "button")) => javafx.scene.control.Button)
       (fact "Getting an entry in an FXParent and an FXContainer"
             (type (get-node-by-id example-graph "label")) => javafx.scene.control.Label)
       (fact "Fetching the whole id map."
             (map? (get-id-map example-graph)) => true)
       (fact "Fetching label text from id-map."
             (-> (get-id-map example-graph)
                 :label
                 get-value) => "This rocks."))
