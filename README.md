SpringMVCWeather 
==================

The SpringMVCWeather is a simple Spring MVC application which returns the weather forecast for a given city/state combination. I created this sample application to show someone I can still build SpringMVC applications and how to use Eclipse again since it's been 4 years!

Basically the project has REST endpoints for retrieving the current forecast. It will return both JSON.

Notes:
* I'm refreshing my SpringMVC memory so I may not be using all the latest bells/whistles from Spring.
* This is built using Eclipse Neon on Ubuntu. It was NOT tested on Windows since I don't have a windows laptop anymore.
* I'm a MAVEN novice (I used ANT 4+ years ago) so I may have some things incorrect there.
* The HTML/JS is simple Angular using bootstrap layouts.
* To perform city/state to latitude/longitude lookup I integrated with the Google Map REST API
* To perform all forecast lookups I integrated with the Forecast.io weather REST API


## Getting Started

* Get all source from this git repo
* **Java 8** - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* **Eclipse** - https://eclipse.org/


### Dependencies
The Java dependencies should be wired using the Maven POM.xml file.
* **Spring MVC** - Spring MVC framework
* **Jackson** - Needed this for the JSON serialization
* **Google Maps API** - Need to obtain an API Key for access to the map api. https://developers.google.com/maps/
* **Forecast.io API** - Need to obtain an API key for access to the ForecastIO REST API. (http://forecast.io)

Running the Angular JS unit tests uses the standard Karma/Jasmine testing stack so you'll need:
* **NodeJS** - NodeJS/Node Package Manager for installing all dependencies (https://nodejs.org/)
* **Karma** - Need to get karma and install it (https://karma-runner.github.io/1.0/index.html)

* `npm install -g karma --save-dev` (had to install Karma globally to work)
* `npm install karma-jasmine jasmine-core --save-dev` (get jasmine unit test framework)
* `npm install angular --save`
* `npm install angular-mocks --save-dev` (for mocking service tier in tests)
* `npm install karma-chrome-launcher --save-dev` (I used chrome as a launcher)
* `npm install karma-cli` (command line tools)


### Configuring the Project

You need to load the pom.xml and create a MAVEN run configuration with "clean install".

Create a new app.properties file in the src/main/resources directory with the correct properties

* **forecastio.api.key=YOUR_KEY_GOES_HERE** - forecast.io api key for all REST requests
* **map.api.key=YOUR_GOOGLE_KEY_GOES_HERE** - 


### Running the Project

Build whole project and deploy to tomcat instance (or Jetty).
Open browser and hit a simple endpoint
http://localhost:8080/SpringMVCWeather/weather/ - main page for UI form/layout
http://localhost:8080/SpringMVCWeather/weather/forecast/city,state - returns JSON for current forecast for given city,state

## Testing

There are unit tests at each layer (Java, Spring MVC, Angular JS)

To run all angular tests:
`./node_modules/.bin/karma start`  - (node_modules means this assumes all node dependencies are installed LOCALLY in project)


## Deployment

N/A


## TODO Stuff

* Spring code based setup (not XML)
* Fix the separation of tests/src code (src/main/java, test/java) (and resources)
* Error handling - Probably should add a custom exception handler in UI
* Push to Heroku - Need to install this in the cloud on Heroku at some point too. I may switch this over to Spring boot at this point

## Getting Help

### Documentation

* Nothing outside this Readme
