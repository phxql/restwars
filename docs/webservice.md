# Webservice
## Get the webservice documentation
* [Start the RESTwars server](https://github.com/phxql/restwars/blob/master/docs/build_from_source.md)
* `git clone https://github.com/phxql/swagger-ui.git`
* `cd` into `dist` folder from Swagger UI and open the file `index.html` in your browser
* Enter your username and your password in the textfields on the top, then click on the "Explore" button

## Websocket
You can connect with a websocket (or use long polling) to `ws://localhost:8080/websocket/round`. The server
 broadcasts when a new round has been started. Format of the new round broadcast:

```json
{"round": 15}
```