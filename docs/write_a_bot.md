# Hacking a bot
## Getting started
* First read [How to get the webservice documentation](https://github.com/phxql/restwars/blob/master/docs/webservice.md)
* Check the webservice metadata endpoint. This will give you information about buildings, ships and technologies.
* Get an overview of the webservice in Swagger UI.
* Now start up your brain and your development tools and write a bot to rule them all!
  * If you want to create a bot which runs on the JVM, check [this](https://github.com/phxql/restwars/tree/master/rest-client) out.

## Don't waste your time waiting for buildings / ships / technologies
There is a configuration option called `speedUpEverything`. Set it to `true` to speed up everything.

## Debugging your bot
There is a project called [RESTwars UI](https://github.com/phxql/restwars-ui) which provides a minimalistic UI for RESTwars.

## Error reporting
If an error pops up in the RESTwars server, please check the log file at `/tmp/restwars-error.log` and [submit a bug report](https://github.com/phxql/restwars/issues/new).