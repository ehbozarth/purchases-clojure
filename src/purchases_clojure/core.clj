(ns purchases-clojure.core
  (:require [clojure.string :as str]                        ;add clojure.string
            [clojure.walk :as walk])                        ;add clojure.walk
  (:gen-class))

(defn -main [& args]                                        ;beginning of main
  (println "What are you serching for?")                    ;print to console for user
  (let [purchases (slurp "purchases.csv")                   ;beginning of let/slurp purchases
        purchases (str/split-lines purchases)               ;split purchases file into sepereate lines
        purchases (map (fn [line]                           ;make a map through the function [line]
                         (str/split line #","))             ;Spllit the strings by the columns using "," mapping over purchases
                       purchases)
        header (first purchases)                            ;the first line in purchases is now the header
        purchases (rest purchases)                          ;the rest of the purchase lines inside purchaes.csv
        purchases (map (fn [line]
                         (interleave header line))          ;make the header the keys in  hash-map
                       purchases)
        purchases (map (fn [line]
                         (apply hash-map line))             ;set the value pairs to the header keys in hash-map
                       purchases)
        purchases (walk/keywordize-keys purchases)          ;set the strings in purchaes to match clojure keywords
        input (read-line)                                   ;reading user input from console
        purchases (filter (fn [line]
                            (= input (:category line)))     ;the user input will cause a sort by category function
                          purchases)
        ]
    (spit (format "filtered_%s.edn" input)    M                      ;spit/print out filtered results to "filtered_purchaes.edn"
          (pr-str purchases))
    )                                                       ;end of let
  )                                                         ;end of main
