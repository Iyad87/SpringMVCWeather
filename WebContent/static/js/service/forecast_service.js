/**
 * 
 */

'use strict';
 
angular.module('myApp').factory('ForecastService', ['$http', '$q', function($http, $q){
 
	// TODO: need to fix the hard coded URL (and parameters)
    var REST_SERVICE_URI = 'http://localhost:8080/SpringMVCWeather/weather/forecast/NewtownSquare,PA';
 
    var factory = {
        fetchForecast: fetchForecast
    };
 
    return factory;
 
    function fetchForecast() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
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