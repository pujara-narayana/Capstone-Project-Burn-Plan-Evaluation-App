package edu.unl.cse.soft160.group07.burnplan.evaluator;

import java.util.List;

public class Supply {
    private SupplyType type;
    private int quantity;
    private double capacity;
    private String unit;
    private int acreage;

    //added list<supply> for main method
    static List<Supply> supplies;

    public Supply(SupplyType type, int quantity, double capacity, String unit) {
        this.type = type;
        this.quantity = quantity;
        this.capacity = capacity;
        this.unit = unit;
    }

    //added list<supply> for main method
    public static List<Supply> getSupplies() {
        return supplies;
    }

    public static void setSupplies(List<Supply> supplyList) {
        supplies = supplyList;
    }

    public SupplyType getType() {
        return type;
    }

    public void setType(SupplyType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAcreage() {
        return acreage;
    }

    public void setAcreage(int acreage) {
        this.acreage = acreage;
    }
}
