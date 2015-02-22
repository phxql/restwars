# Build RESTwars from source and get it running
## Prerequisites
* Git
* Java 8
* Maven

## Build
```
git clone https://github.com/phxql/restwars.git
cd restwars/
mvn clean package -P dist
```

Copy `rest-*.jar`, `config.yaml` and the `lib` folder from `rest/target/` to a directory of your choice.

## Start the server
* Migrate the database: `java -jar rest-*.jar db migrate config.yaml` (Only necessary on first start)
* Start the server: `java -jar rest-*.jar server config.yaml`
* Check if the server is running: [http://localhost:8080/v1/system/ping](http://localhost:8080/v1/system/ping)
