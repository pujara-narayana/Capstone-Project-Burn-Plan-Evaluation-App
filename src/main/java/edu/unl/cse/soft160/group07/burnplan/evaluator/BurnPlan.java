package edu.unl.cse.soft160.group07.burnplan.evaluator;


import edu.unl.cse.soft160.group07.burnplan.evaluator.*;

import java.time.LocalDate;

import java.time.LocalDate;
import edu.unl.cse.soft160.group07.burnplan.evaluator.FuelType;
import edu.unl.cse.soft160.group07.burnplan.evaluator.WindDirection;
import edu.unl.cse.soft160.group07.burnplan.evaluator.FuelType;
import edu.unl.cse.soft160.group07.burnplan.evaluator.WindDirection;
import edu.unl.cse.soft160.json_connections.connection.RestConnection;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static edu.unl.cse.soft160.group07.burnplan.evaluator.Supply.supplies;


public class BurnPlan {
    private double longitude;
    private double latitude;
    private FireType fireType;
    private FuelType fuelType;
    private Date startTime;
    private double temperature;
    private double humidity;
    private WindDirection windDirection;
    private double windSpeed;
    // added list supplies for my main method
    private List<Supply> supplies;
    // added int acreage for my main method
    private int acreage;

    public BurnPlan(Date burnDate, int landSize, FuelType fuelType, FireType fireType, double temperature, double humidity, double windSpeed, WindDirection windDirection) {
        this.startTime = burnDate;
        this.acreage = landSize;
        this.fuelType = fuelType;
        this.fireType = fireType;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }




    public BurnPlan(double windSpeed, double longitude, double latitude, FireType fireType, FuelType fuelType, Date startTime, double temperature, double humidity, WindDirection windDirection) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.fireType = fireType;
        this.fuelType = fuelType;
        this.startTime = startTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }

    public BurnPlan() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public FireType getFireType() {
        return fireType;
    }

    public void setFireType(FireType fireType) {
        this.fireType = fireType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WindDirection windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeed(){
        return windSpeed;
    }

    public void setWindSpeed(double speed){
        windSpeed = speed;
    }

    // added list supplies for my main method
    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;}

    public List<Supply> getSupplies() {return supplies;}

    // added int acreage for my main method

    public void setAcreage(int acreage) {
        this.acreage = acreage;
    }
    // added int acreage for my main method

    public int getAcreage() {
        return acreage;
    }
}

