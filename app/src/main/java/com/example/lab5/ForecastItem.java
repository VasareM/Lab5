package com.example.lab5;

public class ForecastItem {

    private String forecastTimeUtc;
    private double airTemperature;
    private double feelsLikeTemperature;
    private String conditionCode;
    private double windSpeed;

    public ForecastItem(String forecastTimeUtc, double airTemperature, double feelsLikeTemperature,
                        String conditionCode, double windSpeed) {
        this.forecastTimeUtc = forecastTimeUtc;
        this.airTemperature = airTemperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.conditionCode = conditionCode;
        this.windSpeed = windSpeed;
    }

    public String getForecastTimeUtc() { return forecastTimeUtc; }
    public double getAirTemperature() { return airTemperature; }
    public double getFeelsLikeTemperature() { return feelsLikeTemperature; }
    public String getConditionCode() { return conditionCode; }
    public double getWindSpeed() { return windSpeed; }

}