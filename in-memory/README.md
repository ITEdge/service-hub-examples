# In-memory sample

This is an example of simple service-hub REST application with in-memory handlers,
HTTP BASIC authentication and validation of REST calls.

## Default data
The application is configured to provide data for 3 entity types: 

* Users (GET /users - one default user with username 'admin', password 'admin' and assigned roles: 

```clojure
:user
``` 

and 

```clojure
:admin
```

)
* Roles (GET /roles - two default roles with rolenames 'user' and 'admin')
* Products (GET /products - two default products with product names 'Product-1' and 'Product-2')

## Validation

Each REST call is validated by various validator functions, dig into namespace 
```clojure
itedge.service-hub-examples.in-memory.validators.clj
``` 
to see more.

## Authentication

There is a HTTP BASIC authentication configured, so you need to supply user credentials in form of the Authorization header, constructed as

* Authorization: Basic (username:password => Base64 encoded)

For example for the default admin/admin user, the Authorization header is:

* Authorization: Basic YWRtaW46YWrtaW4=

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
