/**
 * 
 */

'use strict';
 
angular.module('myApp').factory('ForecastService', ['$http', '$q', function($http, $q){

	// TODO: need to fix the hard coded URL (and parameters)
    var REST_SERVICE_URI2 = 'http://localhost:8080/SpringMVCWeather/weather/forecast/';
 
    var factory = {
        fetchForecastForLocation: fetchForecastForLocation
    };

    return factory;

    function fetchForecastForLocation(location) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI2 + location)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Forecast');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


}]);