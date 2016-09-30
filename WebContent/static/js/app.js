/**
 * 
 */
'use strict';
 
var App = angular.module('myApp',["ngAnimate"]);

if (location.host.includes("localhost")) {
	// need to fix the maven deploy stuff from eclipse to be consistent with command line..
//	App.constant("baseServiceURL", "http://localhost:8080/SpringMVCWeather");
	App.constant("baseServiceURL", "http://localhost:5000");
	console.log("localhost baseServiceURL is: " + "http://localhost:5000");
} else {
	// heroku doesn't need to have URL hard wired
	App.constant("baseServiceURL", "");
	console.log("using default baseServiceURL: " + location.host);
}

// This is the heroku domain name
//App.constant("baseServiceURL", "https://aqueous-savannah-11822.herokuapp.com");

/***
 * Remove these comments to enable template cache debugging
 * I needed this to figure out how to get my unit tests for template wit providerURL working
 * http://stackoverflow.com/questions/31326614/how-do-i-list-everything-in-the-template-cache
App.config(['$provide', function($provide) {

    // monkey-patches $templateCache to have a keys() method
    $provide.decorator('$templateCache', [
        '$delegate', function($delegate) {

            var keys = [], origPut = $delegate.put;

            $delegate.put = function(key, value) {
                origPut(key, value);
                keys.push(key);
            };

            // we would need cache.peek() to get all keys from $templateCache, but this features was never
            // integrated into Angular: https://github.com/angular/angular.js/pull/3760
            // please note: this is not feature complete, removing templates is NOT considered
            $delegate.getKeys = function() {
                return keys;
            };

            return $delegate;
        }
    ]);
}]);
 */