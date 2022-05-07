package catHouse.entities.houses;

import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT;
import static catHouse.common.ConstantMessages.UNSUITABLE_HOUSE;
import static catHouse.common.ExceptionMessages.*;

public abstract class BaseHouse implements House{
    private String name;
    private int capacity;
    private List<Toy> toys;
    private List<Cat> cats;

    protected BaseHouse(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.toys = new ArrayList<>();
        this.cats = new ArrayList<>();
    }

    @Override
    public int sumSoftness() {
        return toys.stream().mapToInt(Toy::getSoftness).sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (capacity == 0) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY_FOR_CAT);
        }
        String catType = cat.getClass().getSimpleName().replaceAll("hairCat", "");
        if (!this.getClass().getSimpleName().contains(catType)) {
            throw new IllegalArgumentException(UNSUITABLE_HOUSE);
        }
        cats.add(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public void feeding() {
        for (Cat cat : cats) {
            cat.eating();
        }
    }

    @Override
    public String getStatistics() {
          String catOut = cats.isEmpty()
                       ? "none"
                       : cats.stream().map(Cat::getName).collect(Collectors.joining(" "));

          StringBuilder builder = new StringBuilder();
          builder.append(this.name).append(" ").append(getClass().getSimpleName()).append(":")
                  .append(System.lineSeparator());
          builder.append(String.format("Cats: %s",catOut)).append(System.lineSeparator());
          builder.append(String.format("Toys: %d",toys.size())).append(String.format(" Softness: %d",sumSoftness()))
          .append(System.lineSeparator());

          return builder.toString().trim();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;

    }

    @Override
    public List<Cat> getCats() {
        return cats;
    }

    @Override
    public List<Toy> getToys() {
        return toys;
    }
}
