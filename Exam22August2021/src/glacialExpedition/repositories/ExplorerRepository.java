package glacialExpedition.repositories;

import glacialExpedition.models.explorers.Explorer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExplorerRepository implements Repository<Explorer>{
    private List<Explorer> explorers;

    public ExplorerRepository() {
        this.explorers = new ArrayList<>();
    }

    @Override
    public List<Explorer> getCollection() {
        return Collections.unmodifiableList(explorers);
    }

    @Override
    public void add(Explorer entity) {
        explorers.add(entity);
    }

    @Override
    public boolean remove(Explorer entity) {
        if (!explorers.contains(entity)) {
            return false;
        }
        return explorers.remove(entity);
    }

    @Override
    public Explorer byName(String name) {
        return explorers.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }
}
