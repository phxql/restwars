# Hacking
## Prerequisites:
* Java 8
* Maven

## Using an IDE
* Open the root `pom.xml` in the IDE of your choice
* Start the main method of `restwars.rest.RestwarsApplication` with the parameters `db migrate config.yaml`. The working directory has to be the `rest` directory
* Start the main method of `restwars.rest.RestwarsApplication` with the parameters `server config.yaml`. The working directory has to be the `rest` directory
* The server is now starting up, you can see the provided endpoints in the console log
* The server is running on `http://localhost:8080`

## Using maven
* Open the directory containing the root `pom.xml` in a terminal
* Run `mvn clean install -P dist`
* `cd` into `rest/target`
* Run `java -jar rest-*.jar db migrate config.yaml`
* Run `java -jar rest-*.jar server config.yaml`
* The server is now starting up, you can see the provided endpoints in the console log
* The server is running on `http://localhost:8080`
