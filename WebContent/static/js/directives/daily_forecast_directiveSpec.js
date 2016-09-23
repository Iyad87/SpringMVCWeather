/**
 * 
 */
'use strict';
 
describe('dailyForecast Directive', function () {
	var $compile, $rootScope, theElement, $scope;
	
	//Require the module our directive is attached to
	beforeEach(module('myApp'));
	
	// Because my directive uses a templateUrl need to have Karma pre-load the HTML templates 
	// into JS (ng-html2js) see the karma.conf.js file for details
	// http://stackoverflow.com/questions/15214760/unit-testing-angularjs-directive-with-templateurl
	beforeEach(module("my.templates"));
	
	//The inject function strips away the underscores to alleviate naming issues
	beforeEach(inject(function (_$compile_, _$rootScope_) {
		$compile = _$compile_;
		$rootScope = _$rootScope_;
	}));

// uncomment if need to peek into templateCache for HTML snippet	
//	beforeEach(inject(function(_$templateCache_) {
//		templateStuff = _$templateCache_;
//	}));
	
	var dateTimeInJson = [ 2016, 1, 16, 12, 30, 0];
	var dailyForecastData = { "time": dateTimeInJson, "summary": "The summary", "temperatureMin": 65, "temperatureMax": 85 };
	var templateHTML = angular.element("<daily-Forecast forecast='dailyForecast'/>");

	// setup the scope with mock data and compile the element
	beforeEach(function() {
		$scope = $rootScope.$new();
		$scope.dailyForecast = dailyForecastData;
		theElement = $compile(templateHTML)($scope);
		$scope.$digest();
	});
	
	it('binds the forecast object into scope correctly', function() {
		var $directiveScope = theElement.scope();
		expect($directiveScope.dailyForecast).toBeDefined();
		expect($directiveScope.dailyForecast.time).toBeDefined();
		expect($directiveScope.dailyForecast.summary).toBeDefined();
		expect($directiveScope.dailyForecast.temperatureMin).toBeDefined();
		expect($directiveScope.dailyForecast.temperatureMax).toBeDefined();
	});

	it('creates thumbnail class in HTML correctly', function() {
		expect(theElement.html()).toContain('class="thumbnail"');
	});

	it('generates correct summary line HTML', function() {
		expect(theElement.html()).toContain('The summary');
	});

	it('generates correct temperature line HTML', function() {
		expect(theElement.html()).toContain('85 / 65');
	});

	it('generates correct date time line HTML', function() {
		expect(theElement.html()).toContain('Sat January 16');
	});

	it("binds the 'forecast' fields correctly on update", function() {
		// initial values were 85, 65
		expect(theElement.html()).toContain('85 / 65');
		// now update the values to 90, 60
		var $directiveScope = theElement.scope();
		$directiveScope.dailyForecast.temperatureMax = 90;
		$directiveScope.dailyForecast.temperatureMin = 60;
		$scope.$digest();
		// html should reflect the changes
		expect(theElement.html()).toContain('90 / 60');
	});

	it ("creates empty element with no 'forecast' scope variable", function() {
		// element has no forecast= 
		var badTemplateHTML = angular.element("<daily-Forecast/>");
		$scope = $rootScope.$new();
		theElement = $compile(templateHTML)($scope);
		$scope.$digest();
		expect(theElement.html()).not.toContain('Sat January 16');
	});
	
});