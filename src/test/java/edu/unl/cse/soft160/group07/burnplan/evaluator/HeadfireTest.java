package edu.unl.cse.soft160.group07.burnplan.evaluator;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class HeadfireTest {
    @Test
    public void testDesired(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.DESIRED, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadCommonColdfront(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, BurnplanEvaluationAlgorithm.evaluate(burnPlan, true, 1, 1, false, false, true));
    }

    @Test
    public void testAcceptableTemp(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 69d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testIndeterminate(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(3);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testIndeterminateSupplies(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, false));
    }

    @Test
    public void testIndeterminateSuppliesAcceptable(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 69d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, false));
    }

    @Test
    public void testAcceptableHumid(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 43d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testAcceptableWindSpeed(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(19, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testAcceptableWindDirection(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 43d, WindDirection.SOUTH);
        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testRedFlag(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(21, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 90d, 5d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.BURNING_PROHIBITED, BurnplanEvaluationAlgorithm.evaluate(burnPlan, true, 80, 20, true, false, true));
    }

    @Test
    public void testBadTemp(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 91d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_TEMPERATURE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadWindspeed(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(23, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.SOUTHWEST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_WIND, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadWindDirection(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.HEADFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 71d, 30d, WindDirection.EAST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_WIND, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    private static Date convertLocalDateToDate(LocalDateTime dt){
        Date out = Date.from(
                dt.atZone(ZoneId.systemDefault()).toInstant()
        );
        return out;
    }
}
