package vehicles;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] input = scanner.nextLine().split(" ");
        double carFuelAmount = Double.parseDouble(input[1]);
        double carConsumption = Double.parseDouble(input[2]);
        Vehicle car = new Car(carFuelAmount, carConsumption);

        input = scanner.nextLine().split(" ");
        double truckFuelAmount = Double.parseDouble(input[1]);
        double truckConsumption = Double.parseDouble(input[2]);
        Vehicle truck = new Truck(truckFuelAmount, truckConsumption);

        Map<String, Vehicle> vehicles = new LinkedHashMap<>();
        vehicles.put("Car", car);
        vehicles.put("Truck", truck);

        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            input = scanner.nextLine().split("\\s+");

            String commandName = input[0];
            String vehicleName = input[1];

            switch (commandName) {
                case "Drive":
                    double distance = Double.parseDouble(input[2]);
                    System.out.println(vehicles.get(vehicleName).drive(distance));
                    break;
                case "Refuel":
                    double litres = Double.parseDouble(input[2]);
                    vehicles.get(vehicleName).refuel(litres);
                    break;
            }
        }
        vehicles.values().forEach(System.out::println);
    }
}
