# lein-autoreload

This plugin will guarantee that when running the repl, you are always up to date.

## Usage

[![Clojars Project](https://img.shields.io/clojars/v/komcrad/lein-autoreload.svg)](https://clojars.org/komcrad/lein-autoreload)  

Put `[komcrad/lein-autoreload "0.1.2"]` into the `:plugins` vector of your
`:user` profile.

When you start up the repl with `lein repl` and change any files, you'll notice output in the repl like:

    :reloading (my-proj.core)

## License

Copyright © 2013 Thomas Rampelberg

Distributed under the MIT license.
