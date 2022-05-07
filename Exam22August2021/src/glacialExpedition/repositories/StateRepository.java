package glacialExpedition.repositories;

import glacialExpedition.models.states.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StateRepository implements Repository<State>{
    private List<State> states;

    public StateRepository() {
        this.states = new ArrayList<>();
    }

    @Override
    public Collection<State> getCollection() {
        return Collections.unmodifiableList(states);
    }

    @Override
    public void add(State entity) {
        states.add(entity);
    }

    @Override
    public boolean remove(State entity) {
        if (!states.contains(entity)) {
            return false;
        }
        return states.remove(entity);
    }

    @Override
    public State byName(String name) {
        return states.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }
}
