(ns clansi)

(def ANSI-CODES {
:reset "[0m"
:bright "[1m"
:faint "[2m"
:blink-slow "[5m"
:italics "[3m" 
:underline "[4m"
:inverse   "[7m"
:strikethrough "[9m"
:italics-off "[23m"
:underline-off "[24m"
:inverse-off "[27m"
:strikethrough-off "[29m"
:foreground-black "[30m"
:foreground-red "[31m"
:foreground-green "[32m"
:foreground-yellow "[33m"
:foreground-blue "[34m"
:foreground-magenta "[35m"
:foreground-cyan "[36m"
:foreground-white "[37m"
:foreground-default "[39m"
:background-black "[40m"
:background-red "[41m"
:background-green "[42m"
:background-yellow "[43m"
:background-blue "[44m"
:background-magenta "[45m"
:background-cyan "[46m"
:background-white "[47m"
:background-default "[49m"})

(defn ansi [code]
  (str \u001b (get ANSI-CODES code)))

(defn style [s & codes]
  (str (apply str (map ansi codes)) s (ansi :reset)))

;(style "foo" :red :b-black)

(defn style-test-page []
  (doall
    (map #(println (style (name %) %)) (sort-by name (keys ANSI-CODES))))
  nil)

(defn print-color-doc [v]
  (println (style "-------------------------" :foreground-blue))
  (println (style (str (ns-name (:ns (meta v))) "/" (:name (meta v))) :blink-slow :foreground-black))
  (println (style (:arglists (meta v)) :foreground-cyan))
  (when (:macro (meta v))
    (println (style "Macro" :strikethrough)))
  (println " " (style (:doc (meta v)) :foreground-green)))

(defmacro color-doc [v]
  `(binding [print-doc print-color-doc]
     (doc ~v)))
