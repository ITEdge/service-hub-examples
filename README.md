# service-hub-examples
This project contains examples for the service-hub framework.

## Install
You run those examples by first cloning service-hub repository and running lein sub install on it, then you can try examples from this repository. 
Preferably you should try in-memory-simple first. Both in-memory-simple and in-memory examples create services with in memory database back-end 
and validation accesible on http://localhost/products using GET, PUT, POST and DELETE http verbs (RESTful interface). The in-memory sample adds
validation with users and roles on the top of that.
