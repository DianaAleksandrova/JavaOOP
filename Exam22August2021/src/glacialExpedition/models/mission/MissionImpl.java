package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.List;


public class MissionImpl implements Mission{
    @Override
    public void explore(State state, List<Explorer> explorers) {
       // List<String> exhibits = state.getExhibits();

      //  for (Explorer explorer : explorers) {
        //    while (explorer.canSearch() || exhibits.iterator().hasNext()) {
          //      String currentExhibits = exhibits.iterator().next();
            //    explorer.getSuitcase().getExhibits().add(currentExhibits);
              //  exhibits.remove(currentExhibits);
                //explorer.search();
            //}
            
        //}

        for (int explorer = 0; explorer < explorers.size(); explorer++) {
           Explorer currentExplorer = explorers.get(explorer);

            for (int i = 0; i < state.getExhibits().size(); i++) {
                String item = state.getExhibits().get(i);
                currentExplorer.getSuitcase().getExhibits().add(item);
                currentExplorer.search();
                state.getExhibits().remove(item);
                i--;

                if (!currentExplorer.canSearch()) {
                    explorers.remove(currentExplorer);
                    explorer--;
                    break;
                }
            }
        }

    }
}
