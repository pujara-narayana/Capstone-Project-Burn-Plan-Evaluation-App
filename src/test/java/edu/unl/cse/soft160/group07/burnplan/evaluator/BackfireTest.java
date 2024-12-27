package edu.unl.cse.soft160.group07.burnplan.evaluator;

import org.junit.Test;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class BackfireTest {
    @Test
    public void testDesired(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 40d, 50d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.DESIRED, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testIndeterminateHumidity(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 40d, 5d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testIndeterminate(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 40d, 50d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, false));
    }

    @Test
    public void testIndeterminateTimeLow(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(3);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 40d, 50d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, false));
    }

    @Test
    public void testIndeterminateTimeHigh(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(23);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 40d, 50d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, false));
    }

    @Test
    public void testBadTemp(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 90d, 50d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_TEMPERATURE, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadWind(){
        LocalDateTime time = LocalDateTime.now().plus(4, ChronoUnit.DAYS).withHour(0);
        BurnPlan burnPlan = new BurnPlan(21, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 40d, 50d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_WIND, BurnplanEvaluationAlgorithm.evaluate(burnPlan, false, 1, 1, false, false, true));
    }

    @Test
    public void testRedFlag(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(13);
        BurnPlan burnPlan = new BurnPlan(21, 1, 1, FireType.BACKFIRES, FuelType.HEAVY, convertLocalDateToDate(time), 90d, 5d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.BURNING_PROHIBITED, BurnplanEvaluationAlgorithm.evaluate(burnPlan, true, 80, 20, true, false, true));
    }

    private static Date convertLocalDateToDate(LocalDateTime dt){
        Date out = Date.from(
                dt.atZone(ZoneId.systemDefault()).toInstant()
        );
        return out;
    }
}

