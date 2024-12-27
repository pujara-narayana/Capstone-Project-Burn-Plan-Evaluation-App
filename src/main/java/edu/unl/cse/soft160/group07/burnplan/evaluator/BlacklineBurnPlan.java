package edu.unl.cse.soft160.group07.burnplan.evaluator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class BlacklineBurnPlan extends BurnPlan{
    private int burnWidth;
    private FuelCondition fuelCondition;

    public BlacklineBurnPlan(int burnWidth, FuelCondition fuelCondition) {
        this.burnWidth = burnWidth;
        this.fuelCondition = fuelCondition;
    }

    // added this constructor for my main method
    public BlacklineBurnPlan(double longitude, double latitude, FireType fireType, FuelType fuelType, Date startTime, double temperature, double humidity, WindDirection windDirection, int burnWidth, FuelCondition fuelCondition, double windspeed) {
        super(windspeed, longitude, latitude, fireType, fuelType, startTime, temperature, humidity, windDirection);
        this.burnWidth = burnWidth;
        this.fuelCondition = fuelCondition;
    }

    public int getBurnWidth() {
        return burnWidth;
    }

    public void setBurnWidth(int burnWidth) {
        this.burnWidth = burnWidth;
    }

    public FuelCondition getFuelCondition() {
        return fuelCondition;
    }

    public void setFuelCondition(FuelCondition fuelCondition) {
        this.fuelCondition = fuelCondition;
    }
}
