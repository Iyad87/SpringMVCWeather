SpringMVCWeather 
==================

The SpringMVCWeather is a simple Spring MVC application which returns the weather forecast for a given city/state combination. I created this sample application to help me remember building SpringMVC applications and how to use Eclipse again since it's been 4 years!

Basically the project has REST endpoints for retrieving the current forecast. It will return both JSON and HTML depending on the content-type request (applicaiton/json, text/html).

Notes:
* I'm refreshing my SpringMVC memory so I may not be using all the latest bells/whistles from Spring.
* This built using Eclipse Neon on Ubuntu. It was NOT tested on Windows since I don't have a windows laptop anymore.
* I'm MAVEN novice (I used ANT 4+ years ago) so I may have some things incorrect there.
* The HTML used is simple Angular using bootstrap layout stuff.
* To perform city/state to latitude/longitude lookup I integrated with the Google Map REST API
* To perform all forecast lookups I integrated with the Forecast.io weather REST API


## Getting Started

* Get all source from this git repo
* **Java 8** - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* **Eclipse** - https://eclipse.org/


### Dependencies
The Java dependencies should be wired using the Maven POM.xml file.
* **Spring MVC** - I used the boxed Intellij version 4
* **Jackson** - Needed this for the JSON serialization
* **Google Maps API** - Need to obtain an API Key for access to the map api. https://developers.google.com/maps/
* **Forecast.io API** - Need to obtain an API key for access to the ForecastIO REST API. (http://forecast.io)

To run the unit tests for the Angular JS UI you need to install the following:
* **nodejs** - Need to get NodeJS/Node Package Manager to run the angular unit tests
* **karma** - Need to get karma and install it (see below)

* `npm install -g karma --save-dev` (had to install globally to work)
* `npm install karma-jasmine jasmine-core --save-dev`
* `npm install angular-mocks --save-dev`
* `npm install jasmine-core --save`
* `npm install jasmine-core --save`
* `npm install karma-chrome-launcher --save-dev`
* `npm install karma-cli`
* `npm install angular --save`


### Configuring the Project

You need to load the pom.xml and create a MAVEN run configuration with "clean install".

Create a new app.properties file in the src/main/resources directory with the correct properties

* **forecastio.api.key=YOUR_KEY_GOES_HERE** - forecast.io api key for all REST requests
* **map.api.key=YOUR_GOOGLE_KEY_GOES_HERE** - 


### Running the Project

Build whole project and deploy to tomcat instance (or Jetty).
Open browser and hit a simple endpoint
http://localhost:8080/SpringMVCWeather/weather/ - main page retrieves hard coded location for now
http://localhost:8080/SpringMVCWeather/weather/forecast/city,state - returns JSON for current forecast for given city,state

## Testing

There are unit tests at each layer (Java, Spring MVC, Angular JS)

To run the angular unit tests you need to run `./node_modules/.bin/karma start`


## Deployment

N/A


## TODO Stuff

* Spring code based setup (not XML)
* Fix the separation of tests/src code (src/main/java, test/java) (and resources)
* Error handling - Need to add in a custom exception handler
* Push to Heroku - Need to install this in the cloud on Heroku at some point too.

## Getting Help

### Documentation

* Nothing outside this Readme
