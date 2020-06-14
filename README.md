[![Build Status](https://secure.travis-ci.org/brabenetz/spring-stomp-server.png?branch=master)](http://travis-ci.org/brabenetz/spring-stomp-server)
[![Coverage Status](https://coveralls.io/repos/brabenetz/spring-stomp-server/badge.svg?branch=code-quality)](https://coveralls.io/github/brabenetz/spring-stomp-server?branch=code-quality)
[![Coverity](https://scan.coverity.com/projects/21312/badge.svg)](https://scan.coverity.com/projects/brabenetz-spring-stomp-server)
[![Maven site](https://img.shields.io/badge/Maven-site-blue.svg)](https://brabenetz.github.io/spring-stomp-server/)
[![License: Apache 2.0](https://img.shields.io/badge/license-Apache_2.0-brightgreen.svg)](https://github.com/brabenetz/spring-stomp-server/blob/master/LICENSE.txt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.brabenetz.app/spring-stomp-server/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.brabenetz.lib/spring-stomp-server)
[![Javadocs](http://www.javadoc.io/badge/net.brabenetz.app/spring-stomp-server.svg)](http://www.javadoc.io/doc/net.brabenetz.app/spring-stomp-server)

# Spring Stomp Server

Spring Stomp Server is only a Spring-Boot-Application with a very simple Websocket-Stomp Configurion as described by the Spring Boot Guide: \
https://spring.io/guides/gs/messaging-stomp-websocket/


<!-- MACRO{toc} -->

## Basic Idea

For e2e tests with angular, a Mock-Websocket-Stomp server is needed to also automatically test the Websocket functionality.

The benefit to use Spring Stomp Server as mock websocket endpoint is:

  * It is more realistic if your real enpoint is a spring-boot application
  * Easy to use, even without java knowledge

**"Spring Stomp Server" should never be used on a Production System! It is only designed to be used as simple Mock-Server for automatic tests.**

## Integration into an angular application

the usage can be summerized into two steps:

* download jar from the central Maven Repository
* start the application with "`java -jar`" 

### download jar from the central Maven Repository

The best way is, to use **maven** for downloading the artifact:

package.json:
``` js
  "scripts": {
    ...
    "stomp-server:download": "mvn org.apache.maven.plugins:maven-dependency-plugin:3.0.0:copy -Dartifact=net.brabenetz.app:spring-stomp-server:1.0.0 -DoutputDirectory=./target -Dmdep.stripVersion=true",
    ...
  },
```
(Benefit for maven: maven has a local repository where the artifacts are cached. So download will only be done once for all your projects)

**Alternative** download with **wget**:

package.json:
``` js
  "scripts": {
    ...
    "stomp-server:download": "wget https://repo1.maven.org/maven2/net/brabenetz/app/spring-stomp-server/1.0.0/spring-stomp-server-1.0.0.jar -O ./target/spring-stomp-server.jar",
    ...
  },
```

**TIP:** This step can be add as **post install** script:

package.json:
``` js
  "scripts": {
    "postinstall": "npm run stomp-server:download",
    ...
    "stomp-server:download": "...",
    ...
  },
```
This will execute automatically after "npm install".


### start the application with java -jar

package.json:
``` js
  "scripts": {
    ...
    "stomp-server": "java -jar -Dserver.port=8182 -Dserver.servlet.context-path=/my-backend-app -Dspring-stomp-server.destination-prefixes=/topic,/app,/user -Dspring-stomp-server.websocket-endpoints=/websocket ./target/spring-stomp-server.jar ",
    ...
  },
```
With that, the Websocket endpoint will be ws:/localhost:8182/my-backend-app/websocket \
And the Stomp Config will listen on all destinations with the prefixes "/topic", "/app", "/user".

For local development you can simply start the server in the background by "`npm run stomp-server`".

For e2e tests, you can do it with the npm package 'concurrently':

package.json:
``` js
  "scripts": {
    ...
    "e2e": "concurrently -s first -k --names \"STOMP-SERVER,ANGULAR-CLI\" --prefix \"[{name}]\" \"npm run stomp-server\" \"npm run e2e:ng\"",
    "e2e:ng": "ng e2e",
    ...
  },
```


## More Details

  * Maven Site: https://brabenetz.github.io/spring-stomp-server/archiv/latest/index.html
