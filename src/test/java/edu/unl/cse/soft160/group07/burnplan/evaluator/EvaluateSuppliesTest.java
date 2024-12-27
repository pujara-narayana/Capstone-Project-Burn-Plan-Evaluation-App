package edu.unl.cse.soft160.group07.burnplan.evaluator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EvaluateSuppliesTest {

    @Test
    public void testSuppliesMeetRequirements() {
        List<Supply> supplyList = new ArrayList<>();
        supplyList.add(new Supply(SupplyType.PUMPER, 2, 500.0, "units"));
        supplyList.add(new Supply(SupplyType.FIRE_STARTING_FUEL, 10, 50.0, "gallons"));
        supplyList.add(new Supply(SupplyType.DRIP_TORCH, 2, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.RAKE_OR_SWATTER, 10, 10.0, "units"));
        supplyList.add(new Supply(SupplyType.BACKPACK_PUMP, 1, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.DOZER, 1, 100.0, "units"));

        int acreage = 80;

        BurnDetermination result = BurnplanEvaluationAlgorithm.evaluateSupplies(supplyList, acreage);
        assertEquals(BurnDetermination.ACCEPTABLE, result);
    }

    @Test
    public void testMissingPumper() {
        List<Supply> supplyList = new ArrayList<>();
        supplyList.add(new Supply(SupplyType.FIRE_STARTING_FUEL, 10, 50.0, "gallons"));
        supplyList.add(new Supply(SupplyType.DRIP_TORCH, 2, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.RAKE_OR_SWATTER, 10, 10.0, "units"));
        supplyList.add(new Supply(SupplyType.BACKPACK_PUMP, 1, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.DOZER, 1, 100.0, "units"));

        int acreage = 80;

        BurnDetermination result = BurnplanEvaluationAlgorithm.evaluateSupplies(supplyList, acreage);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testInsufficientFireStartingFuel() {
        List<Supply> supplyList = new ArrayList<>();
        supplyList.add(new Supply(SupplyType.PUMPER, 2, 500.0, "units"));
        supplyList.add(new Supply(SupplyType.FIRE_STARTING_FUEL, 5, 50.0, "gallons"));
        supplyList.add(new Supply(SupplyType.DRIP_TORCH, 2, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.RAKE_OR_SWATTER, 10, 10.0, "units"));
        supplyList.add(new Supply(SupplyType.BACKPACK_PUMP, 1, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.DOZER, 1, 100.0, "units"));

        int acreage = 80;

        BurnDetermination result = BurnplanEvaluationAlgorithm.evaluateSupplies(supplyList, acreage);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testMissingDripTorch() {
        List<Supply> supplyList = new ArrayList<>();
        supplyList.add(new Supply(SupplyType.PUMPER, 2, 500.0, "units"));
        supplyList.add(new Supply(SupplyType.FIRE_STARTING_FUEL, 10, 50.0, "gallons"));
        supplyList.add(new Supply(SupplyType.RAKE_OR_SWATTER, 10, 10.0, "units"));
        supplyList.add(new Supply(SupplyType.BACKPACK_PUMP, 1, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.DOZER, 1, 100.0, "units"));

        int acreage = 80;

        BurnDetermination result = BurnplanEvaluationAlgorithm.evaluateSupplies(supplyList, acreage);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testMissingBackpackPump() {
        List<Supply> supplyList = new ArrayList<>();
        supplyList.add(new Supply(SupplyType.PUMPER, 2, 500.0, "units"));
        supplyList.add(new Supply(SupplyType.FIRE_STARTING_FUEL, 10, 50.0, "gallons"));
        supplyList.add(new Supply(SupplyType.DRIP_TORCH, 2, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.RAKE_OR_SWATTER, 10, 10.0, "units"));
        supplyList.add(new Supply(SupplyType.DOZER, 1, 100.0, "units"));

        int acreage = 80;

        BurnDetermination result = BurnplanEvaluationAlgorithm.evaluateSupplies(supplyList, acreage);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }

    @Test
    public void testMissingDozer() {
        List<Supply> supplyList = new ArrayList<>();
        supplyList.add(new Supply(SupplyType.PUMPER, 2, 500.0, "units"));
        supplyList.add(new Supply(SupplyType.FIRE_STARTING_FUEL, 10, 50.0, "gallons"));
        supplyList.add(new Supply(SupplyType.DRIP_TORCH, 2, 5.0, "units"));
        supplyList.add(new Supply(SupplyType.RAKE_OR_SWATTER, 10, 10.0, "units"));
        supplyList.add(new Supply(SupplyType.BACKPACK_PUMP, 1, 5.0, "units"));

        int acreage = 80;

        BurnDetermination result = BurnplanEvaluationAlgorithm.evaluateSupplies(supplyList, acreage);
        assertEquals(BurnDetermination.NOT_RECOMMENDED_OTHER, result);
    }
}
