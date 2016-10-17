SpringMVCWeather 
==================

The SpringMVCWeather is a simple Spring MVC application which returns the weather forecast for a given city/state combination. I created this sample application to show someone I can still build SpringMVC applications and how to use Eclipse again since it's been 4 years!

Basically the project has REST endpoints for retrieving the current forecast.

Notes:
* I'm refreshing my SpringMVC memory so I may not be using all the latest bells/whistles from Spring (e.g. Spring boot).
* This is built using Eclipse Neon on Ubuntu. It was NOT tested on Windows since I don't have a windows laptop anymore.
* I'm a MAVEN novice (I used ANT 4+ years ago) so I may have some things incorrect there.
* The HTML/JS is simple Angular using bootstrap layouts.
* To perform city/state to latitude/longitude lookup I integrated with the Google Map REST API
* To perform all forecast lookups I integrated with the darkSky (https://darksky.net/dev/) weather REST API
* The project is deployed to a heroku free instance and has some minor deployment issues to be worked out. More info is in the deployment/configuration sections.

## Getting Started

* Get all source from this git repo
* **Java 8** - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* **Eclipse** - https://eclipse.org/


### Dependencies
The Java dependencies should be wired using the Maven POM.xml file.
* **Spring MVC** - Spring MVC framework
* **Jackson** - Needed this for the JSON serialization
* **Google Maps API** - Need to obtain an API Key for access to the map api. https://developers.google.com/maps/
* **Darksky API** - Need to obtain an API key for access to the Darksky REST API. (https://darksky.net/dev/)

Running the Angular JS unit tests uses the standard Karma/Jasmine testing stack so you'll need:
* **NodeJS** - NodeJS/Node Package Manager for installing all dependencies (https://nodejs.org/)
* **Karma** - Need to get karma and install it (https://karma-runner.github.io/1.0/index.html)

* `npm install -g karma --save-dev` (had to install Karma globally to work)
* `npm install karma-jasmine jasmine-core --save-dev` (get jasmine unit test framework)
* `npm install karma-ng-html2js-preprocessor --save-dev` (needed for Angular directive testing)
* `npm install angular --save`
* `npm install angular-mocks --save-dev` (for mocking service tier in tests)
* `npm install karma-chrome-launcher --save-dev` (I used chrome as a launcher)
* `npm install karma-cli` (command line tools)


### Configuring the Project

You need to load the pom.xml and create a MAVEN run configuration with "clean install".

There are two environment variables required these can be setup using heroku config:set commands

* **DARKSKY_API_KEY=your dark sky key here** - Darksky api key for all REST requests  
* **MAP_API_KEY=your google map api key here** - Google map API key 


### Running the Project

I run the project using two different approaches: inside Eclipse IDE or heroku command line.

To run in Eclipse IDE, build whole project and deploy to tomcat instance (or Jetty)
Open browser and hit a simple endpoint

* http://localhost:8080/SpringMVCWeather/weather/ - main page for UI form/layout
* http://localhost:8080/SpringMVCWeather/weather/forecast/city,state - returns JSON for current forecast for given city,state

To run on local heroku command line tools

* mvn clean install (create the WAR)
* heroku local web (deploy locally)
* http://localhost:5000/weather (endpoint for the application)

## Testing

There are unit tests at each layer (Java, Spring MVC, Angular JS) these are run by simple Junit test runners.

To run all angular tests:

`./node_modules/.bin/karma start`  - (node_modules means this assumes all node dependencies are installed LOCALLY in project)


## Deployment

The project is currently deployed to a free instance of Heroku using normal Heroku java deployment (NOT spring boot!). You need to ensure the two API keys are configured in the configuration environment.

Also, the Angular app.js contains a global for the domain name (matching the Heroku domain name given during the Heroku create command). This will differ if it's deployed to a different cloud instance. At some point I'll figure out how to 
abstract this correctly.


## TODO Stuff

* Fix the angular domain name issue in app.js
* Spring code based setup (not XML) (probably migrate over to spring boot!)
* Add some logging!
* Fix the separation of tests/src code (src/main/java, test/java) (and resources)
* Error handling - Probably should add a custom exception handler in UI

## Getting Help

### Documentation

* Nothing outside this Readme
