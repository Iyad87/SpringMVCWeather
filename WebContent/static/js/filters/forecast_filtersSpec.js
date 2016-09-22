/**
 * 
 */
'use strict';
 
describe('Filter: dateFromJackson', function() {
	  var dateFromJacksonFilter;

	  // create filter to be tested
	  beforeEach(module('myApp'));
	  
	  // factory provider finds this via "name" + "Filter" (_dateFromJacksonFilter_)
	  beforeEach(inject(function(_dateFromJacksonFilter_) {
		  dateFromJacksonFilter = _dateFromJacksonFilter_;
	  }));
	  
	  it('returns JavaScript Date object', function() {
			var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0]; 
			var result = dateFromJacksonFilter(dateTimeInJson);
			expect(result instanceof Date).toBe(true);
	  });
	  
	  it('returns JavaScript Date with month one less than Jackson formatted month value', function() {
		  	// Jackson returns month as 1 (January)
			var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0]; 
			var result = dateFromJacksonFilter(dateTimeInJson);
			expect(result.getFullYear()).toBe(2016);
			expect(result.getMonth()).toBe(0); // January is 0
			expect(result.getDate()).toBe(16);
	  });

});

describe('Filter: temperatureFormat', function() {
	  var temperatureFormatFilter;
	  beforeEach(module('myApp'));
	  beforeEach(inject(function(_temperatureFormatFilter_) {
		  temperatureFormatFilter = _temperatureFormatFilter_;
	  }));

		it('formatsTemperatures as High / Low', function() {
			var result = temperatureFormatFilter(58, 80);
			expect(result).toEqual("80 / 58");
		});

		it('formatsTemperatures as High / Low with rounded temps', function() {
			var result = temperatureFormatFilter(58.88, 81.33);
			expect(result).toEqual("81 / 59");
		});
		
		it('returns empty / string with non numeric argument values', function() {
			var result = temperatureFormatFilter("test", "blah");
			expect(result).toEqual(" / ");
		});

});

describe('Filter: forecastHeader', function() {
	  var forecastHeaderFilter;
	  beforeEach(module('myApp'));
	  beforeEach(inject(function(_forecastHeaderFilter_) {
		  forecastHeaderFilter = _forecastHeaderFilter_;
	  }));

	  it('returns text with date time as string', function() {
		  var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0];
		  var result = forecastHeaderFilter("Header Text", dateTimeInJson);
		  expect(result).toEqual("Header Text January 16 2016 12:30 PM");
		});

});