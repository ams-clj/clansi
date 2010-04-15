(ns clansi)

(def ANSI-CODES
  {:reset              "[0m"
   :bright             "[1m"
   :blink-slow         "[5m"
   :underline          "[4m"
   :underline-off      "[24m"
   :inverse            "[7m"
   :inverse-off        "[27m"
   :strikethrough      "[9m"
   :strikethrough-off  "[29m"

   :default "[39m"
   :white   "[37m"
   :black   "[30m"
   :red     "[31m"
   :green   "[32m"
   :blue    "[34m"
   :yellow  "[33m"
   :magenta "[35m"
   :cyan    "[36m"

   :bg-default "[49m"
   :bg-white   "[47m"
   :bg-black   "[40m"
   :bg-red     "[41m"
   :bg-green   "[42m"
   :bg-blue    "[44m"
   :bg-yellow  "[43m"
   :bg-magenta "[45m"
   :bg-cyan    "[46m"
   })

(defn ansi
  "Output an ANSI escape code using a style key.

   (ansi :blue)
   (ansi :underline)

  Note, try (style-test-page) to see all available styles.
  "
  [code]
  (str \u001b (get ANSI-CODES code)))

(defn style
  "Applies ANSI color and style to a text string.

   (style \"foo\" :red)
   (style \"foo\" :red :underline)
   (style \"foo\" :red :bg-blue :underline)
 "
  [s & codes]
  (str (apply str (map ansi codes)) s (ansi :reset)))

(defn style-test-page 
  "Print the list of supported ANSI styles, each style name shown
  with its own style."
  []
  (doall
    (map #(println (style (name %) %)) (sort-by name (keys ANSI-CODES))))
  nil)

(def doc-style* (ref {:line  :blue
                      :title :bright
                      :args  :red
                      :macro :blue
                      :doc   :green}))

(defn print-special-doc-color
  "Print stylized special form documentation."
  [name type anchor]
  (println (style "-------------------------" (:line @doc-style*)))
  (println (style name (:title @doc-style*)))
  (println type)
  (println (style (str "  Please see http://clojure.org/special_forms#" anchor)
                  (:doc @doc-style*))))

(defn print-namespace-doc-color
  "Print stylized documentation for a namespace."
  [nspace]
  (println (style "-------------------------"    (:line @doc-style*)))
  (println (style (str (ns-name nspace))         (:title @doc-style*)))
  (println (style (str " " (:doc (meta nspace))) (:doc @doc-style*))))

(defn print-doc-color 
  "Print stylized function documentation."
  [v]
  (println (style "-------------------------" (:line @doc-style*)))
  (println (style (str (ns-name (:ns (meta v))) "/" (:name (meta v)))
                  (:title @doc-style*)))
  (print "(")
  (doseq [alist (:arglists (meta v))]
   (print "[" (style (apply str (interpose " " alist)) (:args @doc-style*)) "]"))
  (println ")")

  (when (:macro (meta v))
    (println (style "Macro" (:macro @doc-style*))))
  (println "  " (style (:doc (meta v)) (:doc @doc-style*))))

(defmacro color-doc 
  "A stylized version of clojure.core/doc."
  [v]
  `(binding [print-doc print-doc-color
             print-special-doc print-special-doc-color
             print-namespace-doc print-namespace-doc-color]
     (doc ~v)))
