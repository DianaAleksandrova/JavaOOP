package spaceStation.core;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static spaceStation.common.ConstantMessages.*;
import static spaceStation.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    //private Map<String, Astronaut> astronauts;
    //private Map<String, Planet> planets;
    private AstronautRepository astronautRepositories;
    private PlanetRepository planetRepository;
    private static int countPlanet;

    public ControllerImpl() {
       // this.astronauts = new LinkedHashMap<>();
        //this.planets = new LinkedHashMap<>();
        this.astronautRepositories = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        if (type.equals("Biologist")) {
            astronaut = new Biologist(astronautName);
        }else if (type.equals("Geodesist")) {
            astronaut = new Geodesist(astronautName);
        }else if (type.equals("Meteorologist")) {
            astronaut = new Meteorologist(astronautName);
        }else {
            throw new IllegalArgumentException(ASTRONAUT_INVALID_TYPE);
        }
        //astronauts.put(astronaut.getName(),astronaut);
        astronautRepositories.add(astronaut);
        return String.format(ASTRONAUT_ADDED,type,astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        List<String> item = List.of(items);
        Planet planet = new PlanetImpl(planetName);
            planet.getItems().addAll(item);

        planetRepository.add(planet);

        return String.format(PLANET_ADDED,planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        if (astronautRepositories.getModels().stream().noneMatch(a -> a.getName().equals(astronautName))) {
            throw new IllegalArgumentException(String.format(ASTRONAUT_DOES_NOT_EXIST,astronautName));
        }
        Astronaut astronaut = astronautRepositories.findByName(astronautName);
        astronautRepositories.remove(astronaut);

        return String.format(ASTRONAUT_RETIRED,astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        Planet planet = planetRepository.findByName(planetName);
        List<Astronaut> astronaut = astronautRepositories.getModels().stream().filter(a -> a.getOxygen() > 60).
                collect(Collectors.toList());
        if (astronaut.isEmpty()) {
            throw new IllegalArgumentException(PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        int countBeforeMission = astronaut.size();
        Mission mission = new MissionImpl();
        mission.explore(planet, astronaut);
        countPlanet++;
        int countAfterMission = astronaut.size();


        return String.format(PLANET_EXPLORED,planetName,countBeforeMission - countAfterMission);
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(REPORT_PLANET_EXPLORED,countPlanet)).append(System.lineSeparator())
                .append(REPORT_ASTRONAUT_INFO).append(System.lineSeparator());
        this.astronautRepositories.getModels().forEach(a -> {
            builder.append(String.format(REPORT_ASTRONAUT_NAME, a.getName())).append(System.lineSeparator())
                    .append(String.format(REPORT_ASTRONAUT_OXYGEN, a.getOxygen())).append(System.lineSeparator());
            if (a.getBag().getItems().size() == 0) {
                builder.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, "none")).append(System.lineSeparator());
            } else {
                Collection<String> item = a.getBag().getItems();
                builder.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, String.join(REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, item)))
                        .append(System.lineSeparator());
            }

        });
        return builder.toString().trim();

    }
}
