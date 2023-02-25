package vehicles;

public class Car extends Vehicle{

    private static final double AIR_CONDITION = 0.9;

    public Car(double fuelQuantity, double fuelConsumption) {
        super(fuelQuantity, fuelConsumption);
    }

    @Override
    public void setFuelConsumption(double fuelConsumption) {
        super.setFuelConsumption(fuelConsumption + AIR_CONDITION);
    }
}
