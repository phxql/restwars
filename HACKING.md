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

## Hacking a bot
### Getting started
* Check the metadata endpoint. This will give you information about buildings, ships and technologies.
* Take a look at the webservice documentation. See below for details on how to get the documentation.
* Now start up your brain and your development tools and write a bot to rule them all!
  * If you want to create a bot which runs on the JVM, check [this](https://github.com/phxql/restwars/tree/master/rest-client) out.

### Get the webservice documentation
* Start the RESTwars server
* `git clone https://github.com/swagger-api/swagger-ui.git`
* `cd` into `swagger-ui/dist` and open the file `index.html` in your browser
* Enter `http://localhost:8080/api-docs` in the textfield on the top, then click on the "Explore" button
  * The webservice endpoints which are protected with basic auth aren't usable yet with the Swagger UI, see https://github.com/swagger-api/swagger-ui/issues/764

