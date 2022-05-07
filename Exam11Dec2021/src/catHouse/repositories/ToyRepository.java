package catHouse.repositories;

import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ToyRepository implements Repository{
    private List<Toy> toys;

    public ToyRepository() {
        this.toys = new ArrayList<>();
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public boolean removeToy(Toy toy) {
        return toys.remove(toy);
    }

    @Override
    public Toy findFirst(String type) {
        return toys.stream().filter(t -> t.getClass().getSimpleName().equals(type)).findFirst().orElse(null);
    }
}
