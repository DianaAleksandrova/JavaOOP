package bakery.repositories.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FoodRepositoryImpl implements FoodRepository<BakedFood>{
    private  List<BakedFood> foods;

    public FoodRepositoryImpl() {
        this.foods = new ArrayList<>();
    }

    @Override
    public BakedFood getByName(String name) {
        return foods.stream().filter(f -> f.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection<BakedFood> getAll() {
        return Collections.unmodifiableList(foods);
    }

    @Override
    public void add(BakedFood bakedFood) {
        foods.add(bakedFood);
    }
}
