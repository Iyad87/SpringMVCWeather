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