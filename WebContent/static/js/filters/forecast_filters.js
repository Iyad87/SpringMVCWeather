/**
 * 
 */
'use strict';
 
angular.module('myApp').filter("dateFromJackson", function() {
	return function(dateValueAsArray) {
		if (!dateValueAsArray) {
    		return "";
    	}
    	// Year,Month,Date,Minutes,Seconds
    	var currMonth = dateValueAsArray[1];
    	var dateOut = new Date(dateValueAsArray[0], currMonth -1,dateValueAsArray[2],dateValueAsArray[3],
    			dateValueAsArray[4],0);
    	return dateOut;
	};
});

angular.module('myApp').filter("temperatureFormat", ['$filter', function($filter) {
	return function(lowTemp, highTemp) {
    	var formattedLowTemp = $filter('number')(lowTemp,0);
    	var formattedHighTemp = $filter('number')(highTemp,0);
    	return formattedHighTemp + ' / ' + formattedLowTemp;
	};
}]);

angular.module('myApp').filter("forecastHeader", ['$filter', function($filter) {
	return function(headerText, dateTimeInJson) {
    	var theDate = $filter('dateFromJackson')(dateTimeInJson);
    	var formattedTime = $filter('date')(theDate, 'MMMM dd yyyy h:mm a');
    	return headerText + " " + formattedTime;
	};
}]);
