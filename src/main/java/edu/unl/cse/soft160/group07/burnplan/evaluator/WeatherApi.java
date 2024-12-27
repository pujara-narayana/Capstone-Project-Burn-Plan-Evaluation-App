package edu.unl.cse.soft160.group07.burnplan.evaluator;

import edu.unl.cse.soft160.json_connections.connection.RestConnection;
import edu.unl.cse.soft160.json_connections.connector.OpenWeatherConnector;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherApi {
    private static final boolean isTest = false;
    private static final String testFile = "redflag.json";

    private static Date getDateWithTimeZero(Date date){
        Date out;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private static OpenWeatherConnector getConnector(String dataset, String lat, String lon){
        try{
            if (!isTest){
                String apiKey = RestConnection.getApiKey("openweathermap");
                OpenWeatherConnector connector = new OpenWeatherConnector(dataset, apiKey);
                connector.retrieveData("lat=" + lat + "&lon="+ lon+ "&units=imperial");
                return connector;
            }else{
                OpenWeatherConnector con = new OpenWeatherConnector(testFile);
                try{
                    con.retrieveData("weather.json");
                    return con;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static double getRainfallForDay(Date date, String lat, String lon){
        OpenWeatherConnector connector = getConnector("forecast", lat, lon);
        System.out.println("Rain before: " + date);
        double total = 0;
        for (int i= 0; i<8; i++){
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            time.set(Calendar.HOUR_OF_DAY, 3*i);
            time.set(Calendar.MINUTE, 0);
            time.set(Calendar.SECOND, 0);
            total += connector.getThreeHourRainfall(time.getTime());
        }
        return total;
    }

    public static double getWindspeed(Date date, String lat, String lon){
        OpenWeatherConnector connector = getConnector("forecast", lat, lon);
        return connector.getWindSpeed(getDateWithTimeZero(date));
    }

    public static WindDirection getWindDirection(Date date, String lat, String lon){
        OpenWeatherConnector connector = getConnector("forecast", lat, lon);
        Long degrees = connector.getWindDirection(getDateWithTimeZero(date));
        // convert from degrees to cardinal
        if (degrees >= 0 && degrees < 23){
            return WindDirection.NORTH;
        }else if (degrees >= 23 && degrees < 68){
            return WindDirection.NORTHEAST;
        }else if (degrees >= 68 && degrees < 113){
            return WindDirection.EAST;
        }else if (degrees >= 113 && degrees < 158){
            return WindDirection.SOUTHEAST;
        }else if (degrees >= 156 && degrees < 203){
            return WindDirection.SOUTH;
        }else if (degrees >= 203 && degrees < 248){
            return WindDirection.SOUTHWEST;
        }else if (degrees >= 248 && degrees < 293){
            return WindDirection.WEST;
        }else if (degrees >= 293 && degrees < 338){
            return WindDirection.NORTHWEST;
        }else{
            return WindDirection.NORTH;
        }
    }

    public static boolean isUnfavorable(Date time, String lat, String lon){
        OpenWeatherConnector con = getConnector("forecast", lat, lon);
        return con.getProbabilityOfPrecipitation(getDateWithTimeZero(time)) > .5 && getRainfallForDay(time, lat, lon) > 10;
    }

    public static List<OpenWeatherConnector.WeatherCategory> getWeatherCategories(Date time, String lat, String lon){
        OpenWeatherConnector con = getConnector("forecast", lat, lon);
        return con.getWeatherCategories(time);
    }

    public static long getHumidity(Date time, String lat, String lon){
        OpenWeatherConnector con = getConnector("forecast", lat, lon);
        return con.getHumidity(getDateWithTimeZero(time));
    }

    public static double getTemperature(Date time, String lat, String lon){
        OpenWeatherConnector con = getConnector("forecast", lat, lon);
        return con.getTemperature(getDateWithTimeZero(time));
    }

    public static double getWindGust(Date time, String lat, String lon){
        OpenWeatherConnector con = getConnector("forecast", lat, lon);
        return con.getWindGust(getDateWithTimeZero(time));
    }

    public static double getPrecipitationChance(Date time, String lat, String lon){
        OpenWeatherConnector con = getConnector("forecast", lat, lon);
        return con.getProbabilityOfPrecipitation(getDateWithTimeZero(time));
    }
}
