# lein-autoreload

This plugin will guarantee that when running the repl, you are always up to date.

## Usage

[![Clojars Project](https://img.shields.io/clojars/v/komcrad/lein-autoreload.svg)](https://clojars.org/komcrad/lein-autoreload)  

Put `[komcrad/lein-autoreload "0.2.0"]` into the `:plugins` vector of your
`:user` profile.

In `:repl-options` you'll want to have some namespace that you won't be changing such as `my-proj.user`  
In this namespace, add a macro like:

```
(ns my-proj.user
  (:gen-class))

(defmacro sandbox []
  `(do
    (require 'clojure.tools.namespace.repl)
    (clojure.tools.namespace.repl/refresh)
    (use 'clojure.repl)))
```

When you start up the repl with `lein repl`, run `(sandbox)`  
Now when you change any files, you'll notice output in the repl like:

    :reloading (my-proj.core)

Or, if you're lazy like me you, can specify `:init` in `:repl-options` in your project.clj so all you have to do is run `lein repl`

```
:repl-options {:init-ns my-proj.user
               :init (my-proj.user/sandbox)})
```

## License

Copyright © 2013 Thomas Rampelberg

Distributed under the MIT license.
