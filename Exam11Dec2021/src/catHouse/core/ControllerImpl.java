package catHouse.core;

import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.*;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{
    private ToyRepository toys;
    private Map<String,House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new LinkedHashMap<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        switch (type) {
            case "ShortHouse":
                house = new ShortHouse(name);
                break;
            case "LongHouse":
                house = new LongHouse(name);
                break;
            default:
                throw new NullPointerException(INVALID_HOUSE_TYPE);
        }
        houses.put(house.getName(),house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE,type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy;
        switch (type) {
            case "Ball":
                toy = new Ball();
                break;
            case "Mouse":
                toy = new Mouse();
                break;
            default:
                throw new IllegalArgumentException(INVALID_TOY_TYPE);
        }
        toys.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE,type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toy = toys.findFirst(toyType);
        if (toy == null) {
            throw new IllegalArgumentException(NO_TOY_FOUND);
        }
        houses.get(houseName).buyToy(toy);
        toys.removeToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE,toyType,houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        Cat cat;
        switch (catType) {
            case "ShorthairCat":
                cat = new ShorthairCat(catName,catBreed,price);
                break;
            case "LonghairCat":
                cat = new LonghairCat(catName,catBreed,price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_CAT_TYPE);
        }
        try {
            House house = houses.get(houseName);
            house.addCat(cat);
        }catch (IllegalStateException ex) {
            return ex.getMessage();
        }
        
       return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE,catType,houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        House house = houses.get(houseName);
        house.feeding();
        return String.format(FEEDING_CAT,house.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House house = houses.get(houseName);
        double sum = house.getToys().stream().mapToDouble(Toy::getPrice).sum()
                + house.getCats().stream().mapToDouble(Cat::getPrice).sum();
        return String.format(VALUE_HOUSE,houseName,sum);
    }

    @Override
    public String getStatistics() {

        return houses.values().stream().map(House::getStatistics).collect(Collectors.joining(System.lineSeparator()));
    }
}
