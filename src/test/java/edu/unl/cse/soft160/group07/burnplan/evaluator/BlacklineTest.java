package edu.unl.cse.soft160.group07.burnplan.evaluator;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BlacklineTest {
    @Test
    public void testDesiredNON_VOLATILE(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(6d, 1d, FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE, 1);

        assertEquals(BurnDetermination.DESIRED, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadColdfront(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(6, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE, 1);

        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, BurnplanEvaluationAlgorithm.evaluate(desired, true, 1, 1, false, false, true));
    }

    @Test
    public void testDesiredVolatile(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(6, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 50d, WindDirection.NORTHEAST, 500, FuelCondition.VOLATILE, 1);

        assertEquals(BurnDetermination.DESIRED, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testAcceptable(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(9, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE,9);

        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testAcceptableTemp(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(6, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 36d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE,1 );

        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testAcceptableHumid(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(6, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 31d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE, 1);

        assertEquals(BurnDetermination.ACCEPTABLE, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadTemp(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(9, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 90d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE,1 );

        assertEquals(BurnDetermination.NOT_RECOMMENDED_TEMPERATURE, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testBadWind(){
        LocalDateTime time = LocalDateTime.now().plus(4, ChronoUnit.DAYS).withHour(11);
        BurnPlan desired = new BlacklineBurnPlan(90, 1,  FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE, 11);

        assertEquals(BurnDetermination.NOT_RECOMMENDED_WIND, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testIndeterminate(){
        LocalDateTime time = LocalDateTime.now().plus(3, ChronoUnit.DAYS).withHour(1);
        BurnPlan desired = new BlacklineBurnPlan(7, 1, FireType.BLACKLINES, FuelType.LIGHT, convertLocalDateToDate(time), 50d, 50d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE, 1);

        assertEquals(BurnDetermination.INDETERMINATE, BurnplanEvaluationAlgorithm.evaluate(desired, false, 1, 1, false, false, true));
    }

    @Test
    public void testRedFlag(){
        BurnPlan redFlag = new BlacklineBurnPlan(21, 1, FireType.BACKFIRES, FuelType.LIGHT, convertLocalDateToDate(LocalDateTime.now()), 81d, 10d, WindDirection.NORTHEAST, 100, FuelCondition.NON_VOLATILE, 21);
        assertEquals(BurnDetermination.BURNING_PROHIBITED,BurnplanEvaluationAlgorithm.evaluate(redFlag, true, 20, 20, true, false, true));
    }

    private static Date convertLocalDateToDate(LocalDateTime dt){
        Date out = Date.from(
                dt.atZone(ZoneId.systemDefault()).toInstant()
        );
        return out;
    }
}
