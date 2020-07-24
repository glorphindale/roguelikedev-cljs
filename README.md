## Devlog

### Day 1 (23.07.2020)

Start by walking through [CLJS tutorial](https://clojurescript.org/guides/quick-start), and adding a simple index.html.
Manipulate canvas with JS interop.

Issues: clj workflow is very unstable, `(require '[parenslike.game :as game] :reload)` constantly hangs.

Peculiarities: reloading index.html results in 5Mb of data to be transferred.

### Day 2 (24.07.2020)

Added box movement - keypress listeners, rendering loop.

Issues: don't understand how reloading code from REPL works - sometimes functions are updated, sometimes not. Hard to understand how to properly interop with JS libraries.
