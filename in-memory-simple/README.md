# In-memory-simple sample

This is an example of simple service-hub REST application with in-memory handlers and validation of REST calls.

## Default data
The application is configured to provide data for 1 entity type: 

* Products (GET /products - two default products with product names 'Product-1' and 'Product-2')

## Validation

Each REST call is validated by various validator functions, examine the product-validator in namespace 
```clojure
itedge.service-hub-examples.in-memory.core.clj
``` 

## Default port to acces service

The most convient way to run this example is to type lein repl at the project root and then type and submit 
```clojure
(use 'itedge.service-hub-examples.in-memory.core)
``` 
- this will start the development server at http://localhost:3000,
to stop the server, type and submit 

```clojure
(.stop server)
```

, to start it again type and submit 

```clojure
(.start server)
```

.
