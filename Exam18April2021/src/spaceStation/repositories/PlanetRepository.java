package spaceStation.repositories;

import spaceStation.models.planets.Planet;

import java.util.*;

public class PlanetRepository implements Repository<Planet> {
    private Map<String,Planet> planets;

    public PlanetRepository() {
        this.planets = new LinkedHashMap<>();
    }

    @Override
    public Collection<Planet> getModels() {
        return Collections.unmodifiableCollection(planets.values());
    }

    @Override
    public void add(Planet model) {
        planets.put(model.getName(), model);
    }

    @Override
    public boolean remove(Planet model) {
        if (!planets.containsKey(model.getName())) {
            return false;
        }
        planets.remove(model.getName());
        return true;
    }

    @Override
    public Planet findByName(String name) {
        return planets.get(name);
    }
}
