package vehiclesExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] input = scanner.nextLine().split(" ");
        Vehicle car = getVehicle(input);

        input = scanner.nextLine().split(" ");
        Vehicle truck = getVehicle(input);

        input = scanner.nextLine().split(" ");
        Vehicle bus = getVehicle(input);

        Map<String, Vehicle> vehicles = new LinkedHashMap<>();
        vehicles.put("Car", car);
        vehicles.put("Truck", truck);
        vehicles.put("Bus", bus);

        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            input = scanner.nextLine().split("\\s+");

            String commandName = input[0];
            String vehicleName = input[1];
            Vehicle vehicle = vehicles.get(vehicleName);
    try {

        switch (commandName) {
            case "Drive":
                double distance = Double.parseDouble(input[2]);
                if (vehicle instanceof Bus) {
                    ((Bus) vehicle).setEmpty(false);
                }
                System.out.println(vehicle.drive(distance));
                break;
            case "Refuel":
                double litres = Double.parseDouble(input[2]);
                vehicles.get(vehicleName).refuel(litres);
                break;
            case "DriveEmpty":
                double emptyBusDistance = Double.parseDouble(input[2]);
                if (vehicle instanceof Bus) {
                    ((Bus) vehicle).setEmpty(true);
                }
                System.out.println(vehicle.drive(emptyBusDistance));
                break;
            default:
                throw new IllegalArgumentException("No such command");
        }
    }catch (IllegalArgumentException ex) {
        System.out.println(ex.getMessage());
    }
        }
        vehicles.values().forEach(System.out::println);
    }

    private static Vehicle getVehicle(String[] input) {
        String vehicleType = input[0];
        double fuelAmount = Double.parseDouble(input[1]);
        double consumption = Double.parseDouble(input[2]);
        double tankCapacity = Double.parseDouble(input[3]);

        Vehicle vehicle = null;

        switch (vehicleType) {
            case "Car":
                vehicle = new Car(fuelAmount, consumption, tankCapacity);
                break;
            case "Truck":
                vehicle = new Truck(fuelAmount, consumption, tankCapacity);
                break;
            case "Bus":
                vehicle = new Bus(fuelAmount, consumption, tankCapacity);
                break;
        }
        return vehicle;
    }
}
