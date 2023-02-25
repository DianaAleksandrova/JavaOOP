package vehicles;

public class Truck extends Vehicle{

    private static final double AIR_CONDITION = 1.6;

    public Truck(double fuelQuantity, double fuelConsumption) {
        super(fuelQuantity, fuelConsumption);
    }

    @Override
    public void setFuelConsumption(double fuelConsumption) {
        super.setFuelConsumption(fuelConsumption + AIR_CONDITION);
    }

    @Override
    public void refuel(double liters) {
       super.refuel(liters * 0.95);
    }
}
