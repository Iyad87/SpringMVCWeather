/**
 * 
 */
'use strict';
 
describe('ForecastController', function() {
	var forecastController, $scope;
	
	// whoa what a bunch of ugly setup code!!!
	beforeEach( function() {
		var mockForecastService = {};
		module('myApp', function($provide) {
			$provide.value('ForecastService', mockForecastService);
		});

		// injecting mock of service call, this seems wrong to be here
		inject(function($q) {
			mockForecastService.data = { longitude: "200", latitude: "100"};
			mockForecastService.fetchForecastForLocation = function(location) {
			      var defer = $q.defer();
			      defer.resolve(this.data);
			      return defer.promise;
			    };
		});
	});
	
	// create the controller
	beforeEach(inject(function($controller, $rootScope, _ForecastService_) {
			$scope = $rootScope.$new();
			forecastController = $controller('ForecastController', {
				$scope: $scope
			});
		}));

	// tests start here..
	it('creates ForecastController correctly', function() {
		expect(forecastController).toBeDefined();
	});
	
	it('defaults searchAddress to Philadelphia,PA', function() { 
		expect(forecastController.searchAddress).toEqual('Philadelphia,PA');
	});
	
	describe('formatTemp', function() {
		it('formatsTemperatures as High / Low with rounded temps', function() {
			var tempJSON = { temperatureMin: 58.88, temperatureMax: 81.33 };
			var result = $scope.formatTemp(tempJSON);
			expect(result).toEqual("81 / 59");
		});
		
		it('returns empty / string with invalid JSON values', function() {
			var tempJSON = { temperatureMin: "test", temperatureMax: "blah" };
			var result = $scope.formatTemp(tempJSON);
			expect(result).toEqual(" / ");
		});

		it('returns undefined / undefined when JSON missing min/max values', function() {
			var tempJSON = { temperature: "test"};
			var result = $scope.formatTemp(tempJSON);
			expect(result).toEqual("undefined / undefined");
		});

	});

	describe('formatTemperatureNumber', function() {
		it('formats temperature by rounding value with no decimal digits', function() {
			var result = $scope.formatTemperatureNumber(58.88);
			expect(result).toEqual("59");
		});
		
		it("returns '' for non numeric values", function() {
			var result = $scope.formatTemperatureNumber("blah");
			expect(result).toEqual("");
		});
	});

	describe('formatHeader', function() {
		it('formatsHeader using date time passed', function() {
			var dateTimeInJson = [ 2016, 0, 16, 12, 30, 0]; 
			var result = $scope.formatHeader("HeaderText", dateTimeInJson);
			expect(result).toEqual("HeaderText January 16 2016 12:30 PM");
		});
	});

	describe('formatDate', function() {
		it('formatsDate with date time passed as array of int values', function() {
			var dateTimeInJson = [ 2016, 0, 16, 12, 30, 0]; 
			var result = $scope.formatDate(dateTimeInJson);
			expect(result instanceof Date).toBe(true);
			expect(result.getFullYear()).toBe(2016);
			expect(result.getMonth()).toBe(0);
			expect(result.getDate()).toBe(16);
		});
		
	});
	
	describe('submit', function() {
		
		it('invokes ForecastService and return JSON', function() {
			expect(forecastController.forecastResponse.latitude).toBeNull();
			forecastController.submit();
			$scope.$digest();  // not sure why I need this here but it's due to all this deferred junk..
			expect(forecastController.forecastResponse.latitude).toBe("100");
			expect(forecastController.forecastResponse.longitude).toBe("200");
		});
	});
	
});