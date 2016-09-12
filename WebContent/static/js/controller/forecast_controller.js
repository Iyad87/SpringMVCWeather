/**
 * 
 */
'use strict';
 
angular.module('myApp').controller('ForecastController', ['$scope', '$filter', 'ForecastService', function($scope, $filter, ForecastService) {
    var self = this;
    self.forecastResponse = {longitude:null, latitude:null, currently:null, daily:null};
    fetchForecast();
 
    // faster jackson is returning as array of values (not string) so just convert it here
    $scope.formatDate = function(dateValueAsArray){
    	if (!dateValueAsArray) {
    		return null;
    	}
    	// Year,Month,Date,Minutes,Seconds
    	var dateOut = new Date(dateValueAsArray[0], dateValueAsArray[1],dateValueAsArray[2],dateValueAsArray[3],
    			dateValueAsArray[4],0);
    	return dateOut;
    };

    // did this to play around with filters in JS code
    $scope.formatHighTemp = function(dailyForecast){
    	var theDate = $scope.formatDate(dailyForecast.temperatureMaxTime);
    	var formattedTime = $filter('date')(theDate, 'h:mm a');
    	return dailyForecast.temperatureMax + '/' + formattedTime;
    };

    $scope.formatLowTemp = function(dailyForecast){
    	var theDate = $scope.formatDate(dailyForecast.temperatureMinTime);
    	var formattedTime = $filter('date')(theDate, 'h:mm a');
    	return dailyForecast.temperatureMin + '/' + formattedTime;
    };

    $scope.formatHeader = function(headerText, dateTimeInJson) {
    	var theDate = $scope.formatDate(dateTimeInJson);
    	var formattedTime = $filter('date')(theDate, 'MMMM dd yyyy h:mm a');
    	return headerText + formattedTime;
    };
    
    function fetchForecast(){
        ForecastService.fetchForecast()
            .then(
            function(d) {
                self.forecastResponse = d;
            },
            function(errResponse){
                console.error('Error while fetching Forecasts');
            }
        );
    }
    
}]);