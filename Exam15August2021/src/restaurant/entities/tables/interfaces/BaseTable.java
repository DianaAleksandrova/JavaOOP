package restaurant.entities.tables.interfaces;

import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;

import java.util.ArrayList;
import java.util.List;

import static restaurant.common.ExceptionMessages.*;

public abstract class BaseTable implements Table{
    private List<HealthyFood> healthyFood;
    private List<Beverages> beverages;
    private int number;
    private int size;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReservedTable;
    private double allPeople;

    protected BaseTable(int number, int size, double pricePerPerson) {
        this.setNumber(number);
        this.setSize(size);
        this.pricePerPerson = pricePerPerson;
        this.healthyFood = new ArrayList<>();
        this.beverages = new ArrayList<>();
    }

    private void setNumber(int number) {
        this.number = number;
    }

    private void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException(INVALID_TABLE_SIZE);
        }
        this.size = size;
    }


    @Override
    public int getTableNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int numberOfPeople() {
        return numberOfPeople;
    }

    @Override
    public double pricePerPerson() {
        return pricePerPerson;
    }

    @Override
    public boolean isReservedTable() {
        return isReservedTable;
    }

    @Override
    public double allPeople() {
        return numberOfPeople * pricePerPerson;
    }

    @Override
    public void reserve(int numberOfPeople) {
        if (number <= 0) {
            throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;
        this.isReservedTable = true;
    }

    @Override
    public void orderHealthy(HealthyFood food) {
            healthyFood.add(food);

    }
    @Override
    public void orderBeverages(Beverages beverages) {
            this.beverages.add(beverages);
    }

    @Override
    public double bill() {
        double foodBill = healthyFood.stream().mapToDouble(HealthyFood::getPrice).sum();
        double beverageBill = beverages.stream().mapToDouble(Beverages::getPrice).sum();

        return foodBill + beverageBill + (numberOfPeople * pricePerPerson);
    }

    @Override
    public void clear() {
        isReservedTable = false;
        this.numberOfPeople = 0;
        beverages.clear();
        healthyFood.clear();

    }

    @Override
    public String tableInformation() {
        StringBuilder builder = new StringBuilder();
        builder.append("Table - ").append(number).append(System.lineSeparator())
                .append("Size - ").append(size).append(System.lineSeparator())
                .append("Type - ").append(getClass().getSimpleName()).append(System.lineSeparator())
                .append("All price - ").append(pricePerPerson);

        return builder.toString();
    }
}
