CLANSI
------

ANSI colorization helper functions for Clojure programs.

You can call `(clansi/style-test-page)` to see what colors and styles are supported
by your terminal.

Wrap strings in ANSI color and style codes like this:

    (style "foo bar" :red)

    (style "foo bar" :underline)

    (style "foo bar" :blue :bg-red :underline)


You can turn the production of ANSI codes on or off by rebinding the
clansi.core/use-ansi at runtime. This allows you to maintain only 
colorized, marked up version of your code, and then turn ANSI on or
off as desired for each output device, execution context, etc.

`(without-ansi)` and `(with-ansi)` convenience macros are provided for
this purpose:

    (defn print-colorized [] 
     (println (style "foo bar" :red)))

    (print-colorized) ;; prints "foo bar" in red
    (without-ansi (print-colorized)) ;; prints plain "foo bar", without any ANSI color codes
    (without-ansi (with-ansi (print-colorized)) (print-colorized)) ;; prints a red "foo bar", then a plaintext "foo bar"


Bonus Features
---------------

* call (clansi/colorize-docs) to make doc colorized
* also supplies the color-doc macro, a stylized version of doc

