// Karma configuration
// Generated on Fri Sep 16 2016 10:24:17 GMT-0400 (EDT)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],

    // list of files / patterns to load in the browser
    files: [ "./node_modules/angular/angular.js",
             "./node_modules/angular-animate/angular-animate.js",
             "./node_modules/angular-mocks/angular-mocks.js",
             "./WebContent/static/js/directives/*.html",
             "./WebContent/static/js/**/*.js",
             "./WebContent/static/js/**/*Spec.js"
    ],

    // list of files to exclude
    exclude: [
    ],

    plugins : [
               'karma-chrome-launcher',
               'karma-jasmine',
               'karma-ng-html2js-preprocessor',
               'karma-mocha-reporter'
             ],

    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    	"./WebContent/static/js/directives/*.html": ["ng-html2js"]
    },

    ngHtml2JsPreprocessor: {
        // strip off WebContent prefix and prepend /SpringMVCWeather/
        stripPrefix: "WebContent/",
        prependPrefix: "/",
        // Create module to load in unit tests
        moduleName: "my.templates"
    },
             
    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['mocha'],

    // web server port
    port: 9876,

    // enable / disable colors in the output (reporters and logs)
    colors: true,

    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: false,

    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],

    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: true,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
