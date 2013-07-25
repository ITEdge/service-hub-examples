service-hub-examples
====================

This is simple example for service-hub framework. Here you can create simple example without unnecesary fuss.

Install
=======

You can install this example by cloning service-hub repository and running

```bash
lein sub install
```

on it. Next you need to clone this repository, run

```bash
lein deps
```

and then running lein repl. In repl you 

```clojure
(use 'itedge.service-hub-examples.in-memory.core)
```

to load server.
