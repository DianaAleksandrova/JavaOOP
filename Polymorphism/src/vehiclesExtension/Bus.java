package vehiclesExtension;

public class Bus extends Vehicle{

    private static final double AIR_CONDITION = 1.4;

    private boolean isEmpty;

    public Bus(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption, tankCapacity);
        this.isEmpty = true;
    }

    public void setEmpty(boolean empty) {
        if(this.isEmpty == empty) {
            return;
        }
        if(this.isEmpty && !empty) {
            super.setFuelConsumption(super.getFuelConsumption() + AIR_CONDITION);
        }
        if(!isEmpty && empty) {
            super.setFuelConsumption(super.getFuelConsumption() - AIR_CONDITION);
        }
        this.isEmpty = empty;
    }
}
