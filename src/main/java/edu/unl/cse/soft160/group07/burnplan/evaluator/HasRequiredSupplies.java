package edu.unl.cse.soft160.group07.burnplan.evaluator;

import java.util.List;

public class HasRequiredSupplies {
    public static boolean hasRequiredSupplies(List<Supply> supplyList, int acreage) {

        int requiredPumpers = acreage / 80 + 1;
        int requiredFuelGallons = acreage / 10;
        int requiredDripTorches = requiredFuelGallons / 10;
        int requiredSwatters = acreage / 10;

        boolean hasPumper = false;
        boolean hasFuel = false;
        boolean hasDripTorch = false;
        boolean hasSwatter = false;
        boolean hasBackpackPump = false;
        boolean hasDozer = false;

        for (Supply supply : supplyList) {
            switch (supply.getType()) {
                case PUMPER:
                    if (supply.getQuantity() >= requiredPumpers) hasPumper = true;
                    break;
                case FIRE_STARTING_FUEL:
                    if (supply.getQuantity() >= requiredFuelGallons) hasFuel = true;
                    break;
                case DRIP_TORCH:
                    if (supply.getQuantity() >= requiredDripTorches) hasDripTorch = true;
                    break;
                case RAKE_OR_SWATTER:
                    if (supply.getQuantity() >= requiredSwatters) hasSwatter = true;
                    break;
                case BACKPACK_PUMP:
                    hasBackpackPump = true;
                    break;
                case DOZER:
                    hasDozer = true;
                    break;
            }
        }
        return hasPumper && hasFuel && hasDripTorch && hasSwatter && hasBackpackPump && hasDozer;
    }


    // added this constructor for my main method
    public static boolean hasRequiredSupplies(BurnPlan burnPlan) {
        return hasRequiredSupplies(burnPlan.getSupplies(), burnPlan.getAcreage());
    }
}