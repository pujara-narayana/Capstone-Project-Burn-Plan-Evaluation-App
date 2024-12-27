package edu.unl.cse.soft160.group07.burnplan.evaluator;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import static edu.unl.cse.soft160.group07.burnplan.evaluator.WeatherApi.isUnfavorable;


public class BurnplanEvaluationAlgorithm {
    private static String MIDMORNING = "10:00";
    private static String AFTERNOON = "16:00";
    private static String MIDDAY = "12:00";


   /*private static Date setDatePart(Date date, int unit, int adjust){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(unit, adjust);
        return cal.getTime();
    }*/

    private static Date adjustDatePart(Date date, int unit, int adjust){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, adjust);
        return cal.getTime();
    }

    private static BurnDetermination determineCommon(BurnPlan burnPlan, boolean isColdFront, double precipitationChance, double resultingRainAmount, boolean unfavorable, boolean prohibited){
        if (burnPlan.getWindSpeed() > 20 && burnPlan.getHumidity() < 20 && burnPlan.getTemperature() > 80 && isColdFront && unfavorable){
            return BurnDetermination.BURNING_PROHIBITED;
        }else if (prohibited){
            return BurnDetermination.BURNING_PROHIBITED;
        }

        if (burnPlan.getFuelType() == FuelType.HEAVY && precipitationChance > 50 && resultingRainAmount > 10){
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (isColdFront) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        if (!burnPlan.getStartTime().after(adjustDatePart(new Date(), Calendar.DAY_OF_YEAR, 2)) || !burnPlan.getStartTime().before(adjustDatePart(new Date(), Calendar.DAY_OF_YEAR, 5))){
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }

        return BurnDetermination.ACCEPTABLE;
    }

    private static BurnDetermination evaluateBlackline(BlacklineBurnPlan burnPlan, boolean isColdFront, double precipitationChance, double resultingRainAmount, boolean unfavorable, boolean prohibited, boolean hasSupplies) {
        int desiredCount = 0;
        int acceptableCount = 0;

        BurnDetermination commonPassed = determineCommon(burnPlan, isColdFront, precipitationChance, resultingRainAmount, unfavorable, prohibited);
        if (commonPassed != BurnDetermination.ACCEPTABLE){
            return commonPassed;
        }

        if (burnPlan.getTemperature() >= 40 && burnPlan.getTemperature() <= 60) {
            desiredCount++;
        } else if (burnPlan.getTemperature() >= 35 && burnPlan.getTemperature() <= 65) {
            acceptableCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_TEMPERATURE;
        }

        if (burnPlan.getHumidity() >= 40 && burnPlan.getHumidity() <= 60) {
            desiredCount++;
        } else if (burnPlan.getHumidity() >= 30 && burnPlan.getHumidity() <= 65) {
            acceptableCount++;
        }

        if (burnPlan.getFuelCondition() == FuelCondition.NON_VOLATILE && burnPlan.getBurnWidth() == 100) {
            desiredCount++;
        } else if (burnPlan.getFuelCondition() == FuelCondition.VOLATILE && burnPlan.getBurnWidth() == 500) {
            desiredCount++;
        }

        if (burnPlan.getStartTime().getHours() >= 10 && burnPlan.getStartTime().getHours() <= 16){
            desiredCount++;
        }else{
            System.out.println("Bad time");
        }

        if (burnPlan.getWindSpeed() <= 8){
            desiredCount++;
        }else if (burnPlan.getWindSpeed() <= 10){
            acceptableCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        }

        if (desiredCount == 5 && hasSupplies){
            return  BurnDetermination.DESIRED;
        }else if (desiredCount + acceptableCount == 5 && hasSupplies){
            return BurnDetermination.ACCEPTABLE;
        }else{
            return BurnDetermination.INDETERMINATE;
        }
    }

    private static BurnDetermination evaluateHeadfire(BurnPlan burnPlan, boolean isColdFront, double precipitationChance, double resultingRainAmount, boolean unfavorable, boolean prohibited, boolean hasSupplies) {
        int desiredCount = 0;
        int acceptableCount = 0;

        BurnDetermination commonPassed = determineCommon(burnPlan, isColdFront, precipitationChance, resultingRainAmount, unfavorable, prohibited);
        if (commonPassed != BurnDetermination.ACCEPTABLE){
            return commonPassed;
        }

        if (burnPlan.getTemperature() >= 70 && burnPlan.getTemperature() <= 80) {
            desiredCount++;
        } else if (burnPlan.getTemperature() >= 60 && burnPlan.getTemperature() <= 85) {
            acceptableCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_TEMPERATURE;
        }

        if (burnPlan.getHumidity() >= 25 && burnPlan.getHumidity() <= 40) {
            desiredCount++;
        } else if (burnPlan.getHumidity() >= 20 && burnPlan.getHumidity() <= 45) {
            acceptableCount++;
        }

        if (burnPlan.getStartTime().getHours() >= 12 && burnPlan.getStartTime().getHours() <= 16){
            desiredCount++;
        }

        if (burnPlan.getWindSpeed() >= 8 && burnPlan.getWindSpeed() <= 15){
            desiredCount++;
        }else if (burnPlan.getWindSpeed() >= 5 && burnPlan.getWindSpeed() <= 20){
            acceptableCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        }

        if (burnPlan.getWindDirection() == WindDirection.SOUTHWEST){
            desiredCount++;
        }else if (burnPlan.getWindDirection() == WindDirection.SOUTH || burnPlan.getWindDirection() == WindDirection.WEST){
            acceptableCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        }

        if (desiredCount == 5 && hasSupplies){
            return  BurnDetermination.DESIRED;
        }else if (desiredCount + acceptableCount == 5 && hasSupplies){
            return BurnDetermination.ACCEPTABLE;
        }else{
            return BurnDetermination.INDETERMINATE;
        }
    }

    private static BurnDetermination evaluateBackfire(BurnPlan burnPlan, boolean isColdFront, double precipitationChance, double resultingRainAmount, boolean unfavorable, boolean prohibited, boolean hasSupplies) {
        int desiredCount = 0;
        int acceptableCount = 0;

        BurnDetermination commonPassed = determineCommon(burnPlan, isColdFront, precipitationChance, resultingRainAmount, unfavorable, prohibited);
        if (commonPassed != BurnDetermination.ACCEPTABLE){
            return commonPassed;
        }

        if (burnPlan.getTemperature() <= 80) {
            desiredCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_TEMPERATURE;
        }

        if (burnPlan.getHumidity() >= 20) {
            desiredCount++;
        }

        if (burnPlan.getStartTime().getHours() >= 12 && burnPlan.getStartTime().getHours() <= 16){
            desiredCount++;
        }

        System.out.println(burnPlan.getWindSpeed());
        if (burnPlan.getWindSpeed() < 20){
            desiredCount++;
        }else{
            return BurnDetermination.NOT_RECOMMENDED_WIND;
        }

        if (desiredCount == 4 && hasSupplies){
            return  BurnDetermination.DESIRED;
        }else{
            return BurnDetermination.INDETERMINATE;
        }
    }

    public static BurnDetermination evaluateSupplies(List<Supply> supplyList, int acreage) {
        if (!HasRequiredSupplies.hasRequiredSupplies(supplyList, acreage)) {
            return BurnDetermination.NOT_RECOMMENDED_OTHER;
        }
        return BurnDetermination.ACCEPTABLE;
    }

    public static BurnDetermination evaluate(BurnPlan burnPlan, boolean isColdFront, double precipitationChance, double resultingRainAmount, boolean unfavorable, boolean prohibited, boolean hasSupplies) {
String lat = String.valueOf(burnPlan.getLatitude());
        String lon = String.valueOf(burnPlan.getLongitude());
        Date time = burnPlan.getStartTime();

        // unfavorable = WeatherApi.isUnfavorable(cal.getTime(),lat,lon);

        if (burnPlan.getFireType() == FireType.BLACKLINES){
            return evaluateBlackline((BlacklineBurnPlan) burnPlan, isColdFront, precipitationChance, resultingRainAmount, unfavorable, prohibited, hasSupplies);
        }else if (burnPlan.getFireType() == FireType.BACKFIRES){
            return evaluateBackfire(burnPlan, isColdFront, precipitationChance, resultingRainAmount, unfavorable, prohibited, hasSupplies);
        }else{
            return evaluateHeadfire(burnPlan, isColdFront, precipitationChance, resultingRainAmount, unfavorable, prohibited, hasSupplies);
        }
    }
}