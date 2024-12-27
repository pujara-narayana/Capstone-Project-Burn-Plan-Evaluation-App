package edu.unl.cse.soft160.group07.burnplan.evaluator;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CommonTest {
    private BurnPlan redFlag = new BurnPlan(21, 1, 1, FireType.BACKFIRES, FuelType.LIGHT, new Date(), 81d, 10d, WindDirection.NORTHEAST);

    @Test
    public void testRedflag(){
        assertEquals(BurnDetermination.BURNING_PROHIBITED,BurnplanEvaluationAlgorithm.evaluate(redFlag, true, 20, 20, true, false, true));
    }

    @Test
    public void testProhibited(){
        redFlag.setWindSpeed(10);
        assertEquals(BurnDetermination.BURNING_PROHIBITED,BurnplanEvaluationAlgorithm.evaluate(redFlag, true, 20, 20, true, true, true));
    }

    @Test
    public void testHeavyHighRain(){
        redFlag.setFuelType(FuelType.HEAVY);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,BurnplanEvaluationAlgorithm.evaluate(redFlag, true, 60, 20, false, false, true));
    }

    @Test
    public void testColdfront(){
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER,BurnplanEvaluationAlgorithm.evaluate(redFlag, true, 1, 1, false, false, true));
    }

    @Test
    public void testBadDate(){
        BurnPlan badDate = new BurnPlan(10, 1, 1, FireType.BACKFIRES, FuelType.LIGHT, new Date(), 60d, 25d, WindDirection.NORTHEAST);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, BurnplanEvaluationAlgorithm.evaluate(badDate, false, 1, 1, false, false, true));
    }

}
