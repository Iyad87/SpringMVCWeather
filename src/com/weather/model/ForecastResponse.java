package com.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {

	private int longitude;
	private int latitude;
	private String timezone;
	private int offset;
	private CurrentForecast currently;
	private DailyForecast daily;
	
	private String formattedAddress;
	private String searchAddress;
	
	public String getSearchAddress() {
		return searchAddress;
	}
	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
	public CurrentForecast getCurrently() {
		return currently;
	}
	public void setCurrently(CurrentForecast currently) {
		this.currently = currently;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timeZone) {
		this.timezone = timeZone;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public DailyForecast getDaily() {
		return daily;
	}
	public void setDaily(DailyForecast daily) {
		this.daily = daily;
	}
	
	
}
