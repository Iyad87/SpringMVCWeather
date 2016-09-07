package com.weather.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.weather.model.CurrentForecast;
import com.weather.model.ForecastResponse;
import com.weather.model.Location;
import com.weather.model.MapAPIResponse;
import com.weather.service.ForecastRetriever;
import com.weather.service.MapInfoRetriever;

@RestController
@RequestMapping(value = "/weather")
public class WeatherController {

	@Autowired
	private ForecastRetriever forecastRetriever;

	@Autowired
	private MapInfoRetriever mapInfoRetriever;
	
	public MapInfoRetriever getMapInfoRetriever() {
		return mapInfoRetriever;
	}

	public void setMapInfoRetriever(MapInfoRetriever mapInfoRetriever) {
		this.mapInfoRetriever = mapInfoRetriever;
	}

	public ForecastRetriever getForecastRetriever() {
		return forecastRetriever;
	}

	public void setForecastRetriever(ForecastRetriever forecastRetriever) {
		this.forecastRetriever = forecastRetriever;
	}

	@RequestMapping(value = "/forecast1", method= RequestMethod.GET)
	public CurrentForecast getForecast1() {
		CurrentForecast forecast = new CurrentForecast();
		return forecast;
	}

	@RequestMapping(value = "/forecast2", method= RequestMethod.GET)
	public CurrentForecast getForecast2() {
		CurrentForecast forecast = new CurrentForecast();
		forecast.setSummary("test summary");
		forecast.setTemperature(86.6d);
		return forecast;
	}

	@RequestMapping(value = "/forecast3/{forecastId}", method=RequestMethod.GET)
	public CurrentForecast getForecast3(@PathVariable("forecastId") long forecastId) {
		CurrentForecast forecast = new CurrentForecast();
		forecast.setSummary("test summary");
		forecast.setTemperature(86.6d);
		forecast.setDewPoint(forecastId);
		return forecast;
	}

	@RequestMapping(value = "/forecast6", method=RequestMethod.GET, produces="application/json")
	public ForecastResponse getForecast6(HttpServletRequest httpRequest) {
		// hard code Newtown square for now ("39.9869", "-75.4007")
		MapAPIResponse mapResponse = this.getMapInfoRetriever().getMapInfoFor("Newtown Square", "PA");
		Location loc = mapResponse.getGeometry().getLocation();
		System.out.println("Lat is: " + loc.getLatitude() + " Long is: " + loc.getLongitude() + " " 
				+ mapResponse.getPlaceId() + " " + mapResponse.getFormattedAddress());
		ForecastResponse forecastResponse = forecastRetriever.getForcastFor(String.valueOf(loc.getLatitude()),
				String.valueOf(loc.getLongitude()));
	    return forecastResponse;
	}

	@RequestMapping(value = "/forecast6", method=RequestMethod.GET)
	public ModelAndView getForecast6HTML(HttpServletRequest httpRequest) {
		// Should probably use content negotiation strategy here but for now just add HTML response
		ForecastResponse forecastResponse = getForecast6(httpRequest);
		ModelAndView modelAndView = new ModelAndView("forecast1");
		modelAndView.addObject("forecast", forecastResponse.getCurrently());
		modelAndView.addObject("message", "hello from HTML world");
		return modelAndView;
	}

	@RequestMapping(value = "/forecast7/{city},{state}", method=RequestMethod.GET, produces="application/json")
	public ForecastResponse getForecast7(@PathVariable("city") String city,
			@PathVariable("state") String state) {
		MapAPIResponse mapResponse = this.getMapInfoRetriever().getMapInfoFor(city, state);
		Location loc = mapResponse.getGeometry().getLocation();
		System.out.println("Lat is: " + loc.getLatitude() + " Long is: " + loc.getLongitude() + " " 
				+ mapResponse.getPlaceId() + " " + mapResponse.getFormattedAddress());
//		ForecastResponse forecastResponse = forecastRetriever.getForcastFor("hello","world");
		ForecastResponse forecastResponse = forecastRetriever.getForcastFor(String.valueOf(loc.getLatitude()),
				String.valueOf(loc.getLongitude()));
		return forecastResponse;
	}

	
}
