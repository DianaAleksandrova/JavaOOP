package spaceStation.models.astronauts;

import spaceStation.models.bags.Bag;

import java.util.Collection;

public interface Astronaut {
    String getName();

    double getOxygen();

    boolean canBreath();

    Bag getBag();

    void breath();
}
