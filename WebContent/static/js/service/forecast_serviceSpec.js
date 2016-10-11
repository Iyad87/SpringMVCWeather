/**
 * 
 */
'use strict';
 
describe('ForecastService', function() {

	var forecastService, httpBackend, baseServiceURL;
	
	beforeEach(module('myApp'));
	
	// basically trying to mock out 'ForecastService' dependency inside controller
	beforeEach(inject(function(_ForecastService_,$httpBackend, _baseServiceURL_) {
	    forecastService = _ForecastService_;
	    httpBackend = $httpBackend;
	    baseServiceURL = _baseServiceURL_;
	}));
	
	// tests start here..
	it('creates the service correctly', function() {
		expect(forecastService).toBeDefined();
	});
	
	describe('fetchForecastForLocation', function() {
		var HTTPSERVICENDPOINT;
		
		beforeEach(function() {
			HTTPSERVICENDPOINT = baseServiceURL + "/weather/forecast/";
		});
		
		it("calls HTTP endpoint and returns promise data on success", function() {
			httpBackend.whenGET(HTTPSERVICENDPOINT + 'Phila,PA').respond({
				data: { hello: "world" } // junk stub data
			});
			
			var thePromise = forecastService.fetchForecastForLocation("Phila,PA");
			expect(thePromise).toBeDefined();
			thePromise.then(function(results) {
				expect(results.data).toBeDefined();
				expect(results.data.hello).toEqual("world"); // ensure http data returned
			});
			httpBackend.flush();
		});

		it("calls HTTP endpoint and returns promise error string on failure", function() {
			spyOn(console, 'error'); // mock out console.error messages
			httpBackend.whenGET(HTTPSERVICENDPOINT + 'Phila,PA').respond(401, "somefailure");
			
			var thePromise = forecastService.fetchForecastForLocation("Phila,PA");
			expect(thePromise).toBeDefined();
			thePromise.catch(function(results) {
				expect(results).toBeDefined();
				expect(results.data).toEqual("somefailure");
			});
			httpBackend.flush();
			expect(console.error).toHaveBeenCalled(); // verify error was written
		});

	});
	
	
});