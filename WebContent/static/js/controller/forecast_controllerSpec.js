/**
 * 
 */
'use strict';
 
describe('ForecastController', function() {
	// controller and mock scope
	var forecastController, $scope, $q;
	var mockForecastService = {};
	
	// basically trying to mock out 'ForecastService' dependency inside controller
	beforeEach( function() {
		
		// tell factory to use our mock service for the ForecastService
		module('myApp', function($provide) {
			$provide.value('ForecastService', mockForecastService);
		});
	});
	
	// create the controller with root scope and forecastService (which is previously setup as a mock)
	beforeEach(inject(function($controller, $rootScope, _ForecastService_, _$q_) {
			// mock out $Scope, $q
			$scope = $rootScope.$new();
			$q = _$q_;
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
		
		it('invokes ForecastService and returns forecast JSON on success', function() {
			// mock out result data, function (and promise result)
			mockForecastService.data = { longitude: "200", latitude: "100"};
			mockForecastService.fetchForecastForLocation = function(location) {
			      var defer = $q.defer();
			      defer.resolve(this.data);
			      return defer.promise;
			    };

			expect(forecastController.forecastResponse.latitude).toBeNull(); // Default value in controller
			forecastController.submit();
			
			// I need this here because I need to tell the deferred junk they're done (not sure this is super correct)
			$scope.$digest();  
			
			// expect to get back my mocked values
			expect(forecastController.forecastResponse.latitude).toBe("100");
			expect(forecastController.forecastResponse.longitude).toBe("200");
			expect(forecastController.errorMessage).toBeNull();
		});
		
		it('invokes ForecastService and writes log on error', function() {
			mockForecastService.fetchForecastForLocation = function(location) {
			      var defer = $q.defer();
			      defer.reject("mocking error here");
			      return defer.promise;
			    };
			
			expect(forecastController.errorMessage).toBeNull();
			forecastController.submit();
			
			// I need this here because I need to tell the deferred junk they're done (not sure this is super correct)
			$scope.$digest();  
			
			// expect original values not to be changed
			expect(forecastController.forecastResponse.latitude).toBeNull();
			expect(forecastController.forecastResponse.longitude).toBeNull();
			expect(forecastController.errorMessage).toBe("mocking error here");
		});

	});
	
});