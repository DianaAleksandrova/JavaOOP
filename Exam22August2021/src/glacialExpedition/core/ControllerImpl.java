package glacialExpedition.core;

import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.Repository;
import glacialExpedition.repositories.StateRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;
import static glacialExpedition.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private ExplorerRepository explorerRepository;
    private StateRepository stateRepository;
    private int exploreState;

    public ControllerImpl() {
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type) {
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(EXPLORER_INVALID_TYPE);
        }
        explorerRepository.add(explorer);
        return String.format(EXPLORER_ADDED,type,explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        List<String> exhibit = Arrays.asList(exhibits);
        State state = new StateImpl(stateName);
        state.getExhibits().addAll(exhibit);

        stateRepository.add(state);
        return String.format(STATE_ADDED,stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {

        if (explorerRepository.getCollection().stream().noneMatch(e -> e.getName().equals(explorerName))) {
            throw new IllegalArgumentException(String.format(EXPLORER_DOES_NOT_EXIST,explorerName));
        }
        Explorer explorer = explorerRepository.byName(explorerName);

                explorerRepository.remove(explorer);

        return String.format(EXPLORER_RETIRED,explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        State state = stateRepository.byName(stateName);
        List<Explorer> explorers = explorerRepository.getCollection().stream()
                .filter(e -> e.getEnergy() > 50).collect(Collectors.toList());
        if (explorers.isEmpty()) {
            throw new IllegalArgumentException(STATE_EXPLORERS_DOES_NOT_EXISTS);
        }
        int countBeforeMission = explorers.size();
        Mission mission = new MissionImpl();
        mission.explore(state,explorers);
        exploreState++;
        int countAfterMission = explorers.size();

        return String.format(STATE_EXPLORER,stateName,countBeforeMission - countAfterMission);
    }

    @Override
    public String finalResult() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(FINAL_STATE_EXPLORED,exploreState)).append(System.lineSeparator())
                .append(FINAL_EXPLORER_INFO).append(System.lineSeparator());
        explorerRepository.getCollection().stream().forEach(e -> {
            builder.append(String.format(FINAL_EXPLORER_NAME,e.getName())).append(System.lineSeparator())
                    .append(String.format(FINAL_EXPLORER_ENERGY,e.getEnergy())).append(System.lineSeparator());

            if (e.getSuitcase().getExhibits().size() == 0) {
                builder.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS,"None")).append(System.lineSeparator());
            }else {
                Collection<String> item = e.getSuitcase().getExhibits();
                builder.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS
                        ,String.join(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER,item))).append(System.lineSeparator());
            }
        });
        return builder.toString().trim();
    }
}
