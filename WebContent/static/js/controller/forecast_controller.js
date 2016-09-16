/**
 * 
 */
'use strict';
 
angular.module('myApp').controller('ForecastController', ['$scope', 'ForecastService', '$filter', function($scope, ForecastService, $filter) {
    var self = this;
    self.forecastResponse = {longitude:null, latitude:null, currently:null, daily:null};
    self.submit = submit;
    self.searchAddress = "Philadelphia,PA";

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
    $scope.formatTemp = function(dailyForecast){
    	var formattedMinTemp = $scope.formatTemperatureNumber(dailyForecast.temperatureMin);
    	var formattedMaxTemp = $scope.formatTemperatureNumber(dailyForecast.temperatureMax);
    	return formattedMaxTemp + ' / ' + formattedMinTemp;
    };

    $scope.formatTemperatureNumber = function(temperatureNumber) {
    	var formattedTemperature = $filter('number')(temperatureNumber,0);
    	return formattedTemperature;
    };
    
    $scope.formatHeader = function(headerText, dateTimeInJson) {
    	var theDate = $scope.formatDate(dateTimeInJson);
    	var formattedTime = $filter('date')(theDate, 'MMMM dd yyyy h:mm a');
    	return headerText + " " + formattedTime;
    };

    function submit() {
    	if (self.searchAddress) {
    		fetchForecastForLocation();
    	}
    }

    function fetchForecastForLocation(){
    	ForecastService.fetchForecastForLocation(self.searchAddress)
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