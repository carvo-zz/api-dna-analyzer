# DNA Analysis API

## Assumptions
* DNA is a square matrix of chars

## Requirements
* Java 8

## Running

### Local
#### In default port (8080)
In %PROJECT_ROOT% path run ```mvnw appengine:devserver```

* For custom port use ```mvnw appengine:devserver -Dappengine.port=XXXX```
* To avoid tests use ```mvnw appengine:devserver -DskipTests```

## API doc
### Local
Access
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
after running. **Important: if you change the port use the one you choose.**

### GoogleCloud
Access