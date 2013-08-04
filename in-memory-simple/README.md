This is an example of basic service-hub application. 

In handlers namespace it creates product-handler, which defines in-memory data structure. In this case its vector of maps.

In services validators namespace it defines Product validator. It ensures that all of the operations on data structures are allowed.

Services namespace defines ProductService, defining all necessary methods. They correspond to GET, DELETE, PUT, POST, and simple query based on criteria.

Finally, running lein repl and

```clojure
(use 'itedge.service-hub-examples.in-memory-simple.core)
```

creates server which can be accessed on http://localhost:3000 .
