<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>Spring MVC/AngularJS Weather Forecast Example</title>  
    <style>
    </style>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="ForecastController as ctrl">
           <div class="panel panel-default">
              <div class="panel-heading">
              	<span class="lead" ng-bind="formatHeader('Current Forecast For ', ctrl.forecastResponse.currently.time)"/>
              </div>
              <div class="formcontainer">
				<form name="myForm" class="form-horizontal" ng-submit="ctrl.submit()">
                      <div class="row">
                          <div class="form-group">
                              <label class="col-sm-2 control-label" for="summary">City,State</label>
                               <div class="col-sm-8">
  									<input type="text" name="address" class="form-control" ng-model="ctrl.searchAddress" />
                              </div>
                              <div class="col-sm-2">
                              	<input type="submit" id="submit" value="Submit" class="btn btn-default"/>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-group">
                              <label class="col-sm-2 control-label" for="summary">Formatted Address</label>
                               <div class="col-sm-10">
  									<p class="form-control-static" ng-bind="ctrl.forecastResponse.formattedAddress" />
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-group">
                              <label class="col-sm-2 control-label" for="summary">Summary</label>
                               <div class="col-sm-10">
                               <p class="form-control-static" ng-bind="ctrl.forecastResponse.currently.summary" />
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-group">
                              <label class="col-sm-2 control-label" for="temperature">Temperature</label>
                              <div class="col-sm-10">
                              <p class="form-control-static" ng-bind="ctrl.forecastResponse.currently.temperature" />
                              </div>
                          </div>
                      </div>
				</form> 
         </div>
          </div>
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Current Week Forecast</span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                          	  <th>Date</th>
                          	  <th>Summary</th>
                              <th>Temp</th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.forecastResponse.daily.data">
                          	  <td><span ng-bind="formatDate(u.time) |  date:'MMMM dd yyyy'"/></td>
                              <td><span ng-bind="u.summary"/></td>
                              <td><span ng-bind="formatTemp(u)"/></td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
       
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/service/forecast_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/forecast_controller.js' />"></script>
  </body>
</html>