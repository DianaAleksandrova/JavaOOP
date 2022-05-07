package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;


import java.util.Collection;
import java.util.List;

public interface Mission {
    void explore(State state, List<Explorer> explorers);
}
