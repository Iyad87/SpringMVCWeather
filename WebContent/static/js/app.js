/**
 * 
 */
'use strict';
 
var App = angular.module('myApp',["ngAnimate"]);
// Defining baseServiceURL as constant (not sure of right pattern here)
//App.constant("baseServiceURL", "http://localhost:8080/SpringMVCWeather");
// TODO: need to figure out how to inject this!!! This is horrible to do here!!
App.constant("baseServiceURL", "https://aqueous-savannah-11822.herokuapp.com/weather");

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