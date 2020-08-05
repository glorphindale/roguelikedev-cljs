## Devlog

### Day 1 (23.07.2020)

Start by walking through [CLJS tutorial](https://clojurescript.org/guides/quick-start), and adding a simple index.html.
Manipulate canvas with JS interop.

Issues: clj workflow is very unstable, `(require '[parenslike.game :as game] :reload)` constantly hangs.

Peculiarities: reloading index.html results in 5Mb of data to be transferred.

### Day 2 (24.07.2020)

Added box movement - keypress listeners, rendering loop.

Issues: don't understand how reloading code from REPL works - sometimes functions are updated, sometimes not. Hard to understand how to properly interop with JS libraries.


### Day 3 (27.07.2020)

Added sprite rendering.

Issues: is constant ctx retrieval bad? What is a proper architecture - should context be obtained once and used to draw everything then?

### Day 4 (29.07.2020)

Tried to tune simple 'clj --main cljs.main' to work both as an nREPL connection and as a compilation watcher, but failed.

Switched to using [Figwheel](https://figwheel.org/tutorial.html). Now I can issue 'make', wait a bit, and after that saving any cljs file will result in its immediate reload in the browser.

Notes: some things are not reloaded, like keyboard listener (it is set once in 'init').

### Day 5 (30.07.2020)

Attempt to model the world with tiles as records (similar to [Caves of Clojure](https://stevelosh.com/blog/2012/07/caves-of-clojure-03-1/) ), but not much success. Started module breakdown.

### Day 6 (31.07.2020)

Model world as map of [x y] -> tile type, render it.

### Day 7 (03.08.2020)

Generate a walled world, add walls.

Issues: code becomes tangled, too many places rely on each other.

### Day 8 (03.08.2020)

Lost in time. Add movement restrictions, probably.

### Day 9 (05.08.2020)

Rewrite world generation in functional style, added wave propagation algorithm to check for connectedness.

Notes: world generation is much cleaner with iterate instead of loop+recur.

Bugs: Sometimes white border around canvas dispappears.
