package bakery.core;

import bakery.core.interfaces.Controller;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.bakedFoods.interfaces.Bread;
import bakery.entities.bakedFoods.interfaces.Cake;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.drinks.interfaces.Tea;
import bakery.entities.drinks.interfaces.Water;
import bakery.entities.tables.interfaces.InsideTable;
import bakery.entities.tables.interfaces.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.*;

import java.util.Collection;

import static bakery.common.ExceptionMessages.FOOD_OR_DRINK_EXIST;
import static bakery.common.ExceptionMessages.TABLE_EXIST;
import static bakery.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepository<BakedFood> foodRepository;
    private DrinkRepository<Drink> drinkRepository;
    private TableRepository<Table> tableRepository;
    private double totalMoney;


    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository,
                          TableRepository<Table> tableRepository) {
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.tableRepository = tableRepository;
    }


    @Override
    public String addFood(String type, String name, double price) {
        BakedFood bakedFood = foodRepository.getByName(name);
        if (bakedFood != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST,type,name));
        }
        BakedFood food = null;
        switch (type) {
            case "Bread":
                food = new Bread(name,price);
                break;
            case "Cake":
                food = new Cake(name,price);
           break;
        }
        foodRepository.add(food);
        return String.format(FOOD_ADDED,name,type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {
        Drink byNameAndBrand = drinkRepository.getByNameAndBrand(name, brand);
        if (byNameAndBrand != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST,type,name));
        }
        Drink drink = null;
        switch (type) {
            case "Tea":
                drink = new Tea(name,portion,brand);
                break;
            case "Water":
                drink = new Water(name,portion,brand);
                break;
        }
        drinkRepository.add(drink);
        return String.format(DRINK_ADDED,name,brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table number = tableRepository.getByNumber(tableNumber);
        if (number != null) {
            throw new IllegalArgumentException(String.format(TABLE_EXIST,tableNumber));
        }
        Table table = null;
        switch (type) {
            case "InsideTable":
                table = new InsideTable(tableNumber,capacity);
                break;
            case "OutsideTable":
                table = new OutsideTable(tableNumber,capacity);
                break;
        }
        tableRepository.add(table);
        return String.format(TABLE_ADDED,tableNumber);
    }

    @Override
    public String reserveTable(int numberOfPeople) {
        Collection<Table> tables = tableRepository.getAll();
        Table table = tables.stream().filter(t -> !t.isReserved() && t.getCapacity() >= numberOfPeople).findFirst().orElse(null);

        if (table == null) {
            return String.format(RESERVATION_NOT_POSSIBLE,numberOfPeople);
        }
        table.reserve(numberOfPeople);
        return String.format(TABLE_RESERVED,table.getTableNumber(),numberOfPeople);
    }

    @Override
    public String orderFood(int tableNumber, String foodName) {
        Table table = tableRepository.getByNumber(tableNumber);
        BakedFood food = foodRepository.getByName(foodName);
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER,tableNumber);
        }
        if (food == null) {
            return String.format(NONE_EXISTENT_FOOD,foodName);
        }
        table.orderFood(food);
        return String.format(FOOD_ORDER_SUCCESSFUL,tableNumber,foodName);
    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {
        Table table = tableRepository.getByNumber(tableNumber);
        Drink drink = drinkRepository.getByNameAndBrand(drinkName, drinkBrand);
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER,tableNumber);
        }
        if (drink == null) {
            return String.format(NON_EXISTENT_DRINK,drinkName,drinkBrand);
        }
        table.orderDrink(drink);
        return String.format(DRINK_ORDER_SUCCESSFUL,tableNumber,drinkName,drinkBrand);

    }

    @Override
    public String leaveTable(int tableNumber) {
        Table table = tableRepository.getByNumber(tableNumber);
        double bill = table.getBill();
        totalMoney += bill;
        table.clear();

        return String.format(BILL,tableNumber,bill);
    }

    @Override
    public String getFreeTablesInfo() {
        StringBuilder builder = new StringBuilder();

        tableRepository.getAll().stream().filter(t -> !t.isReserved()).forEach(t ->
                builder.append(t.getFreeTableInfo()));

        return builder.toString().trim();
    }

    @Override
    public String getTotalIncome() {
        return String.format(TOTAL_INCOME,totalMoney);
    }
}
