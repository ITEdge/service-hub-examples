# Datomic sample

This is an example of simple service-hub REST application with datomic handlers,
HTTP BASIC authentication and validation of REST calls. As a demonstration for
history capable service, the products service can be queried for its history,
such as /products/id/history?param=value (for simple listing /products/id/history/)
and there is also possiblity to get particular snapshot from entity history:
/products/entity-id/history/history-id

## Default data
The application is configured to provide data for 3 entity types, users, roles and products: 

* Schema:

```clojure
[
{:db/id #db/id[:db.part/db]
 :db/ident :user/username
 :db/unique :db.unique/value
 :db/valueType :db.type/string
 :db/cardinality :db.cardinality/one
 :db/fulltext true
 :db/doc "User name"
 :db.install/_attribute :db.part/db}

{:db/id #db/id[:db.part/db]
 :db/ident :user/password
 :db/valueType :db.type/string
 :db/cardinality :db.cardinality/one
 :db/doc "User password"
 :db.install/_attribute :db.part/db}
 
{:db/id #db/id[:db.part/db]
 :db/ident :user/roles
 :db/valueType :db.type/ref
 :db/cardinality :db.cardinality/many
 :db/doc "User roles"
 :db.install/_attribute :db.part/db}
 
{:db/id #db/id[:db.part/db]
 :db/ident :role/rolename
 :db/valueType :db.type/string
 :db/cardinality :db.cardinality/one
 :db/fulltext true
 :db/doc "Role rolename"
 :db.install/_attribute :db.part/db}

{:db/id #db/id[:db.part/db]
 :db/ident :product/name
 :db/valueType :db.type/string
 :db/cardinality :db.cardinality/one
 :db/fulltext true
 :db/doc "Product name"
 :db.install/_attribute :db.part/db}

{:db/id #db/id[:db.part/db]
 :db/ident :product/orderCode
 :db/valueType :db.type/string
 :db/cardinality :db.cardinality/one
 :db/fulltext true
 :db/doc "Product order code"
 :db.install/_attribute :db.part/db}

{:db/id #db/id[:db.part/db]
 :db/ident :product/price
 :db/valueType :db.type/double
 :db/cardinality :db.cardinality/one
 :db/doc "Product price"
 :db.install/_attribute :db.part/db}
]
```

* Data:

```clojure
[
{:db/id #db/id[:db.part/user -1] :user/username "admin" :user/password "$2a$10$K83DHEqRZ7ZCpq0HK49lj.4XW/EvMw4XPQPSrIxWNVPsOhwFk8bHe" :user/roles #{#db/id[:db.part/user -2] #db/id[:db.part/user -3]}}
{:db/id #db/id[:db.part/user -2] :role/rolename "user"}
{:db/id #db/id[:db.part/user -3] :role/rolename "admin"}
{:db/id #db/id[:db.part/user -4] :product/name "Product 1" :product/orderCode "P1" :product/price 12.2}
{:db/id #db/id[:db.part/user -5] :product/name "Product 2" :product/orderCode "P2" :product/price 17.1}
]
```

## Validation

Each REST call is validated by various validator functions, dig into namespace 
```clojure
itedge.service-hub-examples.datomic.validators.clj
```
to see more.

## Authentication

There is a HTTP BASIC authentication configured, so you need to supply user credentials in form of the Authorization header, constructed as

* Authorization: Basic (username:password => Base64 encoded)

For example for the default admin/admin user, the Authorization header is:

* Authorization: Basic YWRtaW46YWrtaW4=

Or when you use curl, then just run:

``` sh
curl -u admin:admin http://localhost:3000/users/
```

## Default port to acces service

The most convient way to run this example is to type lein repl at the project root and then type and submit 
```clojure
(use 'itedge.service-hub-examples.datomic.core)
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
