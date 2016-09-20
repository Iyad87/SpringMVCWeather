/**
 * 
 */
'use strict';
 
describe('Filter: dateFromJackson', function() {
	  var theFilter;

	  beforeEach(module('myApp'));
	  beforeEach(inject(function(_dateFromJacksonFilter_) {
	    theFilter = _dateFromJacksonFilter_;
	  }));
	  
	  it('returns JavaScript Date object', function() {
			var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0]; 
			var result = theFilter(dateTimeInJson);
			expect(result instanceof Date).toBe(true);
	  });
	  
	  it('returns JavaScript Date with month one less than Jackson formatted month value', function() {
			var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0]; 
			var result = theFilter(dateTimeInJson);
			expect(result.getFullYear()).toBe(2016);
			expect(result.getMonth()).toBe(0);
			expect(result.getDate()).toBe(16);
	  });

});

describe('Filter: temperatureFormat', function() {
	  var theFilter;
	  beforeEach(module('myApp'));
	  beforeEach(inject(function(_temperatureFormatFilter_) {
	    theFilter = _temperatureFormatFilter_;
	  }));

		it('formatsTemperatures as High / Low', function() {
			var result = theFilter(58, 80);
			expect(result).toEqual("80 / 58");
		});

		it('formatsTemperatures as High / Low with rounded temps', function() {
			var result = theFilter(58.88, 81.33);
			expect(result).toEqual("81 / 59");
		});
		
		it('returns empty / string with invalid argument values', function() {
			var result = theFilter("test", "blah");
			expect(result).toEqual(" / ");
		});

});

describe('Filter: forecastHeader', function() {
	  var theFilter;
	  beforeEach(module('myApp'));
	  beforeEach(inject(function(_forecastHeaderFilter_) {
	    theFilter = _forecastHeaderFilter_;
	  }));

	  it('returns text with date time as string', function() {
		  var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0];
		  var result = theFilter("Header Text", dateTimeInJson);
		  expect(result).toEqual("Header Text January 16 2016 12:30 PM");
		});

});