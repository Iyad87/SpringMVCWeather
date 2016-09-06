SpringMVCWeather 
==================

The SpringMVCWeather is a simple Spring MVC application which returns the weather forecast for a given city/state combination. I created this sample application to help me remember building SpringMVC applications and how to use Eclipse again since it's been 4 years!

Basically the project has REST endpoints for retrieving the current forecast. It will return both JSON and HTML depending on the content-type request (applicaiton/json, text/html).

Notes:
* I'm refreshing my SpringMVC memory so I may not be using all the latest bells/whistles from Spring.
* This built using Eclipse Neon on Ubuntu. It was NOT tested on Windows since I don't have a windows laptop anymore.
* I'm MAVEN novice (I used ANT 4+ years ago) so I may have some things incorrect there.
* The HTML generated from JSP is super basic HTML/TABLE. I may extend this to use a Javascript MVC framework at a later date.
* To perform city/state to latitude/longitude lookup I integrated with the Google Map REST API
* To perform all forecast lookups I integrated with the Forecast.io weather REST API


## Getting Started

* Get all source from this git repo
* **Java 8** - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* **Eclipse** - https://eclipse.org/


### Dependencies
The dependencies should be wired using the Maven POM.xml file.
* **Spring MVC** - I used the boxed Intellij version 4
* **Jackson** - Needed this for the JSON serialization
* **Google Maps API** - Need to obtain an API Key for access to the map api. https://developers.google.com/maps/
* **Forecast.io API** - Need to obtain an API key for access to the ForecastIO REST API. (http://forecast.io)


### Configuring the Project

You need to load the pom.xml and create a MAVEN run configuration with "clean install".

Create a new app.properties file in the src/main/resources directory with the correct properties

* **forecastio.api.key=YOUR_KEY_GOES_HERE** - forecast.io api key for all REST requests
* **map.api.key=YOUR_GOOGLE_KEY_GOES_HERE** - 


### Running the Project

Build whole project and deploy to tomcat instance (or Jetty).
Open browser and hit a simple endpoint
http://localhost:8080/SpringMVCWeather/index.jsp - main page does nothing right now
http://localhost:8080/SpringMVCWeather/weather/forecast6 - returns HTML for current weather
http://localhost:8080/SpringMVCWeather/weather/forecast6.json - returns JSON for current forecast

## Testing

I'm adding a set of unit tests for each layer. I'm still wiring up the goop so this is next.

## Deployment

N/A


## TODO Stuff

* Unit Testing - Need to add in proper unit testing
* Error handling - Need to add in a custom exception handler
* UI Framework - Need to integrate with a UI framework (e.g Angular, Backbone, ???)
* Push to Heroku - Need to install this in the cloud on Heroku at some point too.

## Getting Help

### Documentation

* Nothing outside this Readme
