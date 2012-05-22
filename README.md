== CLANSI ==

Some ANSI text output helper functions for Clojure terminal programs.

First, call (clansi/style-test-page) to check what colors and styles are supported
by your terminal.

Wrap strings in ANSI color and style codes like this:

    (style "foo bar" :red)

    (style "foo bar" :underline)

    (style "foo bar" :blue :bg-red :underline)


You can turn the use of ANSI codes on or off by rebinding
clansi.core/use-ansi at runtime. This allows you to maintain only one
colorized, marked up version of your code, and then turn ANSI on or
off as desired for each output device:

    (defn print-colorized [] 
     (println (style "foo bar" :red)))

    (print-colorized) ;; prints "foo bar" in red
    (binding [use-ansi false] (print-colorized)) ;; prints plain "foo bar", without any ANSI color codes


=== Bonus Features: ===

* call (clansi/colorize-docs) to make doc colorized
* also supplies the color-doc macro, a stylized version of doc

