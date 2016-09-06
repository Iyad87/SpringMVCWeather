package com.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecast {

	private String summary;
	private String icon;
	
	private List<DailyForecastData> data;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<DailyForecastData> getData() {
		return data;
	}

	public void setData(List<DailyForecastData> data) {
		this.data = data;
	}
	
	
	
}
