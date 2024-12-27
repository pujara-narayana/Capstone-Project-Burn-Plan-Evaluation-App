import edu.unl.cse.soft160.group07.burnplan.evaluator.*;
import edu.unl.cse.soft160.json_connections.connector.OpenWeatherConnector;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static edu.unl.cse.soft160.group07.burnplan.evaluator.BurnDetermination.NOT_RECOMMENDED_OTHER;

public class BurnPlanEvaluationApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean aplication = true;

        while (aplication) {
            System.out.println("\nPlease choose an option");
            System.out.println("1. Evaluate a BurnPlan");
            System.out.println("2. EXIT the application");
            System.out.println("Please enter 1 or 2 to proceed further");

            int user = scanner.nextInt();
            scanner.nextLine();

            if (user == 1) {
                System.out.println("\n--------- Welcome to Burn Evaluation App!! -----------");
                getData(scanner);
            } else if (user == 2) {
                System.out.println("Thank you for your time! Exiting the application...");
                aplication = false;
            } else {
                System.out.println("INVALID INPUT -> Please enter 1 or 2.");
            }

        }
    }

    private static void getData(Scanner scanner) {

        try {
            System.out.println("Enter the latitude (-90 to 90):");
            double latitude = scanner.nextDouble();

            if (latitude > 90 || latitude < -90) {
                System.out.println("Invalid!! -> enter a value between -90 to 90");
                return;
            }

            System.out.println("Enter the longitude (-180 to 180):");
            double longitude = scanner.nextDouble();
            scanner.nextLine();

            if (longitude > 180 || longitude < -180) {
                System.out.println("Invalid!! -> enter a value between -180 to 180");
                return;
            }

            System.out.println("Enter burn date (yyyy-MM-dd):");
            String userDate = scanner.nextLine();

            SimpleDateFormat userDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            userDateFormat.setLenient(false);

            Date burnDate = userDateFormat.parse(userDate);
            Date currentDate = new Date();

            if (burnDate.before(currentDate) || burnDate.equals(currentDate)) {
                System.out.println("Burn date must be in the future.");
                return;
            }


            System.out.println("Enter the BurnWindowTime (MIDMORNING, MIDDAY, or LATE_AFTERNOON)");
            String burnWindowTime = scanner.nextLine();
            burnWindowTime = burnWindowTime.toUpperCase();

            if (!burnWindowTime.equals("MIDMORNING") && !burnWindowTime.equals("MIDDAY") && !burnWindowTime.equals("LATE_AFTERNOON")) {
                System.out.println("Invalid!! please enter -> MIDMORNING, MIDDAY, or LATE_AFTERNOON");
                return;
            }

            System.out.println("Is there a cold front within 12 hours of the (YES or NO)");
            String front = scanner.nextLine();
            front = front.toUpperCase();

            if (!front.equals("YES") && !front.equals("NO")) {
                System.out.println("Invalid!! please enter -> YES or NO");
                return;
            }
            boolean isColdFront;
            if (front.equals("YES")) {
                isColdFront = true;
                BurnDetermination notRecommendedOther = NOT_RECOMMENDED_OTHER;
                System.out.println("Burn Plan recommendation -> " + notRecommendedOther);
                System.out.println("--------- Burn Plan evaluation completed ----------");
            } else if (front.equals("NO")) {
                isColdFront = false;
                System.out.println("Enter the size to be burned (in acres):");
                int landSize = scanner.nextInt();
                scanner.nextLine();


                List<Supply> supplies = collectSupplies(scanner, landSize);


                System.out.println("Enter the fuel type (LIGHT or HEAVY):");
                String fuelType = scanner.nextLine();
                fuelType = fuelType.toUpperCase();

                if (!fuelType.equals("LIGHT") && !fuelType.equals("HEAVY")) {
                    System.out.println("Invalid!! Please enter -> LIGHT or HEAVY.");
                }

                FuelType burnFuelType = FuelType.valueOf(fuelType);

                System.out.println("Enter the fire type (HEADFIRES, BACKFIRES, or BLACKLINES)");
                String fireType = scanner.nextLine();
                fireType = fireType.toUpperCase();

                if (!fireType.equals("HEADFIRES") && !fireType.equals("BACKFIRES") && !fireType.equals("BLACKLINES")) {
                    System.out.println("Invalid!! Please enter -> HEADFIRES, BACKFIRES, or BLACKLINES");
                }

                FireType burnFireType = FireType.valueOf(fireType);

                FuelCondition fuelCondition = null;
                double burnWidth = 0;
                if (burnFireType == FireType.BLACKLINES) {
                    System.out.println("Enter blackline fuel condition (VOLATILE or NON_VOLATILE):");
                    String fuelConditionStr = scanner.nextLine().toUpperCase();
                    if (!fuelConditionStr.equals("VOLATILE") && !fuelConditionStr.equals("NON_VOLATILE")) {
                        System.out.println("Invalid!! Please enter -> VOLATILE or NON_VOLATILE");
                        return;
                    }
                    fuelCondition = FuelCondition.valueOf(fuelConditionStr);

                    System.out.println("Enter blackline width:");
                    burnWidth = scanner.nextDouble();
                    scanner.nextLine();
                }


                evaluatUserData(latitude, longitude, burnDate, landSize, burnFuelType, burnFireType, burnWindowTime, burnWidth, fuelCondition, isColdFront, supplies);
            }
        } catch (ParseException e) {
            System.out.println("Invalid!! Please use yyyy-MM-dd.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error 404: " + e.getMessage());
        }
    }


    private static List<Supply> collectSupplies(Scanner scanner, int landSize) {
        List<Supply> supplies = new ArrayList<>();

        int requiredPumpers = (int) (landSize / 80.0);
        int requiredFuelGallons = (int) (landSize / 10.0);
        int requiredDripTorches = (int) (requiredFuelGallons / 10.0);
        int requiredSwatters = (int) (landSize / 10.0);

        System.out.println("\nSupply Collection");
        System.out.println("Minimum Required Supplies:");
        System.out.println("Pumpers: " + requiredPumpers);
        System.out.println("Fire Starting Fuel (gallons): " + requiredFuelGallons);
        System.out.println("Drip Torches: " + requiredDripTorches);
        System.out.println("Rakes or Swatters: " + requiredSwatters);
        System.out.println("1 Backpack Pump");
        System.out.println("1 Dozer");

        System.out.println("\nEnter number of Pumpers:");
        int pumperQuantity = scanner.nextInt();
        scanner.nextLine();
        supplies.add(new Supply(SupplyType.PUMPER, pumperQuantity, 1000.0, "gallon"));

        System.out.println("Enter gallons of Fire Starting Fuel:");
        int fuelQuantity = scanner.nextInt();
        scanner.nextLine();
        supplies.add(new Supply(SupplyType.FIRE_STARTING_FUEL, fuelQuantity, 10.0, "gallon"));

        System.out.println("Enter number of Drip Torches:");
        int dripTorchQuantity = scanner.nextInt();
        scanner.nextLine();
        supplies.add(new Supply(SupplyType.DRIP_TORCH, dripTorchQuantity, 10.0, "unit"));

        System.out.println("Enter number of Rakes or Swatters:");
        int swatterQuantity = scanner.nextInt();
        scanner.nextLine();
        supplies.add(new Supply(SupplyType.RAKE_OR_SWATTER, swatterQuantity, 1.0, "unit"));

        System.out.println("Enter number of Backpack Pumps:");
        int backpackPumpQuantity = scanner.nextInt();
        scanner.nextLine();
        supplies.add(new Supply(SupplyType.BACKPACK_PUMP, backpackPumpQuantity, 5.0, "gallon"));

        System.out.println("Enter number of Dozers:");
        int dozerQuantity = scanner.nextInt();
        scanner.nextLine();
        supplies.add(new Supply(SupplyType.DOZER, dozerQuantity, 1.0, "unit"));

        Supply.setSupplies(supplies);

        return supplies;
    }


    private static void evaluatUserData(double latitude, double longitude, Date burnDate,
                                        int landSize, FuelType fuelType, FireType fireType, String burnWindowTime,
                                        double burnWidth, FuelCondition fuelCondition, boolean isColdFront, List<Supply> supplies) {
        System.out.println("\nEvaluating Burn Plan...");
        System.out.println("\n");

        String lat = Double.toString(latitude);
        String lon = Double.toString(longitude);

        double precipitationChance = WeatherApi.getPrecipitationChance(burnDate, lat, lon);

        double rainfall = WeatherApi.getRainfallForDay(burnDate, lat, lon);
        double windSpeed = WeatherApi.getWindspeed(burnDate, lat, lon);
        WindDirection windDirection = WeatherApi.getWindDirection(burnDate, lat, lon);
        double temperature = WeatherApi.getTemperature(burnDate, lat, lon);
        double humidity = WeatherApi.getHumidity(burnDate, lat, lon);
        System.out.println(WeatherApi.getWeatherCategories(burnDate, lat, lon));


        BurnPlan burnPlan;
        if (fireType == FireType.BLACKLINES) {
            burnPlan = new BlacklineBurnPlan((int) burnWidth, fuelCondition);

            burnPlan.setStartTime(burnDate);
            burnPlan.setAcreage((int) landSize);
            burnPlan.setFuelType(fuelType);
            burnPlan.setFireType(fireType);
            burnPlan.setTemperature(temperature);
            burnPlan.setHumidity(humidity);
            burnPlan.setWindSpeed(windSpeed);
            burnPlan.setWindDirection(windDirection);
        } else {
            burnPlan = new BurnPlan(burnDate, landSize, fuelType, fireType,
                    temperature, humidity, windSpeed, windDirection);
        }

        burnPlan.setSupplies(supplies);

        boolean hasSupplies = HasRequiredSupplies.hasRequiredSupplies(burnPlan);


        BurnDetermination evaluation = BurnplanEvaluationAlgorithm.evaluate(burnPlan, isColdFront, precipitationChance,
                rainfall, WeatherApi.isUnfavorable(burnDate, lat, lon), false, hasSupplies);


        generateBurnPlan(burnPlan, evaluation, lat, lon, supplies);


        System.out.println("Location: Latitude " + latitude + ", Longitude " + longitude);
        System.out.println("Burn Date: " + burnDate);
        System.out.println("Burn Window Time: " + burnWindowTime);
        System.out.println("Land Size: " + landSize + " acres");
        System.out.println("Fuel Type: " + fuelType);
        System.out.println("Fire Type: " + fireType);

        System.out.println("---------- Burn Plan evaluation completed ---------");

    }

    private static void generateBurnPlan(BurnPlan burnPlan, BurnDetermination determination,
                                         String lat, String lon, List<Supply> supplies) {

        System.out.println("\n----- Burn Plan Evaluation Report -----");
        System.out.println("Report Generated: " + new Date());
        System.out.println("Proposed Burn Date: " + burnPlan.getStartTime());
        System.out.println("Location: Latitude " + lat + ", Longitude " + lon);
        System.out.println("Land Size: " + burnPlan.getAcreage() + " acres");
        System.out.println("Fuel Type: " + burnPlan.getFuelType());
        System.out.println("Fire Type: " + burnPlan.getFireType());
        System.out.println("Temperature: " + burnPlan.getTemperature() + "°F");
        System.out.println("Humidity: " + burnPlan.getHumidity() + "%");
        System.out.println("Wind Speed: " + burnPlan.getWindSpeed() + " mph");
        System.out.println("Wind Direction: " + burnPlan.getWindDirection());

        System.out.println("\nSupplies:");
        for (Supply supply : supplies) {
            System.out.println(supply.getType() + ": " + supply.getQuantity() +
                    " (Capacity: " + supply.getCapacity() + ")");
        }
        System.out.println("Supplies Meet Requirements: " + HasRequiredSupplies.hasRequiredSupplies(burnPlan));

        System.out.println("\nBurn Plan Determination: " + determination);

        switch (determination) {
            case DESIRED:
                System.out.println("All conditions are Desirable for burning.");
                break;
            case ACCEPTABLE:
                System.out.println("Conditions are Acceptable for burning.");
                break;
            case BURNING_PROHIBITED:
                System.out.println("Red flag conditions or burning ban in effect.");
                break;
            case NOT_RECOMMENDED_TEMPERATURE:
                System.out.println("Temperature conditions are unfavorable.");
                break;
            case NOT_RECOMMENDED_WIND:
                System.out.println("Wind conditions are unfavorable.");
                break;
            case NOT_RECOMMENDED_OTHER:
                System.out.println("Other conditions make burning not recommended.");
                break;
            case INDETERMINATE:
                System.out.println("Insufficient data to make a definitive determination.");
                break;
        }

        System.out.println("\n----- End of Burn Plan Evaluation Report -----");
    }
}

