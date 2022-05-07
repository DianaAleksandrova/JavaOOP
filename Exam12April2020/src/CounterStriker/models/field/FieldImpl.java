package CounterStriker.models.field;

import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;

import java.util.*;
import java.util.stream.Collectors;

import static CounterStriker.common.OutputMessages.COUNTER_TERRORIST_WINS;
import static CounterStriker.common.OutputMessages.TERRORIST_WINS;

public class FieldImpl implements Field {
    @Override
    public String start(Collection<Player> players) {

        List<Player> terrorists = players.stream().filter(p -> p instanceof Terrorist)
                .collect(Collectors.toList());

        List<Player> counterTerrorists = players.stream().filter(p -> p instanceof CounterTerrorist)
                .collect(Collectors.toList());

        while (terrorists.stream().anyMatch(Player::isAlive) && counterTerrorists.stream().anyMatch(Player::isAlive)) {
            for (Player terrorist : terrorists) {
                for (Player counterTerrorist : counterTerrorists) {

                    int fire = terrorist.getGun().fire();
                    counterTerrorist.takeDamage(fire);

                }
                counterTerrorists = counterTerrorists.stream().filter(Player::isAlive).collect(Collectors.toList());
            }
            for (Player counterTerrorist : counterTerrorists) {
                for (Player terrorist : terrorists) {
                    int fire = counterTerrorist.getGun().fire();
                    terrorist.takeDamage(fire);

                }
                terrorists = terrorists.stream().filter(Player::isAlive).collect(Collectors.toList());
            }
        }
        return terrorists.stream().anyMatch(Player::isAlive) ? TERRORIST_WINS :
                COUNTER_TERRORIST_WINS;
    }
}
