/**
 * 
 */

'use strict';
 
angular.module('myApp').factory('ForecastService', ['$http', '$q', "baseServiceURL", function($http, $q, baseServiceURL){

	// define service endpoint
    var REST_SERVICE_URI = baseServiceURL + '/weather/forecast/';
 
    var factory = {
        fetchForecastForLocation: fetchForecastForLocation
    };

    return factory;

    function fetchForecastForLocation(location) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI + location)
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