/**
 * 
 */
'use strict';
 
describe('ForecastController', function() {
	// controller and mock scope
	var forecastController, $scope;

	// basically trying to mock out 'ForecastService' dependency inside controller
	beforeEach( function() {
		var mockForecastService = {};
		
		// tell factory to use our mock service for the ForecastService
		module('myApp', function($provide) {
			$provide.value('ForecastService', mockForecastService);
		});

		// whoa what a bunch of ugly setup code!!!
		// injecting mock of service call, this seems wrong to be here
		inject(function($q) {
			// mock out result data
			mockForecastService.data = { longitude: "200", latitude: "100"};
			// mock out function (and promises)
			mockForecastService.fetchForecastForLocation = function(location) {
			      var defer = $q.defer();
			      defer.resolve(this.data);
			      return defer.promise;
			    };
		});
	});
	
	// create the controller with root scope and forecastService (which is previously setup as a mock)
	beforeEach(inject(function($controller, $rootScope, _ForecastService_) {
			// mock out $Scope
			$scope = $rootScope.$new();
			// Create the forecastController
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
		
		it('invokes ForecastService and returns forecast JSON', function() {
			expect(forecastController.forecastResponse.latitude).toBeNull();
			forecastController.submit();
			
			// I need this here because I need to tell the deferred junk they're done (not sure this is super correct)
			$scope.$digest();  
			
			// expect to get back my mocked values
			expect(forecastController.forecastResponse.latitude).toBe("100");
			expect(forecastController.forecastResponse.longitude).toBe("200");
		});
	});
	
});