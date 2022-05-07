package restaurant.core;

import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.interfaces.Fresh;
import restaurant.entities.drinks.interfaces.Smoothie;
import restaurant.entities.healthyFoods.interfaces.Food;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.Salad;
import restaurant.entities.healthyFoods.interfaces.VeganBiscuits;
import restaurant.entities.tables.interfaces.InGarden;
import restaurant.entities.tables.interfaces.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import java.util.*;

import static restaurant.common.ExceptionMessages.*;
import static restaurant.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    private final HealthFoodRepository<HealthyFood> healthFoodRepository;
    private final BeverageRepository<Beverages> beverageRepository;
    private final TableRepository<Table> tableRepository;
    private double totalMoney;


    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository,
                          BeverageRepository<Beverages> beverageRepository,
                          TableRepository<Table> tableRepository) {
       this.healthFoodRepository = healthFoodRepository;
       this.beverageRepository = beverageRepository;
       this.tableRepository = tableRepository;

    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood food = null;
        if (type.equals("Salad")) {
            food = new Salad(name,price);
        } else if (type.equals("VeganBiscuits")) {
            food = new VeganBiscuits(name,price);
        }
        healthFoodRepository.add(food);

       if (healthFoodRepository.getClass().getSimpleName().contains(name)) {
           throw new IllegalArgumentException(String.format(FOOD_EXIST,name));
       }

        return String.format(FOOD_ADDED,name);
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverages = null;
        if (type.equals("Fresh")) {
            beverages = new Fresh(name,counter,brand);
        }else if (type.equals("Smoothie")) {
            beverages = new Smoothie(name,counter,brand);
        }
        beverageRepository.add(beverages);
        if (beverageRepository.getClass().getSimpleName().contains(name)) {
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST,name));
        }
        return String.format(BEVERAGE_ADDED,type,brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table tables = null;
        if (type.equals("Indoors")) {
            tables = new Indoors(tableNumber,capacity);
        }else if (type.equals("InGarden")) {
            tables = new InGarden(tableNumber,capacity);
        }

        Table tableExist = tableRepository.byNumber(tableNumber);
        if (tableExist == null) {
            tableRepository.add(tables);
            return String.format(TABLE_EXIST,tableNumber);
        }
            throw new IllegalArgumentException(String.format(TABLE_IS_ALREADY_ADDED,tableNumber));

    }

    @Override
    public String reserve(int numberOfPeople) {
        Collection<Table> tables = tableRepository.getAllEntities();
        Table table = tables.stream().filter(t -> !t.isReservedTable() && t.getSize() >= numberOfPeople)
                .findFirst().orElse(null);

        String messages = String.format(RESERVATION_NOT_POSSIBLE,numberOfPeople);
        if (table != null) {
            table.reserve(numberOfPeople);
            messages = String.format(TABLE_RESERVED,table.getTableNumber(),numberOfPeople);
        }
        return messages;
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        Table table = tableRepository.byNumber(tableNumber);
        HealthyFood healthyFood = healthFoodRepository.foodByName(healthyFoodName);

        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER,tableNumber);
        }
        if (healthyFood == null) {
            return String.format(NONE_EXISTENT_FOOD,healthyFoodName);
        }
        table.orderHealthy(healthyFood);

        return String.format(FOOD_ORDER_SUCCESSFUL,healthyFoodName,tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table table = tableRepository.byNumber(tableNumber);
        Beverages beverages = beverageRepository.beverageByName(name, brand);

        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER,tableNumber);
        }
        if (beverages == null) {
            return String.format(NON_EXISTENT_DRINK,name,brand);
        }
        table.orderBeverages(beverages);
        return String.format(BEVERAGE_ORDER_SUCCESSFUL,name,tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        Table table = tableRepository.byNumber(tableNumber);
        double bill = table.bill();
        table.clear();

        totalMoney += bill;

        return String.format(BILL,tableNumber,bill);
    }


    @Override
    public String totalMoney() {

        return String.format(TOTAL_MONEY,totalMoney);
    }
}
